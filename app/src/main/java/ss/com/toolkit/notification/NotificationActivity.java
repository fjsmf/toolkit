package ss.com.toolkit.notification;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ss.com.toolkit.MainActivity;
import ss.com.toolkit.R;
import ss.com.toolkit.util.UIUtil;

public class NotificationActivity extends AppCompatActivity {
    @BindView(R.id.notification1)
    View notification1;
    @BindView(R.id.btn)
    ImageView btn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notification_activity);
        ButterKnife.bind(this);
        notification1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationActivity.this, MainActivity.class);
                NotificationManager.getInstance().showCustomNotification("title", "content", intent);
            }
        });

        int[] colors = {0xFFFF9326, 0xFFFFC54E};
        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
        drawable.setCornerRadius(25);
        drawable.setGradientType(GradientDrawable.RECTANGLE);
//        btn.setImageBitmap(UIUtil.drawableToBitmap(drawable));
        btn.setImageDrawable(drawable);


    }
}
