package ss.com.toolkit.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.opensource.svgaplayer.SVGAImageView;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.yasic.bubbleview.BubbleView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ss.com.toolkit.R;
import ss.com.toolkit.anim.blurview.Blurry;
import ss.com.toolkit.anim.scrolllist.LooperLayoutManager;
import ss.com.toolkit.anim.scrolllist.MyAdapter;
import ss.com.toolkit.base.BaseActivity;
import ss.com.toolkit.util.ScreenUtil;
import ss.com.toolkit.view.AvatarViewWithFrame;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class AnimActivity extends BaseActivity {
    ImageView iv_blur;
    ViewGroup content;
    boolean hasBlurred;
    @Nullable
    @BindView(R.id.headview)
    AvatarViewWithFrame headview;
    ImageView iv_christmas_socks;
//    @BindView(R.id.layout1)
    View layout1;
//    @BindView(R.id.tv_1)
    View tv_1;
    View mSubscribeLayout, subText;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        BubbleView bubbleView = findViewById(R.id.bubbleview);
        bubbleView.setDefaultDrawableList();
        bubbleView.startAnimation(bubbleView.getWidth(), bubbleView.getHeight());
        iv_blur = findViewById(R.id.iv_blur);
        content = findViewById(R.id.content);
        layout1 = findViewById(R.id.layout1);
        tv_1 = findViewById(R.id.tv_1);
        iv_christmas_socks = findViewById(R.id.iv_christmas_socks);
        mSubscribeLayout = findViewById(R.id.sub_layout);
        subText = findViewById(R.id.tv_sub);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(layout1, View.TRANSLATION_X, 0, tv_1.getWidth());
        ObjectAnimator alpha = ObjectAnimator.ofFloat(tv_1, "alpha", 1.0f, 0f);
        AnimatorSet set = new AnimatorSet();
        //动画监听
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                tv_1.setVisibility(GONE);
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        set.setDuration(1500);
        set.play(translationX).with(alpha);
        set.start();
//        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                ViewTransformManager.Companion.getInstance().setMSrcX(200);
//                ViewTransformManager.Companion.getInstance().setMSrcY(200);
//                ViewTransformManager.Companion.getInstance().setMSrcWidth(100);
//                ViewTransformManager.Companion.getInstance().setMSrcHeight(100);
//                ViewTransformManager.Companion.getInstance().doTransformAnim(AnimActivity.this, getWindow().getDecorView());
//            }
//        });

        LogUtils.tag("nadiee").d("start");
        Observable.intervalRange(1, 100, 0, 10, TimeUnit.MILLISECONDS)
//                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Consumer<Long>() {
                               @Override
                               public void accept(Long aLong) throws Exception {
                                   LogUtils.tag("nadiee").d(aLong);
                               }
                           }
                );
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Blurry.with(AnimActivity.this).sampling(5).onto(findViewById(R.id.content));
//            }
//        }, 200);

//        headview.setProfile("https://cdn-test.poko.app/image/face/201906/14/1560479203712/91944137-200.png", R.drawable.emoji01_03,
//                "https://cdn-test.poko.app/image/common2/201910/30/1572407150783/841913d48fa925b8.png", "https://github.com/yyued/SVGA-Samples/blob/master/posche.svga?raw=true");

//        svga_combo_label.startAnimation();
        AndroidSchedulers.mainThread().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                Path path = new Path();
                path.moveTo(0, 0);
                path.cubicTo(0.2f, 0f, 0.1f, 1f, 0.5f, 1f);
                path.lineTo(1f, 1f);
                ObjectAnimator animator = ObjectAnimator.ofFloat(iv_christmas_socks, View.TRANSLATION_X, 500);
                animator.setInterpolator(PathInterpolatorCompat.create(path));
                animator.start();
            }
        }, 2000, TimeUnit.MICROSECONDS);

        View tv_sub_btn = findViewById(R.id.tv_sub_btn);
        tv_sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSubscribeLayout.isSelected()) {
                    setSubscribeVisible(true);
                } else {
                    setSubscribeVisible(false);
                }
                mSubscribeLayout.setSelected(!mSubscribeLayout.isSelected());
            }
        });

        MarqueeView marquee_view = findViewById(R.id.marquee_view);
        for (int i = 0; i< 2; i++) {
            TextView tv = new TextView(this);
            tv.setText("this is marquee view");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.check, 0, 0, 0);
            }
            marquee_view.addViewInQueue(tv);
        }
        TextView tv = new TextView(this);
        tv.setText("hello, world!");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.check, 0, 0, 0);
        }
        marquee_view.addViewInQueue(tv);

        marquee_view.setScrollSpeed(30);
        marquee_view.setScrollDirection(MarqueeView.RIGHT_TO_LEFT);
        marquee_view.setViewMargin(ScreenUtil.dp2px(10));
        marquee_view.startScroll();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(new MyAdapter());
        LooperLayoutManager layoutManager = new LooperLayoutManager();
        layoutManager.setLooperEnable(true);
        recyclerView.setLayoutManager(layoutManager);
    }



    private void setSubscribeVisible(boolean show) {
        if (show) {
            subText.setVisibility(VISIBLE);
        }
        ObjectAnimator translationX = show ? ObjectAnimator.ofFloat(mSubscribeLayout, View.TRANSLATION_X, subText.getWidth(), 0)
                : ObjectAnimator.ofFloat(mSubscribeLayout, View.TRANSLATION_X, 0,  subText.getWidth());
        ObjectAnimator alpha = show ? ObjectAnimator.ofFloat(subText, "alpha", 0f, 1f)
                :ObjectAnimator.ofFloat(subText, "alpha", 1.0f, 0f);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        //动画监听
        mAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!show) {
                    subText.setVisibility(GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimatorSet.setDuration(300);
        mAnimatorSet.playTogether(translationX, alpha);
        mAnimatorSet.start();
    }

    public void showblur(View view) {
        if (hasBlurred) {
            Blurry.delete(content);
        }
        hasBlurred = true;
        Blurry.with(this)
                .radius(25)
                .sampling(1)
                .color(Color.argb(66, 255, 255, 255))
                .async()
                .capture(content)
                .into(iv_blur);
        MyFragment fragment
                = new MyFragment();
        fragment.show(getSupportFragmentManager(), "blur_sample");
    }
}
