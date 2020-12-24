package ss.com.toolkit.float

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.PixelFormat
import android.os.IBinder
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import ss.com.toolkit.R


class FloatWindowService : Service() {
    var mFloatView: ImageView? = null
    //定义浮动窗口布局
    var mFloatLayout: ConstraintLayout? = null
    var wmParams: WindowManager.LayoutParams? = null
    //创建浮动窗口设置布局参数的对象
    var mWindowManager: WindowManager? = null
    var myReceiver: MyReceiver? = null
    //表示悬浮窗的显示状态
    private var mHasShown = false

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "oncreat")
        //注册BroadCastReceiver
        myReceiver = MyReceiver()
        val filter = IntentFilter()
        filter.addAction(UPDATE_ACTION)
        registerReceiver(myReceiver, filter)
        //初始化悬浮窗UI
        createFloatView()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    fun createFloatView() {
        wmParams = WindowManager.LayoutParams()
        //获取的是WindowManagerImpl.CompatModeWrapper
        mWindowManager = getApplication().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        /*
            以下代码块使得android6.0之后的用户不必再去手动开启悬浮窗权限
         */run {
            val packname: String = this@FloatWindowService.getPackageName()
            val pm: PackageManager = this@FloatWindowService.getPackageManager()
            val permission = PackageManager.PERMISSION_GRANTED === pm.checkPermission("android.permission.SYSTEM_ALERT_WINDOW", packname)
            if (permission) {
                wmParams!!.type = WindowManager.LayoutParams.TYPE_PHONE
            } else {
                wmParams!!.type = WindowManager.LayoutParams.TYPE_TOAST
            }
        }
        //设置图片格式，效果为背景透明
        wmParams!!.format = PixelFormat.RGBA_8888
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
        wmParams!!.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        //调整悬浮窗显示的停靠位置为左侧置顶
        wmParams!!.gravity = Gravity.LEFT or Gravity.TOP
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity
        wmParams!!.x = 0
        wmParams!!.y = 0
        //设置悬浮窗口长宽数据
        wmParams!!.width = WindowManager.LayoutParams.WRAP_CONTENT
        wmParams!!.height = WindowManager.LayoutParams.WRAP_CONTENT
        /*// 设置悬浮窗口长宽数据
        wmParams.width = 200;
        wmParams.height = 80;*/
        val inflater: LayoutInflater = LayoutInflater.from(getApplication())
        //获取浮动窗口视图所在布局
        mFloatLayout = inflater.inflate(R.layout.float_layout, null) as ConstraintLayout
        //添加mFloatLayout
        mWindowManager!!.addView(mFloatLayout, wmParams)
        mHasShown = true
        //浮动窗口按钮
        mFloatView = mFloatLayout!!.findViewById(R.id.btn) as ImageView
        mFloatLayout!!.measure(View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
                .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED))
        //设置监听浮动窗口的触摸移动
        mFloatView!!.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent): Boolean { //getRawX是触摸位置相对于屏幕的坐标，getX是相对于按钮的坐标
                wmParams!!.x = event.getRawX() as Int - mFloatView!!.getMeasuredWidth() / 2
                //减25为状态栏的高度
                wmParams!!.y = event.getRawY() as Int - mFloatView!!.getMeasuredHeight() / 2 - 25
                //刷新
                mWindowManager!!.updateViewLayout(mFloatLayout, wmParams)
                return false //此处必须返回false，否则OnClickListener获取不到监听
            }
        })
        mFloatView!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(this@FloatWindowService, "onClick", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroy() {
        Log.e(TAG, "onDestroy")
        super.onDestroy()
        if (mFloatLayout != null) { //移除悬浮窗口
            if (mHasShown) mWindowManager!!.removeView(mFloatLayout)
        }
        //取消注册BrodaCastReceiver
        unregisterReceiver(myReceiver)
    }

    //悬浮窗的隐藏
    fun hide() {
        if (mHasShown) mWindowManager!!.removeViewImmediate(mFloatLayout)
        mHasShown = false
    }

    //悬浮窗的显示
    fun show() {
        if (!mHasShown) mWindowManager!!.addView(mFloatLayout, wmParams)
        mHasShown = true
    }

    //
    inner class MyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            val iod: Int = intent.getIntExtra("msg", -1) //获取广播传递来的参数
            if (iod == 2) {
                activeNumber++
            } else if (iod == 1) {
                activeNumber--
            } else Toast.makeText(this@FloatWindowService, "广播传递参数遇到一个错误", Toast.LENGTH_SHORT).show()
            if (activeNumber == 0) hide() //当前处于前台的activity数目为零，隐藏悬浮窗
            else show()
        }
    }

    companion object {
        private const val TAG = "FloatWindowService"
        private const val UPDATE_ACTION = "com.liang.lib.ACTIVE_NUMBER"
        //标识当前app有几个activity处于前台活跃状态
        private var activeNumber = 1
    }
}