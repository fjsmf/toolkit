package ss.com.toolkit.anim;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.opensource.svgaplayer.SVGAImageView;
import com.yasic.bubbleview.BubbleView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ss.com.toolkit.R;
import ss.com.toolkit.anim.blurview.Blurry;
import ss.com.toolkit.base.BaseActivity;
import ss.com.toolkit.view.AvatarViewWithFrame;

public class AnimActivity extends BaseActivity {
    ImageView iv_blur;
    ViewGroup content;
    boolean hasBlurred;
    @Nullable
    @BindView(R.id.headview)
    AvatarViewWithFrame headview;
    ImageView iv_christmas_socks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        BubbleView bubbleView = findViewById(R.id.bubbleview);
        bubbleView.setDefaultDrawableList();
        bubbleView.startAnimation(bubbleView.getWidth(), bubbleView.getHeight());
        iv_blur = findViewById(R.id.iv_blur);
        content = findViewById(R.id.content);
        iv_christmas_socks = findViewById(R.id.iv_christmas_socks);
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
