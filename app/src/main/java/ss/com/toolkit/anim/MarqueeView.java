package ss.com.toolkit.anim;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.apkfuns.logutils.LogUtils;

import ss.com.toolkit.R;

/**
 * 跑马灯View
 */
public class MarqueeView extends HorizontalScrollView implements Runnable {

    private Context context;
    private LinearLayout mainLayout;//跑马灯滚动部分
    private int scrollSpeed = 5;//滚动速度
    private int scrollDirection = LEFT_TO_RIGHT;//滚动方向
    private int currentX;//当前x坐标
    private int viewMargin = 20;//View间距
    private int viewWidth;//View总宽度
    private int marqueeViewWidth;//屏幕宽度
    private boolean hasCalculateTotalWidth;


    public static final int LEFT_TO_RIGHT = 1;
    public static final int RIGHT_TO_LEFT = 2;

    public MarqueeView(Context context) {
        this(context, null);
    }

    public MarqueeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initView();
    }

    void initView() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mainLayout = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.scroll_content, null);
        this.addView(mainLayout);
        getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                marqueeViewWidth = getWidth();
                getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });
    }

    public void addViewInQueue(View view){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(viewMargin, 0, 0, 0);
        view.setLayoutParams(lp);
        mainLayout.addView(view);
        view.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        view.measure(0, 0);//测量view
        LogUtils.tag("Marqueeview").i("addViewInQueue width:%d", view.getMeasuredWidth());
        viewWidth += view.getMeasuredWidth() + viewMargin;
    }

    //开始滚动
    public void startScroll(){
        removeCallbacks(this);
        hasCalculateTotalWidth = false;
//        currentX = (scrollDirection == LEFT_TO_RIGHT ? viewWidth : -screenWidth);
        currentX = 0;
        post(this);
    }

    //停止滚动
    public void stopScroll(){
        removeCallbacks(this);
    }

    //设置View间距
    public void setViewMargin(int viewMargin){
        this.viewMargin = viewMargin;
    }

    //设置滚动速度
    public void setScrollSpeed(int scrollSpeed){
        this.scrollSpeed = scrollSpeed;
    }

    //设置滚动方向 默认从左向右
    public void setScrollDirection(int scrollDirection){
        this.scrollDirection = scrollDirection;
    }

    @Override
    public void run() {
        if (!hasCalculateTotalWidth) {
            viewWidth = 0;
            for (int i = 0, len = getChildCount(); i < len; i++) {
                viewWidth += getChildAt(i).getWidth();
            }
        }
        switch (scrollDirection){
            case LEFT_TO_RIGHT:
                mainLayout.scrollTo(currentX, 0);
                currentX --;

                if (-currentX >= marqueeViewWidth) {
                    mainLayout.scrollTo(viewWidth, 0);
                    currentX = viewWidth;
                }
                break;
            case RIGHT_TO_LEFT:
                mainLayout.scrollTo(currentX, 0);
                currentX += 2;

                if (currentX >= viewWidth) {
                    mainLayout.scrollTo(-marqueeViewWidth, 0);
                    currentX = -marqueeViewWidth;
                }
                break;
            default:
                break;
        }

        postDelayed(this, 50 / scrollSpeed);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}

