package ss.com.toolkit.transitions

import ss.com.toolkit.notification.NotificationManager

class ViewTransformManager {
    val VIEW_TRANSFORM_START = 1;
    val VIEW_TRANSFORM_BACK = 2;

    var mSrcX = 0
    var mSrcY = 0
    var mSrcWidth = 0
    var mSrcHeight = 0
    var mDestX = 0
    var mDestY = 0
    var mDestWidth = 0
    var mDestHeight = 0
    private var mAction = VIEW_TRANSFORM_START
    companion object {
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



}