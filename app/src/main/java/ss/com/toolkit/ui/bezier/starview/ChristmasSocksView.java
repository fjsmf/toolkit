package ss.com.toolkit.ui.bezier.starview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import java.util.List;
import java.util.Random;

import ss.com.toolkit.R;
import ss.com.toolkit.util.ScreenUtil;
import ss.com.toolkit.util.glide.GlideImageLoader;

public class ChristmasSocksView extends RelativeLayout implements View.OnClickListener {
    public static final String TAG = "StarViewGroup";

    public class SockData {
        public String sockUrl;
        public List<Gift> gift;
    }

    public class Gift {
        public String iconUrl;
        public int count;
    }

    private ImageView iv_christmas_socks;


    private Bitmap mBitmap;

    //画笔，路径
    private Paint mPaint;
    private Path mPath;

    //记录屏幕的宽，高
    private int mWidth;
    private int mHeight;

    //记录数据点，控制点(由于是三阶贝塞尔曲线，所以有2个控制点)
    protected Point mStartPoint;
    protected Point mEndPoint;
    protected Point mConOnePoint;
    protected Point mConTwoPoint;

    protected Random mRandom;
    protected int[] mColors = {Color.BLUE, Color.CYAN, Color.GREEN, Color.RED, Color.MAGENTA, Color.YELLOW};

    private SockData sockData;

    public ChristmasSocksView(Context context) {
        super(context);
        initView(context);
    }

    public ChristmasSocksView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ChristmasSocksView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 进行一些初始化的操作
     *
     * @param context
     */
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.christmas_socks_view, this, true);
        iv_christmas_socks = findViewById(R.id.iv_christmas_socks);
        GlideImageLoader.loadImageNoPlaceholder(iv_christmas_socks, "https://cdn-test.poko.app/image/common2/202012/08/1607430111009/41baea3c38d16a9b.png");
        //初始化画笔，路径
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPath = new Path();
        mRandom = new Random();
        //获取资源图片转化Bitmap（不可修改）
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_star);

        setOnClickListener(this);
    }

    /**
     * @param data
     */
    public void setData(SockData data) {
        if (data == null || sockData.gift == null || TextUtils.isEmpty(sockData.sockUrl)) {
            return;
        }
        this.sockData = data;
        GlideImageLoader.loadImageNoPlaceholder(iv_christmas_socks, "https://cdn-test.poko.app/image/common2/202012/08/1607430111009/41baea3c38d16a9b.png");
    }

    /**
     * 画星星并随机赋予不同的颜色
     *
     * @param color
     * @return
     */
    private Bitmap addGift(int color) {
        //创建和资源文件Bitmap相同尺寸的Bitmap填充Canvas
        Bitmap outBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        //利用Graphics中的XferModes对Canvas进行着色
        canvas.drawColor(color, PorterDuff.Mode.SRC_IN);
        canvas.setBitmap(null);
        return outBitmap;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mWidth == 0) {
            LogUtils.tag(TAG).d("onLayout l:%d, t:%d, r:%d, b:%d", l, t, r, b);
            this.mWidth = r - l;
            this.mHeight = b - t;
        }
    }

    private void updatePoint() {
        mStartPoint = new Point(mWidth / 2 - ScreenUtil.dp2px(25), mHeight / 2 - ScreenUtil.dp2px(10));
        mEndPoint = new Point(mRandom.nextInt(mWidth) - mWidth / 2, 0);
        mConOnePoint = new Point((int) (mWidth * mRandom.nextFloat() / 10), (int) (mHeight * 3 * mRandom.nextFloat() / 1000));
        mConTwoPoint = new Point(mRandom.nextInt(mWidth) / 10, (int) (mHeight * mRandom.nextFloat() / 1000));
//        LogUtils.tag(TAG).d("startPoint:(%d, %d)", mStartPoint.x, mStartPoint.y);
//        LogUtils.tag(TAG).d("mConOnePoint:(%d, %d)", mConOnePoint.x, mConOnePoint.y);
//        LogUtils.tag(TAG).d("mConTwoPoint:(%d, %d)", mConTwoPoint.x, mConTwoPoint.y);
//        LogUtils.tag(TAG).d("mEndPoint:(%d, %d)", mEndPoint.x, mEndPoint.y);
    }

    public void addGift(String url, int count) {
        updatePoint();
        LogUtils.tag(TAG).d("addGift");
        View giftView = LayoutInflater.from(getContext()).inflate(R.layout.christmas_item_view, this, false);
        ImageView iv_gift = giftView.findViewById(R.id.iv_gift);
        TextView tv_count = giftView.findViewById(R.id.tv_count);
        tv_count.setText("+" + count);
        LayoutParams layoutParams = new LayoutParams(ScreenUtil.dp2px(50), ScreenUtil.dp2px(20));
        layoutParams.addRule(CENTER_HORIZONTAL);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        GlideImageLoader.loadImageNoPlaceholder(iv_gift, url);
        addView(giftView, layoutParams);

        Point conOnePoint = this.mConOnePoint;
        Point conTwoPoint = this.mConTwoPoint;
        Point startPoint = this.mStartPoint;
        Point endPoint = this.mEndPoint;

        //设置属性动画
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new StarTypeEvaluator(conOnePoint, conTwoPoint), startPoint,
                endPoint);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point point = (Point) animation.getAnimatedValue();
                giftView.setX(point.x);
                giftView.setY(point.y);
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ChristmasSocksView.this.removeView(giftView);
            }
        });
        //透明度动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(giftView, "alpha", 0f, 1.0f, 0f);
        //组合动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(4000);
        animatorSet.play(valueAnimator).with(objectAnimator);
        animatorSet.start();
    }

   /* @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.moveTo(mStartPoint.x, mStartPoint.y);
        mPath.cubicTo(mConOnePoint.x, mConOnePoint.y, mConTwoPoint.x, mConTwoPoint.y, mEndPoint.x, mEndPoint.y);
        canvas.drawPath(mPath, mPaint);
    }*/


    class StarTypeEvaluator implements TypeEvaluator<Point> {

        //记录控制点
        private Point conOnePoint, conSecondPoint;

        public StarTypeEvaluator(Point conOnePoint, Point conSecondPoint) {
            this.conOnePoint = conOnePoint;
            this.conSecondPoint = conSecondPoint;
        }

        @Override
        public Point evaluate(float t, Point startValue, Point endValue) {

            //利用三阶贝塞尔曲线公式算出中间点坐标
            int x = (int) (startValue.x * Math.pow((1 - t), 3) + 3 * conOnePoint.x * t * Math.pow((1 - t), 2) + 3 *
                    conSecondPoint.x * Math.pow(t, 2) * (1 - t) + endValue.x * Math.pow(t, 3));
            int y = (int) (startValue.y * Math.pow((1 - t), 3) + 3 * conOnePoint.y * t * Math.pow((1 - t), 2) + 3 *
                    conSecondPoint.y * Math.pow(t, 2) * (1 - t) + endValue.y * Math.pow(t, 3));
            return new Point(x, y);
        }
    }

    @Override
    public void onClick(View v) {

//        mStartPoint = new Point(mScreenWidth / 2, mScreenHeight);
//        mEndPoint = new Point((int) (mScreenWidth / 2 + 150 * mRandom.nextFloat()), 0);
//        mConOnePoint = new Point((int) (mScreenWidth * mRandom.nextFloat()), (int) (mScreenHeight * 3 * mRandom.nextFloat() / 4));
//        mConTwoPoint = new Point(0, (int) (mScreenHeight * mRandom.nextFloat() / 4));
//
//        addStar();
    }

    /**
     * 监听onTouch事件，动态生成对应坐标
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
//            mStartPoint = new Point(mScreenWidth / 2, mScreenHeight);
//            mEndPoint = new Point((int) (mScreenWidth / 2 + 150 * mRandom.nextFloat()), 0);
//            mConOnePoint = new Point((int) (mScreenWidth * mRandom.nextFloat()), (int) (mScreenHeight * 3 * mRandom.nextFloat() / 4));
//            mConTwoPoint = new Point(0, (int) (mScreenHeight * mRandom.nextFloat() / 4));
            addGift("https://cdn-test.poko.app/image/common2/202012/08/1607430111009/41baea3c38d16a9b.png", 95);
        }
        return true;
    }
}
