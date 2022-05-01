package ss.com.toolkit.anim

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.util.Log
import android.view.View

class ViewTransformManager {
    var mSrcActivity: String? = null
    var mSrcX = 0
    var mSrcY = 0
    var mSrcWidth = 0
    var mSrcHeight = 0
    var mDestX = 0
    var mDestY = 0
    var mDestWidth = 0
    var mDestHeight = 0
    var mDuration = DURATION
    companion object {
        const val TAG = "ViewTransformManager"
        const val DURATION = 1000L;
        private var instance: ViewTransformManager? = null
        fun getInstance(): ViewTransformManager? {
            if (instance == null) {
                synchronized(ViewTransformManager::class.java) {
                    if (instance == null) {
                        instance = ViewTransformManager()
                    }
                }
            }
            return instance
        }
    }

    fun doTransformAnim(activity: Activity?, view: View?) {
        if (activity?.javaClass?.name?.equals(mSrcActivity) == false) {

        }
        val location = getLocationInScreen(view)
        val vtm = getInstance()
        vtm?.mDestX = location[0]
        vtm?.mDestY = location[1]
        vtm?.mDestWidth = view?.width!!
        vtm?.mDestHeight = view?.height!!
        val currentX: Float = view?.translationX!!
        val currentY: Float = view?.translationY!!
        val translationXValue = (vtm?.mSrcWidth?.div(2)
                ?: 0) + vtm?.mSrcX!! - ((vtm?.mDestWidth?.div(2) ?: 0) + vtm?.mDestX!!)
        val translationYValue = (vtm?.mSrcHeight?.div(2)
                ?: 0) + vtm?.mSrcY!! - ((vtm?.mDestHeight?.div(2) ?: 0) + vtm?.mDestY!!)
        Log.i(TAG, "translationXValue:$translationXValue, translationYValue:$translationYValue, currentX:${vtm?.mDestX.toFloat()}, currentY:${vtm?.mDestY.toFloat()}")
        val scaleX: Float = getInstance()?.mDestWidth?.toFloat()?.let { getInstance()?.mSrcWidth?.toFloat()?.div(it) }
                ?: 1f
        val scaleY: Float = getInstance()?.mDestHeight?.toFloat()?.let { getInstance()?.mSrcHeight?.toFloat()?.div(it) }
                ?: 1f
        Log.i(TAG, "scaleX:$scaleX, scaleY:$scaleY")
        val translationX = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, translationXValue.toFloat(), currentX)
        val translationY = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, translationYValue.toFloat(), currentY)
        val alphaAnimator = ObjectAnimator.ofFloat(activity?.window?.decorView, View.ALPHA, 0f, 1f)
        val scaleXAnimator = ObjectAnimator.ofFloat(view, View.SCALE_X, scaleX, 1F)
        val scaleYAnimator = ObjectAnimator.ofFloat(view, View.SCALE_Y, scaleY, 1F)
        val animatorSet = AnimatorSet() //组合动画
        animatorSet.playTogether(translationX, translationY, alphaAnimator, scaleXAnimator, scaleYAnimator) //设置动画
        animatorSet.duration = 1000 //设置动画时间
        animatorSet.start() //启动
    }

    private fun getLocationInScreen(view: View?): IntArray {
        val location = IntArray(2)
        view?.getLocationOnScreen(location)
        return location
    }

}