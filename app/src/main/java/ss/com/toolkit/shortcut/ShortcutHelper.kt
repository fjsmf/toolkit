package ss.com.toolkit.shortcut

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.Log
import java.lang.Exception
import java.lang.StringBuilder

class ShortcutHelper {
    companion object {


        const val EXTRA_ID = "extra_id"
        const val EXTRA_LABEL = "extra_label"
        const val TAG = "ShortcutHelper"
//        fun createH5GameShortcut(context: Context, from: Int, title: String?, url: String?, gameId:Int, bitmap: Bitmap?) {
//            if (from <= 0 || TextUtils.isEmpty(url) || bitmap == null || gameId <= 0) {
//                Log.i(TAG, "createH5GameShortcut parameter error")
//                return
//            }
//            val target = Intent()
//            target.setClassName(context, "com.duowan.kiwi.simpleactivity.SplashActivity")
//            target.putExtra(ShortcutConstant.KEY_SHORTCUT_FROM, ShortcutConstant.SHORTCUT_FROM_H5_GAME)
//            target.putExtra(ShortcutConstant.H5_GAME_TITLE, title)
//            target.putExtra(ShortcutConstant.H5_GAME_URL, url)
//            Log.i(TAG, "createShortCut")
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val pm = context.packageManager ?: return
//                val hasShortcutPermission = pm.checkPermission("com.android.launcher.permission.INSTALL_SHORTCUT", BaseApp.gContext.packageName) == PackageManager.PERMISSION_GRANTED
//                Log.i(TAG, "asShortcutPermission " + hasShortcutPermission)
//                if (!hasShortcutPermission) {
//                    //ToastUtil.showToast("请去设置里开启桌面快捷方式权限");
//                    return
//                }
//                ShortcutUtils.createShortcutOnAndroidO(ArkValue.gContext, target, title, String.format("%s-%s", ShortcutConstant.SHORTCUT_FROM_H5_GAME_PID, gameId), bitmap)
//            } else {
//                ShortcutUtils.createShortcut(ArkValue.gContext, target, title, bitmap)
//            }
//        }

        @JvmStatic
        fun hasCreateShortcutPermission(): Boolean {

            return false
        }

        @JvmStatic
        fun hasShortCut(gameName: String): Boolean { // 判断规则正确？
            return false
        }

    }

    fun hasShortcut(cx: Context): Boolean {
        var result = false
        var title: String? = null
        try {
            val pm = cx.packageManager
            title = pm.getApplicationLabel(
                    pm.getApplicationInfo(cx.packageName,
                            PackageManager.GET_META_DATA)).toString()
        } catch (e: Exception) {
        }
        val uriStr: String = if (Build.VERSION.SDK_INT < 8) {
            "content://com.android.launcher.settings/favorites?notify=true"
        } else {
            "content://com.android.launcher2.settings/favorites?notify=true"
        }
        val CONTENT_URI = Uri.parse(uriStr)
        val c = cx.contentResolver.query(CONTENT_URI, null,
                "title=?", arrayOf(title), null)
        if (c != null && c.count > 0) {
            result = true
        }
        return result
    }
}