package ss.com.toolkit;

import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yasic.bubbleview.BubbleView;

public class AnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        BubbleView bubbleView = findViewById(R.id.bubbleview);
        bubbleView.setDefaultDrawableList();
        bubbleView.startAnimation(bubbleView.getWidth(), bubbleView.getHeight());
    }
}
