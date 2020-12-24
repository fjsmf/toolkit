package ss.com.toolkit.util;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import ss.com.toolkit.App;
import ss.com.toolkit.R;

/**
 * Toast统一管理类
 */
public class ToastUtil1 {

    private static Toast mTopToast;
    private static Toast mBottomToast;
    private static Toast mCustomToast;

    private static Context mContext = App.getInstance().getApplicationContext();

    private static class SafeHandler extends Handler {
        Handler inner;

        SafeHandler(Handler inner) {
            this.inner = inner;
        }

        @Override
        public void handleMessage(Message msg) {
            try {
                if (inner != null) {
                    inner.handleMessage(msg);
                }
            } catch (Exception ignore) {

            }
        }

        @Override
        public void dispatchMessage(Message msg) {
            if (inner != null) {
                inner.dispatchMessage(msg);
            }
        }
    }

    private static void fixToastBadTokenException(Toast toast) {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1 || Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            try {
                Field mTN = toast.getClass().getDeclaredField("mTN");
                mTN.setAccessible(true);
                Object tn = mTN.get(toast);
                Field mHandler = tn.getClass().getDeclaredField("mHandler");
                mHandler.setAccessible(true);
                Handler handler = (Handler) mHandler.get(tn);
                mHandler.set(tn, new SafeHandler(handler));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void show(final int resId) {
        if (invalidToast()) return;

        if (mBottomToast != null) {
            mBottomToast.cancel();
        }

        mBottomToast = Toast.makeText(mContext, resId, Toast.LENGTH_SHORT);
        fixToastBadTokenException(mBottomToast);
        mBottomToast.show();
    }

    public static void show(final String msg) {
        if (invalidToast()) return;

        if (mBottomToast != null) {
            mBottomToast.cancel();
        }

        mBottomToast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        fixToastBadTokenException(mBottomToast);
        mBottomToast.show();
    }

    public static void show(final String msg, final int len) {
        if (invalidToast()) return;

        if (mBottomToast != null) {
            mBottomToast.cancel();
        }

        mBottomToast = Toast.makeText(mContext, msg, len);
        fixToastBadTokenException(mBottomToast);
        mBottomToast.show();
    }

    public static boolean invalidToast() {
        return Looper.getMainLooper() != Looper.myLooper();
    }

    public static void showView(final View view, final int len) {
        if (invalidToast()) return;

        if (mCustomToast != null) {
            mCustomToast.cancel();
        }

        mCustomToast = new Toast(mContext);
        mCustomToast.setView(view);
        mCustomToast.setDuration(len);
        fixToastBadTokenException(mCustomToast);
        mCustomToast.show();
    }

    public static void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public static void showToast(String msg, int len) {
        if (invalidToast()) return;

        if (mBottomToast != null) {
            mBottomToast.cancel();
        }

        mBottomToast = Toast.makeText(mContext, msg, len);
        fixToastBadTokenException(mBottomToast);
        mBottomToast.show();
    }

    public static void showShort(@StringRes int msg) {
        showToast(mContext.getString(msg));
    }

    public static void showShort(String msg) {
        showToast(msg);
    }

    public static void showToastTop(String msg) {
        if (invalidToast()) return;

        if (mTopToast != null) {
            mTopToast.cancel();
        }

        mTopToast = Toast.makeText(mContext, msg, Toast.LENGTH_LONG);
        fixToastBadTokenException(mTopToast);
        mTopToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, ScreenUtil.dp2px(60));
        mTopToast.show();
    }

    public static void showRedTopToast(String msg) {
        showRedTopToast(msg, ScreenUtil.dp2px(41));
    }

    public static void showRedTopToast(String msg, int yoffset) {
        if (invalidToast()) return;

        if (mCustomToast != null) {
            mCustomToast.cancel();
        }

        mCustomToast = new Toast(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.red_toast_view, null);
        TextView textView = view.findViewById(R.id.tv_toast);
        textView.setText(msg);
        mCustomToast.setView(view);
        mCustomToast.setDuration(Toast.LENGTH_SHORT);
        mCustomToast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, yoffset);
        fixToastBadTokenException(mCustomToast);
        mCustomToast.show();
    }

    public static void showGreenTopToast(String msg) {
        if (invalidToast()) return;

        if (mCustomToast != null) {
            mCustomToast.cancel();
        }

        mCustomToast = new Toast(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.red_toast_view, null);
        view.findViewById(R.id.layout_view).setBackgroundResource(R.color.red_color);
        TextView textView = view.findViewById(R.id.tv_toast);
        textView.setText(msg);
        mCustomToast.setView(view);
        mCustomToast.setDuration(Toast.LENGTH_SHORT);
        mCustomToast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.TOP, 0, ScreenUtil.dp2px(40));
        fixToastBadTokenException(mCustomToast);
        mCustomToast.show();
    }

    //Toast自定义View固定布局
    public static Toast showCustomToast(int layout, int time, ToastViewCallback callback) {
        if (invalidToast()) return null;

        LayoutInflater inflater = LayoutInflater.from(App.getInstance());
        View view = inflater.inflate(layout, null);
        if (callback != null) {
            callback.onViewCreated(view);
        }
        Toast toast = new Toast(App.getInstance());
        setClickable(toast);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(time);
        toast.setView(view);
        fixToastBadTokenException(toast);
        toast.show();
        return toast;
    }

    public interface ToastViewCallback {
        void onViewCreated(View view);
    }

    public static void setClickable(Toast toast) {
        try {
            Object mTN;
            mTN = getField(toast, "mTN");
            if (mTN != null) {
                Object mParams = getField(mTN, "mParams");
                if (mParams != null
                        && mParams instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
                    //显示与隐藏动画
//                    params.windowAnimations = R.style.ClickToast;
                    //Toast可点击
                    params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

                    //设置viewgroup宽高
//                    params.width = WindowManager.LayoutParams.MATCH_PARENT; //设置Toast宽度为屏幕宽度
//                    params.height = WindowManager.LayoutParams.WRAP_CONTENT; //设置高度
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射字段
     *
     * @param object    要反射的对象
     * @param fieldName 要反射的字段名称
     */
    private static Object getField(Object object, String fieldName)
            throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        if (field != null) {
            field.setAccessible(true);
            return field.get(object);
        }
        return null;
    }


}
