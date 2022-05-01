package ss.com.toolkit.shortcut

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.apkfuns.logutils.LogUtils

/**
 * Created by ZP on 2019-06-28.
 */
class AutoCreateBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action
        LogUtils.tag(ShortcutHelper.TAG).i("onReceive: $action")
        if (ACTION == action) {
            val id = intent.getStringExtra(ShortcutHelper.EXTRA_ID)
            val label = intent.getStringExtra(ShortcutHelper.EXTRA_LABEL)
            LogUtils.tag(ShortcutHelper.TAG).i("Shortcut auto create callback, " +
                        "id = $id, label = $label"
            )
            if (id != null && label != null) {
                val fetchExitShortcut = ShortcutCore().fetchExitShortcut(context, id)
                fetchExitShortcut?.let {
                    try {
                        val declaredField = it.javaClass.getDeclaredField("mTitle")
                        declaredField.isAccessible = true
                        declaredField.set(it, label)
                        val updatePinShortcut = ShortcutCore().updatePinShortcut(context, it)
                        LogUtils.tag(ShortcutHelper.TAG).i("receive updatePinShortcut ", updatePinShortcut)
                        if (updatePinShortcut) {
//                            singleInstance.notifyCreate(id, label)
                        }
                    } catch (e: Exception) {
                        LogUtils.tag(ShortcutHelper.TAG).i("receive error, ", e)
                    }
                }
            }
        }
    }

    companion object {
        const val ACTION = "com.shortcut.core.auto_create"
    }
}