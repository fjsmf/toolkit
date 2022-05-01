package ss.com.toolkit.view.expandabletextview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import ss.com.toolkit.R;


@SuppressLint("AppCompatCustomView")
public class TextViewExpandable extends TextView {
    private static final String TAG = "TextViewExpandable";
    public static String TEXT_PREFIX_EXPEND = "... ";
    public static String TEXT_EXPEND = "展开";
    private static final int DEFAULT_MAX_LINE = 3;
    /**
     * 限定行数
     */
    private int mLimitLines;
    /**
     * 是否显示展开
     */
    private boolean mExpandable;
    /**
     * 是否显示在最右侧，目前不支持
     */
    private boolean mAlwaysShowRight;
    /**
     * 展开前面的文案
     */
    private String mExpandPrefixString;
    /**
     * 展开文字
     */
    private String mExpandString;
    /**
     * 展开文字的颜色
     */
    private int mExpandTextColor;


    public TextViewExpandable(Context context) {
        this(context, null);
    }

    public TextViewExpandable(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TextViewExpandable(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.Expandable_TextView, defStyleAttr, 0);

            mLimitLines = a.getInt(R.styleable.Expandable_TextView_et_max_line, DEFAULT_MAX_LINE);
            mExpandable = a.getBoolean(R.styleable.Expandable_TextView_et_need_expand, true);
            mAlwaysShowRight = a.getBoolean(R.styleable.Expandable_TextView_et_always_showright, false);
            mExpandPrefixString = a.getString(R.styleable.Expandable_TextView_et_expand_prefix_text);
            if (TextUtils.isEmpty(mExpandPrefixString)) {
                mExpandPrefixString = TEXT_PREFIX_EXPEND;
            }
            mExpandString = a.getString(R.styleable.Expandable_TextView_et_expand_text);
            mExpandTextColor = a.getColor(R.styleable.Expandable_TextView_et_expand_color, Color.parseColor("#B2FFFFFF"));
            if (TextUtils.isEmpty(mExpandString)) {
                mExpandString = TEXT_EXPEND;
            }
            a.recycle();
        }
    }

    public void setTextContent(String text) {
        setText(text);
        if (mExpandable) {
            post(new LineContent(this, text));
        }
    }

    private class LineContent implements Runnable {
        private TextView mTarget;
        private String mContent;

        public LineContent(TextView mTarget, String mContent) {
            this.mTarget = mTarget;
            this.mContent = mContent;
        }

        public void run() {
            if (null != mTarget && !TextUtils.isEmpty(mContent)) {
                Layout layout = mTarget.getLayout();
                if (layout != null && mTarget.getLineCount() > mLimitLines) {
                    SpannableString ss = new SpannableString(String.format("%s%s%s", mContent.substring(0, layout.getLineEnd(mLimitLines - 1) - 6), mExpandPrefixString, mExpandString));
                    ss.setSpan(new ForegroundColorSpan(mExpandTextColor), ss.length() - mExpandString.length(), ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    mTarget.setText(ss);
                }
            }
        }
    }
}
