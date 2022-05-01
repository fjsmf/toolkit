package ss.com.toolkit.transitions

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.view.SurfaceView
import android.view.View
import ss.com.toolkit.R
import ss.com.toolkit.util.UIUtil

class AActivity: SurfaceViewBaseActivity() {
    private val TAG = "AActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "A activity"
    }

    @SuppressLint("WrongViewCast")
    override fun getSurfaceView() :SurfaceView? {
        return findViewById(R.id.surfaceView)
    }

    override fun getContentView(): Int? {
        return R.layout.a_activity
    }

    fun toBActivity(view: View) {
        val intent = Intent(this, BActivity::class.java)
        val surfaceView: SurfaceView = findViewById(R.id.surfaceView)
        val options: ActivityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, surfaceView, "transitionSV")
        startActivity(intent, options.toBundle())
    }

    fun toCActivity(view: View) {
        val location = UIUtil.getLocationInScreen(mSurfaceView)
        ViewTransformManager.getInstance()?.mSrcX = location[0]
        ViewTransformManager.getInstance()?.mSrcY = location[1]
        ViewTransformManager.getInstance()?.mSrcWidth = mSurfaceView?.width!!
        ViewTransformManager.getInstance()?.mSrcHeight = mSurfaceView?.height!!
        val intent = Intent(this, CActivity::class.java)
        val surfaceView: SurfaceView = findViewById(R.id.surfaceView)
        val options: ActivityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(this, surfaceView, "transitionPoint")
        startActivity(intent, options.toBundle())
//        startActivity(intent)
//        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }
}