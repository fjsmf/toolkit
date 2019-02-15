package ss.com.toolkit.anim;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yasic.bubbleview.BubbleView;

import ss.com.toolkit.R;
import ss.com.toolkit.anim.blurview.Blurry;

public class AnimActivity extends AppCompatActivity {
    ImageView iv_blur;
    ViewGroup content;
    boolean hasBlurred;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        BubbleView bubbleView = findViewById(R.id.bubbleview);
        bubbleView.setDefaultDrawableList();
        bubbleView.startAnimation(bubbleView.getWidth(), bubbleView.getHeight());
        iv_blur = findViewById(R.id.iv_blur);
        content = findViewById(R.id.content);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Blurry.with(AnimActivity.this).sampling(5).onto(findViewById(R.id.content));
            }
        }, 200);
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
