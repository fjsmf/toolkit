package ss.com.toolkit.transitions

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.SurfaceView

class CustomSurfaceView : SurfaceView {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    @SuppressLint("MissingSuperCall")
    override fun onDetachedFromWindow() {
//        super.onDetachedFromWindow()
    }
}