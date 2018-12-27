package ss.com.toolkit.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CusView extends View {
    private Paint mPaint;
    private Rect mBounds;
    private Drawable mDrawable;
    private String mCustomText = "100";
    private float mDrawableTextPadding;
    private float mCusTextSize = 50;
    private float mDensity;

    public CusView(Context context) {
        super(context);
        init();
    }

    public CusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CusView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        System.out.println("绘图初始化");
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextSize(mCusTextSize);
        mBounds = new Rect();
        mDensity = getContext().getResources().getDisplayMetrics().density;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int finalWidth;
        int finalHeight;
        mPaint.getTextBounds(mCustomText, 0, mCustomText.length(), mBounds);
        if (widthMode == MeasureSpec.EXACTLY) {
            finalWidth = widthSize;
        } else {
            if (mDrawable != null) {
                finalWidth = mDrawable.getIntrinsicWidth() + mBounds.width() + (int) (2 * mDensity) + getPaddingLeft() + getPaddingRight() + (int) mDrawableTextPadding;
            } else {
                finalWidth = mBounds.width() + getPaddingLeft() + getPaddingRight() + (int) (5 * mDensity);
            }
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            finalHeight = heightSize;
        } else {
            if (mDrawable != null) {
                finalHeight = Math.max(mBounds.height(), mDrawable.getIntrinsicHeight()) + getPaddingTop() + getPaddingBottom();
            } else {
                finalHeight = mBounds.height() + (int) (5 * mDensity) + getPaddingTop() + getPaddingBottom();
            }
        }
        setMeasuredDimension(finalWidth, finalHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Rect rect = new Rect(0, 0, mBounds.width(), mBounds.height());//画一个矩形
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.BLUE);
        rectPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(rect, rectPaint);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(50);
        textPaint.setStyle(Paint.Style.FILL);
        //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
        textPaint.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

        int baseLineY = (int) (rect.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式

        canvas.drawText("你好世界", rect.centerX(), baseLineY, textPaint);

    }

}
