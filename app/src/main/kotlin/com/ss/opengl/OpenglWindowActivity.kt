package com.ss.opengl

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.opengl.GLES20
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.View.OnAttachStateChangeListener
import com.apkfuns.logutils.LogUtils
import ss.com.toolkit.R

var surfaceView: SurfaceView? = null
var isExit: Boolean? = null

class OpenglWindowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.opengl_window);
        initView();
    }

    private fun initView() {
        surfaceView = findViewById(R.id.surfaceView)
        surfaceView?.holder?.addCallback(object : SurfaceHolder.Callback {
            var holder: SurfaceHolder? = null
            override fun surfaceCreated(holder: SurfaceHolder?) {
                LogUtils.tag("nadiee").d("surfaceCreated")
                this.holder = holder;
                GLThread(holder).start()
            }

            override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
                LogUtils.tag("nadiee").d("surfaceChanged")
                GLES20.glViewport(0, 0, width, height)
            }

            override fun surfaceDestroyed(holder: SurfaceHolder?) {
                LogUtils.tag("nadiee").d("surfaceDestroyed")
            }
        })
        surfaceView?.addOnAttachStateChangeListener(object : OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View?) {

            }

            override fun onViewDetachedFromWindow(v: View?) {

            }
        })
    }

    fun openglWindow(view: View) {

    }

    class GLThread(h: SurfaceHolder?) : Thread() {
        var holder: SurfaceHolder? = h
        override fun run() {
            var c: Canvas? = null
            var count: Int = 0
            while (!isExit!!) {
                c = holder?.lockCanvas() //锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。  
                try {
                    holder?.let {
                        synchronized(it) {
                            c?.drawColor(Color.BLACK)//设置画布背景颜色
                            var p: Paint = Paint() //创建画笔
                            p.setColor(Color.WHITE)
                            var r: Rect = Rect(100, 50, 300, 250)
                            c?.drawRect(r, p);
                            c?.drawText("这是第" + (count++) + "秒", 100f, 310f, p)
                            Thread.sleep(1000);//睡眠时间为1秒
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace();
                } finally {
                    if (c != null) {
                        holder?.unlockCanvasAndPost(c);//结束锁定画图，并提交改变。
                    }
                }
                holder?.unlockCanvasAndPost(c)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isExit = true
    }
}