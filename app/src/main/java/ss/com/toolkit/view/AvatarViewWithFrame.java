package ss.com.toolkit.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.apkfuns.logutils.LogUtils;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;

import ss.com.toolkit.R;
import ss.com.toolkit.util.glide.GlideImageLoader;

/**
 * 带头像框的头像视图
 */

public class AvatarViewWithFrame extends ConstraintLayout {
    private static final String TAG = "HeadViewWithFrame";
    private float DEFAULT_RATIO = 0.88f, avatarRatio;
    private ImageView iv_profile, iv_beautify;
    private SVGAImageView svga;
    private int avatarDiameter;

    public AvatarViewWithFrame(Context context) {
        this(context, null, 0);
    }

    public AvatarViewWithFrame(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarViewWithFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.avatarFrame);
        avatarRatio = array.getFloat(R.styleable.avatarFrame_avatar_ratio, DEFAULT_RATIO);
        array.recycle();

        LayoutInflater.from(context).inflate(R.layout.head_view, this, true);
        iv_profile = findViewById(R.id.iv_profile);
        LogUtils.tag(TAG).d("avatarRatio : " + avatarRatio);
        iv_beautify = findViewById(R.id.iv_beautify);
        svga = findViewById(R.id.svga);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (avatarDiameter <= 0) {
            avatarDiameter = (int) (MeasureSpec.getSize(heightMeasureSpec) * avatarRatio);
            LogUtils.tag(TAG).d("diameter : " + avatarDiameter + ", height:" + MeasureSpec.getSize(heightMeasureSpec) + ", width:" + MeasureSpec.getSize(widthMeasureSpec));
            ViewGroup.LayoutParams lp = iv_profile.getLayoutParams();
            lp.width = lp.height = avatarDiameter;
            iv_profile.setLayoutParams(lp);
        }
    }
//
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        ViewGroup.LayoutParams lp = iv_profile.getLayoutParams();
//        lp.width = lp.height = avatarDiameter;
//        iv_profile.setLayoutParams(lp);
//
//        int childLeft = left + (right - left - avatarDiameter) / 2;
//        int childTop = top + (bottom - top - avatarDiameter) / 2;
//        int childRight = childLeft + avatarDiameter;
//        int childBottom = childTop + avatarDiameter;
//        iv_profile.layout(childLeft, childTop, childRight, childBottom);
//    }

    public void setProfile(String url, String beautifyUrl, int defaultProfile) {
        setProfile(url, defaultProfile, beautifyUrl, null);
    }

    public void setProfile(String url, int defaultProfile, String beautifyUrl, String svgaUrl) {
        if (!TextUtils.isEmpty(url)) {
            GlideImageLoader.loadCircleImage(iv_profile, url, defaultProfile);
        }
        svgaUrl = "https://cdn-test.poko.app/file/common/202011/02/1604303235889/head_frame_dynamic.svga";
        if (!TextUtils.isEmpty(svgaUrl)) {
            SVGAParser parser = new SVGAParser(getContext());
//            parser.decodeFromAssets("head_frame_dynamic.svga", new SVGAParser.ParseCompletion() {
//                @Override
//                public void onError() {
//
//                }
//
//                @Override
//                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
//                    LogUtils.tag(TAG).d("onComplete");
//                    SVGADrawable drawable = new SVGADrawable(videoItem);
//                    svga.setImageDrawable(drawable);
//                    svga.startAnimation();
//                }
//            });
            try {
                parser.decodeFromURL(new URL(svgaUrl), new SVGAParser.ParseCompletion() {

                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                        svga.setVisibility(VISIBLE);
                        SVGADrawable drawable = new SVGADrawable(videoItem);
                        svga.setImageDrawable(drawable);
                        svga.startAnimation();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (!TextUtils.isEmpty(beautifyUrl)) {
            GlideImageLoader.loadImageNoPlaceholder(iv_beautify, beautifyUrl);
            iv_beautify.setVisibility(VISIBLE);
        } else {
            iv_beautify.setVisibility(INVISIBLE);
            svga.setVisibility(INVISIBLE);
        }
    }
}
