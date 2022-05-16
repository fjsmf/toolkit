package ss.com.toolkit.transitions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.opengl.GLES20
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.util.AndroidException
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceHolder.Callback
import android.view.SurfaceView
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import com.apkfuns.logutils.LogUtils
import rx.android.schedulers.AndroidSchedulers
import ss.com.toolkit.R
import ss.com.toolkit.base.BaseActivity
import java.io.File

open class SurfaceViewBaseActivity: BaseActivity() {
    private val TAG = "SurfaceViewBaseActivity"
    private val REQUEST_PERMISSION_STORAGE_CODE = 1
    private var mMediaPlayer: MediaPlayer? = null
    private var mIsPlaying = false
    var mSurfaceView: SurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getContentView()?.let { setContentView(it) }
        initView()
        mSurfaceView = getSurfaceView()
        mSurfaceView?.holder?.addCallback(object : Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                LogUtils.tag(TAG).d("surfaceCreated")
                play(File(Environment.getExternalStorageDirectory(), "DCIM/Camera/1234.mp4"));
            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                LogUtils.tag(TAG).d("surfaceChanged")
                GLES20.glViewport(0, 0, width, height)
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                LogUtils.tag(TAG).d("surfaceDestroyed")
                mMediaPlayer?.stop()
                mMediaPlayer?.release()
            }
        })
//        mSurfaceView?.postDelayed(Runnable {
//            var f1: FrameLayout = findViewById(R.id.f1)
//            var f2: FrameLayout = findViewById(R.id.f2)
//            f1.removeView(mSurfaceView)
//            f2.addView(mSurfaceView)
//        }, 3000)
        val PERMISSIONS_STORAGE = arrayOf<String>(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_STORAGE_CODE);
            }
        }
    }

    protected open fun getSurfaceView() :SurfaceView? {
        return null
    }

    protected open fun getContentView() :Int? {
        return 0
    }

    protected open fun initView() {

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
            mMediaPlayer!!.setDisplay(mSurfaceView?.holder)
            mMediaPlayer!!.prepareAsync()
            mMediaPlayer!!.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
                override fun onPrepared(mp: MediaPlayer?) {
                    mMediaPlayer!!.start()
                    object : Thread() {
                        override fun run() {
                            try {
                                mIsPlaying = true
                                while (mIsPlaying) {
                                    val current = mMediaPlayer!!
                                            .currentPosition
                                    sleep(500)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }.start()
                }
            })
            mMediaPlayer!!.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
                override fun onCompletion(mp: MediaPlayer?) {
                }
            })
            mMediaPlayer!!.setOnErrorListener(object : MediaPlayer.OnErrorListener {
                override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                    return false
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}