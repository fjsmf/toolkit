package ss.com.toolkit.shortcut

import android.content.Context
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.os.Build
import android.support.v4.content.pm.ShortcutInfoCompat

/**
 * Created by ZP on 2020/4/20.
 */
open class ShortcutCore {
    open fun isShortcutExit(context: Context, id: String, label: CharSequence): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val mShortcutManager = context.getSystemService(ShortcutManager::class.java)
                    ?: return false
            val pinnedShortcuts = mShortcutManager.pinnedShortcuts
            for (pinnedShortcut in pinnedShortcuts) {
                if (pinnedShortcut.id == id) {
                    return true
                }
            }
        }
        return false
    }

    fun fetchExitShortcut(context: Context, id: String): ShortcutInfo? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val mShortcutManager = context.getSystemService(ShortcutManager::class.java)
                ?: return null
            val pinnedShortcuts = mShortcutManager.pinnedShortcuts
            for (pinnedShortcut in pinnedShortcuts) {
                if (pinnedShortcut.id == id) {
                    return pinnedShortcut
                }
            }
        }
        return null
    }

    fun updatePinShortcut(context: Context, info: ShortcutInfo): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            val mShortcutManager = context.getSystemService(ShortcutManager::class.java)
                ?: return false
            return mShortcutManager.updateShortcuts(listOf(info))
        }
        return false
    }

    fun updatePinShortcut(context: Context, info: ShortcutInfoCompat): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            updatePinShortcut(context, info.toShortcutInfo())
        } else {
            false
        }
    }


}