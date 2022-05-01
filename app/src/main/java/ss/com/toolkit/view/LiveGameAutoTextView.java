package ss.com.toolkit.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.apkfuns.logutils.LogUtils;

public class LiveGameAutoTextView extends AutoTextView {

    private Runnable mRunnable;
    private static final long INTERVAL_TIME = 5000;
    private CharSequence[] mTexts;
    private int mIndex = 0;
    public LiveGameAutoTextView(Context context) {
        super(context);
    }

    public LiveGameAutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setText(CharSequence[] texts) {
        if (texts != null && texts.length > 0) {
            mTexts = texts;
            setText(texts[mIndex = 0]);
        }
    }
    
    private CharSequence getNextText() {
        if (mTexts == null) {
            mIndex = 0;
            return "";
        } else if (mTexts.length == 1) {
            return mTexts[mIndex = 0];
        } else {
            return mTexts[mIndex = mIndex == 0 ? 1 : 0];
        }
    }

    private CharSequence getFirstText() {
        mIndex = 0;
        if (mTexts != null && mTexts.length > 0) {
            return mTexts[0];
        }
        return "";
    }

    public void setAutoScroll(boolean autoScroll) {
        if (autoScroll) {
            autoScrollText();
        } else {
            removeCallbacks(mRunnable);
            setText(getFirstText());
        }
    }

    private void autoScrollText() {
        if (mRunnable == null) {
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    CharSequence txt = getNextText();
                    setText(txt);
                    autoScrollText();
                }
            };
        }
        postDelayed(mRunnable, INTERVAL_TIME);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(mRunnable);
    }
}
