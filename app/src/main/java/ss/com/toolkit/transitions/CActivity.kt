package ss.com.toolkit.transitions

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.media.AudioManager
import android.media.MediaPlayer
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.os.Bundle
import android.os.Environment
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Toast
import com.apkfuns.logutils.LogUtils
import ss.com.toolkit.R
import ss.com.toolkit.base.BaseActivity
import ss.com.toolkit.util.ScreenUtil
import ss.com.toolkit.util.UIUtil
import java.io.File


class CActivity : BaseActivity(), TextureView.SurfaceTextureListener {
    private val TAG = "CActivity"
    private var mSurface: Surface? = null
    private var mMediaPlayer: MediaPlayer? = null
    private var mIsPlaying = false
    private var mTextureView: TextureView? = null
    private var mVideoHeight: Int? = null
    private var mVideoWidth: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.c_activity)

        title = "c activity"
        mTextureView = findViewById<TextureView>(R.id.texture_view)
        mTextureView?.surfaceTextureListener = this
        mTextureView?.surfaceTexture?.setDefaultBufferSize(ScreenUtil.getScreenWidthPx() - ScreenUtil.dp2px(40f), ScreenUtil.dp2px(150f))
        mTextureView?.viewTreeObserver?.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                mTextureView?.viewTreeObserver?.removeOnPreDrawListener(this)
                doTransformAnim()
                return true
            }
        })
    }
    fun doTransformAnim1() {
//      val translationX = ObjectAnimator.ofFloat(mTextureView, View.TRANSLATION_X, 0f, 0f)
        val translationY = ObjectAnimator.ofFloat(mTextureView, View.TRANSLATION_Y, -ScreenUtil.dp2px(200f).toFloat(), 0f)
        val alphaAnimator = ObjectAnimator.ofFloat(mTextureView, View.ALPHA, /*0.7f, 0.8f, 0.9f, */1f)
        val animatorSet = AnimatorSet() //组合动画

//        animatorSet.playTogether(translationX, translationY, alphaAnimator) //设置动画
        animatorSet.playTogether(translationY, alphaAnimator) //设置动画

        animatorSet.setDuration(1000) //设置动画时间

        animatorSet.start() //启动
    }
    fun doTransformAnim() {
        val location = UIUtil.getLocationInScreen(mTextureView)
        val vtm = ViewTransformManager.getInstance()
        vtm?.mDestX = location[0]
        vtm?.mDestY = location[1]
        vtm?.mDestWidth = mTextureView?.width!!
        vtm?.mDestHeight = mTextureView?.height!!
        val currentX: Float = mTextureView?.translationX!!
        val currentY: Float = mTextureView?.translationY!!
//        LogUtils.tag(TAG).d("mSrcX :${vtm?.mSrcX }, mSrcY:${vtm?.mSrcY}, srcwidth:${vtm?.mSrcWidth}, srcheight:${vtm?.mSrcHeight}")
//        LogUtils.tag(TAG).d("mDestX :${vtm?.mDestX }, mDestY:${vtm?.mDestY}, Destwidth:${vtm?.mDestWidth}, destheight:${vtm?.mDestHeight}")
//        LogUtils.tag(TAG).d("src center pointX:${vtm?.mSrcWidth?.div(2) ?: 0 + vtm?.mSrcX!!}")
//        LogUtils.tag(TAG).d("dest center pointX:${vtm?.mDestWidth?.div(2) ?: 0 + vtm?.mDestX!!}")
//        LogUtils.tag(TAG).d("src center pointY:${vtm?.mSrcHeight?.div(2) ?: 0 + vtm?.mSrcY!!}")
//        LogUtils.tag(TAG).d("dest center pointY:${vtm?.mSrcHeight?.div(2) ?: 0 + vtm?.mDestY!!}")
        val translationXValue = (vtm?.mSrcWidth?.div(2) ?: 0) + vtm?.mSrcX!! - ((vtm?.mDestWidth?.div(2) ?: 0) + vtm?.mDestX!!)
        val translationYValue = (vtm?.mSrcHeight?.div(2) ?: 0) + vtm?.mSrcY!! - ((vtm?.mDestHeight?.div(2) ?: 0) + vtm?.mDestY!!)
        LogUtils.tag(TAG).d("translationXValue:$translationXValue, translationYValue:$translationYValue, currentX:${vtm?.mDestX.toFloat()}, currentY:${vtm?.mDestY.toFloat()}")
        val scaleX: Float = ViewTransformManager.getInstance()?.mDestWidth?.toFloat()?.let { ViewTransformManager.getInstance()?.mSrcWidth?.toFloat()?.div(it) } ?: 1f
        val scaleY: Float = ViewTransformManager.getInstance()?.mDestHeight?.toFloat()?.let { ViewTransformManager.getInstance()?.mSrcHeight?.toFloat()?.div(it) } ?: 1f
        LogUtils.tag(TAG).d("scaleX:$scaleX, scaleY:$scaleY")
        val translationX = ObjectAnimator.ofFloat(mTextureView, View.TRANSLATION_X, translationXValue.toFloat(), currentX)
        val translationY = ObjectAnimator.ofFloat(mTextureView, View.TRANSLATION_Y, translationYValue.toFloat(), currentY)
        val alphaAnimator = ObjectAnimator.ofFloat(window.decorView, View.ALPHA, 0f, 1f)
        val scaleXAnimator = ObjectAnimator.ofFloat(mTextureView, View.SCALE_X, scaleX, 1F)
        val scaleYAnimator = ObjectAnimator.ofFloat(mTextureView, View.SCALE_Y, scaleY, 1F)

        val animatorSet = AnimatorSet() //组合动画

        animatorSet.playTogether(translationX, translationY, alphaAnimator, scaleXAnimator, scaleYAnimator) //设置动画



        animatorSet.setDuration(1000) //设置动画时间

        animatorSet.start() //启动
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }

    /**
     * 开始播放视频
     */
    private fun play(file: File) {
        LogUtils.tag(TAG).d("surfaceCreated")
        if (!file.exists()) {
            Toast.makeText(this, "视频文件路径错误", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            mMediaPlayer = MediaPlayer()
            mMediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mMediaPlayer?.setDataSource(file.absolutePath)
            mMediaPlayer?.setSurface(mSurface);
            mMediaPlayer?.prepareAsync()
            mMediaPlayer?.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
                override fun onPrepared(mp: MediaPlayer?) {
                    mMediaPlayer?.start()
                    object : Thread() {
                        override fun run() {
                            try {
                                mIsPlaying = true
                                while (mIsPlaying) {
                                    val current = mMediaPlayer?.currentPosition
                                    sleep(500)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }.start()
                }
            })
            mMediaPlayer?.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
                override fun onCompletion(mp: MediaPlayer?) {
                }
            })
            mMediaPlayer?.setOnErrorListener(object : MediaPlayer.OnErrorListener {
                override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                    return false
                }
            })
            mMediaPlayer!!.setOnVideoSizeChangedListener { mp, width, height ->
                mVideoHeight = mMediaPlayer?.videoHeight
                mVideoWidth = mMediaPlayer?.videoWidth
//                updateTextureViewSizeCenter()
                updateTextureViewSizeCenterCrop()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onSurfaceTextureAvailable(surface: SurfaceTexture, width: Int, height: Int) {
        mSurface = Surface(surface)
        play(File(Environment.getExternalStorageDirectory(), "DCIM/Camera/1234.mp4"))
        //        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Blurry.with(AnimActivity.this).sampling(5).onto(findViewById(R.id.content));
//            }
//        }, 200);

//        headview.setProfile("https://cdn-test.poko.app/image/face/201906/14/1560479203712/91944137-200.png", R.drawable.emoji01_03,
//                "https://cdn-test.poko.app/image/common2/201910/30/1572407150783/841913d48fa925b8.png", "https://github.com/yyued/SVGA-Samples/blob/master/posche.svga?raw=true");

//        svga_combo_label.startAnimation();
//        io.reactivex.android.schedulers.AndroidSchedulers.mainThread().scheduleDirect({
//            val path = Path()
//            path.moveTo(0f, 0f)
//            path.cubicTo(0.2f, 0f, 0.1f, 1f, 0.5f, 1f)
//            path.lineTo(1f, 1f)
//            val animator = ObjectAnimator.ofFloat(mTextureView, View.TRANSLATION_X, 500f)
//            animator.interpolator = PathInterpolatorCompat.create(path)
//            animator.start()
//        }, 2000, TimeUnit.MICROSECONDS)
    }

    override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {
    }

    override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
        mSurface = null;
        mMediaPlayer?.stop()
        mMediaPlayer?.release()
        return true;
    }

    override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
    }

    //重新计算video的显示位置，裁剪后全屏显示
    private fun updateTextureViewSizeCenterCrop() {
        val sx = mTextureView?.width?.toFloat()?.div(mVideoWidth!!)
        val sy = mTextureView?.height?.toFloat()?.div(mVideoHeight!!)
        val matrix = Matrix()
        val maxScale = Math.max(sx!!, sy!!)

        //第1步:把视频区移动到View区,使两者中心点重合.
        matrix.preTranslate(((mTextureView?.width?.toFloat()!! - mVideoWidth!!) / 2).toFloat(), ((mTextureView?.height?.toFloat()!! - mVideoHeight!!) / 2).toFloat())

        //第2步:因为默认视频是fitXY的形式显示的,所以首先要缩放还原回来.
        matrix.preScale(mVideoWidth!! / mTextureView?.width?.toFloat()!!, mVideoHeight!! / mTextureView?.height?.toFloat()!!)

        //第3步,等比例放大或缩小,直到视频区的一边超过View一边, 另一边与View的另一边相等. 因为超过的部分超出了View的范围,所以是不会显示的,相当于裁剪了.
        matrix.postScale(maxScale, maxScale, (mTextureView?.width?.toFloat()!! / 2).toFloat(), (mTextureView?.height?.toFloat()!! / 2).toFloat()) //后两个参数坐标是以整个View的坐标系以参考的
        mTextureView?.setTransform(matrix)
        mTextureView?.postInvalidate()
    }

    //重新计算video的显示位置，让其全部显示并据中
    private fun updateTextureViewSizeCenter() {
        val sx = mTextureView?.width?.toFloat()?.div(mVideoWidth!!)
        val sy = mTextureView?.height?.toFloat()?.div(mVideoHeight!!)
        val matrix = Matrix()

        //第1步:把视频区移动到View区,使两者中心点重合.
        matrix.preTranslate((mTextureView?.width?.toFloat()?.minus(mVideoWidth!!))!! / 2, (mTextureView?.height?.toFloat()!! - mVideoHeight?.toFloat()!!) / 2)

        //第2步:因为默认视频是fitXY的形式显示的,所以首先要缩放还原回来.
        matrix.preScale(mVideoWidth?.div(mTextureView?.width?.toFloat()!!)!!, mVideoHeight?.toFloat()!! / mTextureView?.height?.toFloat()!!)

        //第3步,等比例放大或缩小,直到视频区的一边和View一边相等.如果另一边和view的一边不相等，则留下空隙
        if (sx!! >= sy!!) {
            matrix.postScale(sy, sy, mTextureView?.width?.toFloat()!! / 2, mTextureView?.height?.toFloat()!! / 2)
        } else {
            matrix.postScale(sx, sx, mTextureView?.width?.toFloat()!! / 2, mTextureView?.height?.toFloat()!! / 2)
        }
        mTextureView?.setTransform(matrix)
        mTextureView?.postInvalidate()
    }
}