package ss.com.toolkit.util

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.apkfuns.logutils.LogUtils
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.functions.Predicate
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList


class ActivityStack {
    companion object {
        private const val TAG = "ActivityStack"
        private var sInstance: ActivityStack? = null
        val instance: ActivityStack?
            get() {
                if (sInstance == null) {
                    synchronized(ActivityStack::class.java) {
                        if (sInstance == null) {
                            sInstance = ActivityStack()
                        }
                    }
                }
                return sInstance
            }
    }
    private val mStack = LinkedList<Activity>()
    private val mListeners: MutableList<ActivityEventListener> = CopyOnWriteArrayList()
    fun init(app: Application) {
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                if (activity != null) {
                    mStack.add(activity)
                }
                LogUtils.tag(TAG).d("onActivityCreated:%s", activity?.javaClass?.simpleName)
                notifyActivityEvent(activity, ActivityEvent.CREATE)
            }

            override fun onActivityStarted(activity: Activity?) {
                LogUtils.tag(TAG).d("onActivityStarted:%s", activity?.javaClass?.simpleName)
                notifyActivityEvent(activity, ActivityEvent.START)
            }

            override fun onActivityResumed(activity: Activity?) {
                LogUtils.tag(TAG).d("onActivityResumed:%s", activity?.javaClass?.simpleName)
                notifyActivityEvent(activity, ActivityEvent.RESUME)
            }

            override fun onActivityPaused(activity: Activity?) {
                LogUtils.tag(TAG).d("onActivityPaused:%s", activity?.javaClass?.simpleName)
                notifyActivityEvent(activity, ActivityEvent.PAUSE)
            }

            override fun onActivityStopped(activity: Activity?) {
                LogUtils.tag(TAG).d("onActivityStopped:%s", activity?.javaClass?.simpleName)
                notifyActivityEvent(activity, ActivityEvent.STOP)
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
                LogUtils.tag(TAG).d("onActivitySaveInstanceState:%s", activity?.javaClass?.simpleName)
            }
            override fun onActivityDestroyed(activity: Activity?) {
                LogUtils.tag(TAG).d("onActivityDestroyed:%s", activity?.javaClass?.simpleName)
                mStack.remove(activity)
                notifyActivityEvent(activity, ActivityEvent.DESTROY)
            }
        })
    }

    private fun notifyActivityEvent(act: Activity?, event: ActivityEvent?) {
        for (listener in mListeners) {
            listener.onActivityEvent(act, event)
        }
    }

    @Synchronized
    fun addActivityEventListener(listener: ActivityEventListener) {
        mListeners.add(listener)
    }

    @Synchronized
    fun removeActivityEventListener(listener: ActivityEventListener?) {
        mListeners.remove(listener)
    }

    fun findActivity(predicate: Predicate<Activity?>): Activity? {
        for (activity in mStack) {
            try {
                if (predicate.test(activity)) {
                    return activity
                }
            } catch (ignore: Exception) {
            }
        }
        return null
    }

    fun finishActivities(predicate: Predicate<Activity?>) {
        val toFinish: MutableList<Activity> = ArrayList()
        for (i in mStack.indices.reversed()) {
            val activity = mStack[i]
            try {
                if (predicate.test(activity)) {
                    toFinish.add(activity)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (!toFinish.isEmpty()) {
            for (activity in toFinish) {
                activity.finish()
            }
        }
    }

    fun finishActivities(aClass: Class<*>) {
        val toFinish: MutableList<Activity> = ArrayList()
        for (i in mStack.indices.reversed()) {
            val activity = mStack[i]
            if (activity != null && activity.javaClass == aClass) {
                toFinish.add(activity)
            }
        }
        if (!toFinish.isEmpty()) {
            for (activity in toFinish) {
                activity.finish()
            }
        }
    }

    fun clearTop(predicate: Predicate<Activity?>) {
        var index = -1
        for (i in mStack.indices.reversed()) {
            val activity = mStack[i]
            try {
                if (predicate.test(activity)) {
                    index = i + 1
                    break
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if (index == -1) {
            return
        }
        while (mStack.size > index) {
            val act = mStack.removeLast()
            act.finish()
        }
    }

    val topactivity: Activity?
        get() = if (mStack.isEmpty()) {
            null
        } else mStack.last

    fun printActivitys() {
        if (mStack.size < 1) {
            LogUtils.tag(TAG).d("No activity in stack!")
            return
        }
        for (i in mStack.indices.reversed()) {
            val activity = mStack[i]
            LogUtils.tag(TAG).d(activity.localClassName)
        }
    }

    fun finishActivity(withoutActivity: Class<*>) {
        if (!isEmpty()) {
            val iterator = mStack.iterator()
            while (iterator.hasNext()) {
                val activity = iterator.next()
                if (activity.javaClass == withoutActivity) {
                    iterator.remove()
                    activity.finish()
                }
            }
        }
    }

    fun removeActivityFromStack(activity: Activity?) {
        if (!isEmpty()) {
            mStack.remove(activity)
        }
    }

    fun finishAllActivity() {
        val iterator = mStack.iterator()
        while (iterator.hasNext()) {
            val activity = iterator.next()
            activity.finish()
            iterator.remove()
        }
    }

    private fun isEmpty(): Boolean {
        return mStack.size == 0
    }

    interface ActivityEventListener {
        fun onActivityEvent(activity: Activity?, event: ActivityEvent?)
    }
}
