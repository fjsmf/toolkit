package ss.com.toolkit.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import ss.com.toolkit.R;
import ss.com.toolkit.util.glide.GlideImageLoader;

/**
 * 带头像框的头像视图
 */

public class HeadViewWithFrame extends ConstraintLayout {
    private float rate = 1.1333f;
    private ImageView iv_profile, iv_beautify;
    private int profile_width;

    public HeadViewWithFrame(Context context) {
        this(context, null, 0);
    }

    public HeadViewWithFrame(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadViewWithFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.head_view, this, true);
        iv_profile = findViewById(R.id.iv_profile);
        iv_beautify = findViewById(R.id.iv_beautify);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int profile_width = (int) (width / rate);
        ViewGroup.LayoutParams lp = iv_profile.getLayoutParams();
        lp.width = lp.height = profile_width;
        iv_profile.setLayoutParams(lp);
    }

    public void setProfile(String url, String beautifyUrl, int defaultProfile) {
        if (!TextUtils.isEmpty(url)) {
            GlideImageLoader.loadCircleImage(iv_profile, url, defaultProfile);
        }
        if (!TextUtils.isEmpty(beautifyUrl)) {
            GlideImageLoader.loadImageNoPlaceholder(iv_beautify, beautifyUrl);
            iv_beautify.setVisibility(VISIBLE);
        } else {
            iv_beautify.setVisibility(INVISIBLE);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
