package ss.com.toolkit.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.yasic.bubbleview.BubbleView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import ss.com.toolkit.R;
import ss.com.toolkit.anim.blurview.Blurry;
import ss.com.toolkit.base.BaseActivity;
import ss.com.toolkit.view.AvatarViewWithFrame;

public class PathActivity extends BaseActivity {
    ImageView iv_christmas_socks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        iv_christmas_socks = findViewById(R.id.iv_christmas_socks);
        iv_christmas_socks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Path path = new Path();
//                path.cubicTo(0.2f, 0f, 0.1f, 1f, 0.5f, 1f);
//                path.lineTo(1f, 1f);
//
//                ObjectAnimator animator = ObjectAnimator.ofFloat(iv_christmas_socks, View.TRANSLATION_X, 500);
//                animator.setInterpolator(PathInterpolatorCompat.create(path));
//                animator.start();
//                ObjectAnimator animation = ObjectAnimator.ofFloat(v, "translationX",  100f);
//                animation.setDuration(2000);
//                animation.start();
               /* Animation animation = new Animation() {
                    @Override
                    protected void applyTransformation(float interpolatedTime, Transformation t) {
                        super.applyTransformation(interpolatedTime, t);
                        iv_christmas_socks.setAlpha(interpolatedTime);
                    }
                };

                //define the CubicBezierInterpolator
                Interpolator easeInOut = new CubicBezierInterpolator(.1, .7, .1, 1);
                animation.setInterpolator(easeInOut);

                animation.setDuration(3000);
                iv_christmas_socks.startAnimation(animation);*/

                final Path path = new Path();
                path.lineTo(0.25f, 0.25f);
                path.lineTo(0.5f, -0.25f);
                path.lineTo(0.7f, 0.5f);
                path.lineTo(0.9f, -0.75f);
                path.lineTo(1f, 1f);
                // Create the ObjectAnimatpr
                ObjectAnimator animator = ObjectAnimator.ofFloat(iv_christmas_socks,
                        View.TRANSLATION_Y,
                        -1,
                        0);
                animator.setRepeatCount(ObjectAnimator.INFINITE);
                animator.setRepeatMode(ObjectAnimator.REVERSE);
                animator.setDuration(500);
                // Use the PathInterpolatorCompat
                animator.setInterpolator(PathInterpolatorCompat.create(path));
            }
        });
        iv_christmas_socks.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onLongClick(View v) {
                Path path = new Path();
                path.cubicTo(0.2f, 0f, 0.1f, 1f, 0.5f, 1f);
                path.lineTo(1f, 1f);
                ObjectAnimator animator = ObjectAnimator.ofFloat(iv_christmas_socks, View.TRANSLATION_X, 150);
//                animator.setInterpolator(PathInterpolatorCompat.create(path));
//                animator.start();
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(iv_christmas_socks, View.TRANSLATION_Y, 150);
                AnimatorSet set = new AnimatorSet();
                set.playTogether(animator, animator1);
                animator.setInterpolator(PathInterpolatorCompat.create(path));
                animator1.setInterpolator(PathInterpolatorCompat.create(path));
                set.setDuration(1000);
                set.start();
                return true;
            }
        });
    }

}
