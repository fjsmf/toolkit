package ss.com.toolkit.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class AnimBallView extends View {

    private static final int ALPHA = 255;
    private int rectLeft = 0;
    private int rectTop = 0;
    private float cx, cy;
    private static final int rectWidth = 40;
    private static final int rectHeight = 40;
    private static final int radius = 15;
    private Paint mInnerPaint;
    private RectF mDrawRect;
    public int width;
    public int height;

    private boolean goRight = true;
    private boolean goBottom = true;
    private int xSpeed = 5;
    private int ySpeed = 5;

    public AnimBallView(Context context) {
        super(context);
        init();
    }

    public AnimBallView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimBallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnimBallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mInnerPaint = new Paint();
        mDrawRect = new RectF();
        mInnerPaint.setARGB(ALPHA, 255, 0, 0);
        mInnerPaint.setAntiAlias(true);
        System.out.println("绘图初始化");
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        mDrawRect.set(rectLeft, rectTop, rectLeft + rectWidth, rectTop + rectHeight);
//        canvas.drawRoundRect(mDrawRect, 0, 0, mInnerPaint);
        canvas.drawCircle(cx + 15, cy + 15, radius, mInnerPaint);
        changePos();
    }

    //改变位置
    protected void changePos() {
        //x坐标
        if (cx + radius * 2 > width || cy + radius * 2 > height) {
            cx = 0;
            cy = 0;
        } else {
            cx += 5;
//            cy += 5* w;
        }

        /*if (rectLeft > width - rectWidth) {
            goRight = false;
        }
        if (rectLeft < 0) {
            goRight = true;
        }
        if (goRight) {
            rectLeft += xSpeed;
        } else {
            rectLeft -= xSpeed;
        }*/

        //y坐标
        /*if (rectTop > height - rectHeight) {
            goBottom = false;
        }
        if (rectTop < 0) {
            goBottom = true;
        }
        if (goBottom) {
            rectTop += ySpeed;
        } else {
            rectTop -= ySpeed;
        }*/
        //重新绘制,触发 onDraw()
        postInvalidate();
    }
}
