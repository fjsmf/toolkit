package ss.com.toolkit.util.glide;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;

import ss.com.toolkit.App;

/**
 * Glide加载图片
 */

public class GlideImageLoader {

    public static DrawableTransitionOptions normalTransitionOptions = new DrawableTransitionOptions()
            .crossFade();

    public static void loadImage(final ImageView imageView, Object o) {
        GlideApp.with(App.getInstance())
                .load(o)
                .placeholder(new ColorDrawable(Color.rgb(0xe4, 0xe4, 0xe4)))//加载的时候占位
                .error(new ColorDrawable(Color.rgb(0xe4, 0xe4, 0xe4)))//请求资源失败的时候
                .fallback(new ColorDrawable(Color.rgb(0xe4, 0xe4, 0xe4)))//当请求内容为null的时候显示
                .into(imageView);
    }


    public static void loadImageNoPlaceholder(final ImageView imageView, String url) {
        GlideApp.with(App.getInstance())
                .load(url)
                .into(imageView);
    }

    public static void loadImageNoPlaceholder(final ImageView imageView, int defRes) {
        GlideApp.with(App.getInstance())
                .load(defRes)
                .into(imageView);
    }


    public static void loadImage(final ImageView imageView, String url, int defRes) {
        GlideApp.with(App.getInstance())
                .load(url)
//                .transition(normalTransitionOptions)
                .placeholder(defRes)
                .error(defRes)
                .into(imageView);
    }

    public static void loadCircleImage(ImageView imageView, int res) {
        GlideApp.with(App.getInstance())
                .load(res)
                .transition(normalTransitionOptions)
                .circleCrop()
                .into(imageView);
    }

    public static void loadCircleImage(final ImageView imageView, String url, int defRes) {
        loadCircleImage(imageView, url, defRes, defRes);
    }

    public static void loadCircleImage(final ImageView imageView, int res, int defRes) {
        loadCircleImage(imageView, res, defRes, defRes);
    }

    // 加载圆形图片
    public static void loadCircleImage(final ImageView imageView, String url, int defRes, int errorRes) {
        GlideApp.with(App.getInstance())
                .load(url)
//                .transition(normalTransitionOptions)
                .circleCrop()
                .placeholder(defRes)
                .error(errorRes)
                .into(imageView);
    }

    // 加载圆形图片
    public static void loadCircleImage(final ImageView imageView, int res, int defRes, int errorRes) {
        GlideApp.with(App.getInstance())
                .load(res)
                .transition(normalTransitionOptions)
                .circleCrop()
                .placeholder(defRes)
                .error(errorRes)
                .into(imageView);
    }

    public static void loadCircleImage(final ImageView imageView, String url, int defRes, RequestListener listener) {
        GlideApp.with(App.getInstance())
                .load(url)
                .transition(normalTransitionOptions)
                .circleCrop()
                .placeholder(defRes)
                .error(defRes)
//                .listener(listener)
                .into(imageView);
    }


    // 加载圆形图片
    public static void loadImage(final ImageView imageView, File file) {
        GlideApp.with(App.getInstance())
                .load(file)
                .transition(normalTransitionOptions)
                .into(imageView);
    }
}
