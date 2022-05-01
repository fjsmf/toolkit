package ss.com.toolkit.transitions

import android.R.attr.data
import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Environment
import android.support.annotation.Nullable
import android.transition.Transition
import android.view.SurfaceView
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.widget.ImageView
import com.apkfuns.logutils.LogUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ss.com.toolkit.R
import java.io.File


class BActivity : SurfaceViewBaseActivity() {
    private val TAG = "BActivity"
    private var mFakeView: ImageView? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "B activity"

    }

    override fun initView() {
        mFakeView = findViewById(R.id.fake_image)
        mFakeView?.animation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                LogUtils.tag(TAG).d("onAnimationStart")
            }

            override fun onAnimationEnd(animation: Animation?) {
                LogUtils.tag(TAG).d("onAnimationEnd")
                mSurfaceView?.visibility = View.VISIBLE
                mFakeView?.visibility = View.INVISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {
                LogUtils.tag(TAG).d("onAnimationRepeat")
            }

        })
        mSurfaceView?.visibility = View.INVISIBLE
        // 延迟共享动画的执行
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition()
        };
        LogUtils.tag(TAG).d("load url")

        Glide.with(this)
                .load(File(Environment.getExternalStorageDirectory(), "Pictures/Screenshots/cover.jpg"))
//                .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimage.biaobaiju.com%2Fuploads%2F20181111%2F21%2F1541941547-pqSftmaulz.jpg&refer=http%3A%2F%2Fimage.biaobaiju.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1617868846&t=37cec68a6b25cb7319337326a3c19eaa")
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        LogUtils.tag(TAG).d("onResourceReady")
                        mFakeView?.postDelayed(object: Runnable{
                            override fun run() {
                                mSurfaceView?.visibility = View.VISIBLE
                            }
                        }, 1000)
                        mFakeView?.postDelayed(object: Runnable{
                            override fun run() {
                                mFakeView?.visibility = View.INVISIBLE
                            }
                        }, 1200)
                        supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .into(mFakeView!!)
    }

    @SuppressLint("WrongViewCast")
    override fun getSurfaceView(): SurfaceView? {
        return findViewById(R.id.surfaceView)
    }

    override fun getContentView(): Int? {
        return R.layout.b_activity
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }
}