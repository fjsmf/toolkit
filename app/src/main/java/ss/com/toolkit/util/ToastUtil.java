package ss.com.toolkit.util;

import android.os.Build;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Toast统一管理类
 */
public class ToastUtil {

    private static boolean is711712() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1;
    }

    public static void show(final int resId) {
        if (invalidToast()) return;

        if (is711712()) {
            ToastUtilImpl711712.show(resId);
        } else {
            ToastUtilImplOther.show(resId);
        }
    }

    public static void show(final String msg) {
        if (invalidToast()) return;

        if (is711712()) {
            ToastUtilImpl711712.show(msg);
        } else {
            ToastUtilImplOther.show(msg);
        }
    }

    public static void show(final String msg, final int len) {
        if (invalidToast()) return;

        if (is711712()) {
            ToastUtilImpl711712.show(msg, len);
        } else {
            ToastUtilImplOther.show(msg, len);
        }
    }

    public static boolean invalidToast() {
        return Looper.getMainLooper() != Looper.myLooper();
    }

    public static void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public static void showToast(String msg, int len) {
        if (invalidToast()) return;

        if (is711712()) {
            ToastUtilImpl711712.showToast(msg, len);
        } else {
            ToastUtilImplOther.showToast(msg, len);
        }
    }

    public static void showShort(@StringRes int msg) {
        if (is711712()) {
            ToastUtilImpl711712.showShort(msg);
        } else {
            ToastUtilImplOther.showShort(msg);
        }
    }

    public static void showShort(String msg) {
        if (is711712()) {
            ToastUtilImpl711712.showShort(msg);
        } else {
            ToastUtilImplOther.showShort(msg);
        }
    }

    public static void showToastTop(String msg) {
        if (is711712()) {
            ToastUtilImpl711712.showToastTop(msg);
        } else {
            ToastUtilImplOther.showToastTop(msg);
        }
    }

    public static void showRedTopToast(String msg) {
        if (is711712()) {
            ToastUtilImpl711712.showRedTopToast(msg, ScreenUtil.dp2px(41));
        } else {
            ToastUtilImplOther.showRedTopToast(msg, ScreenUtil.dp2px(41));
        }
    }

    public static void showRedTopToast(String msg, int yoffset) {
        if (invalidToast()) return;

        if (is711712()) {
            ToastUtilImpl711712.showRedTopToast(msg, yoffset);
        } else {
            ToastUtilImplOther.showRedTopToast(msg, yoffset);
        }
    }

    //Toast自定义View固定布局
    public static Toast showCustomToast(int layout, int time, ToastViewCallback callback) {
        if (invalidToast()) return null;
        if (is711712()) {
            return ToastUtilImpl711712.showCustomToast(layout, time, callback);
        } else {
            return ToastUtilImplOther.showCustomToast(layout, time, callback);
        }
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
