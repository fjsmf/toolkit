package ss.com.toolkit.util.glide;

import android.app.ActivityManager;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import ss.com.toolkit.App;

/**
 * 自定义GlideModule
 */

@GlideModule
public class BaseAppGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
        App.getInstance().registerComponentCallbacks(new ComponentCallbacks2() {
            @Override
            public void onTrimMemory(int level) {
                if (glide != null) {
                    if (level == TRIM_MEMORY_UI_HIDDEN) {
                        glide.clearMemory();
                    }
                    glide.onTrimMemory(level);
                }
            }

            @Override
            public void onConfigurationChanged(Configuration newConfig) {

            }

            @Override
            public void onLowMemory() {
                if (glide != null) {
                    glide.clearMemory();
                }
            }
        });
//        glide.getRegistry().register(CustomSizeUrl.class, InputStream.class, new CustomSizeOkHttpUrlLoader.Factory(client));
    }

    /**
     * 设置内存缓存大小50M
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setLogLevel(Log.ERROR);
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2)
                .setBitmapPoolScreens(3)
                .build();

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        if (mi != null) {
            LogUtils.tag("BaseAppGlideModule").i("availMem = %s, totalMem = %s, cache:%s, bitmapPool:%s", mi.availMem/1024/1024.0, mi.totalMem/1024/1024.0, calculator.getMemoryCacheSize()/1024/1024.0, calculator.getBitmapPoolSize()/1024/1024.0);
        }
        if (mi != null) {
            if (mi.availMem > 1.5 * 1024L * 1024L * 1024L) {
                builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()));
            } else if (mi.availMem > 800 * 1024L * 1024L) {
                builder.setMemoryCache(new LruResourceCache(50 * 1024 * 1024));
            } else if (mi.availMem > 500 * 1024L * 1024L) {
                builder.setMemoryCache(new LruResourceCache(20 * 1024 * 1024));
            } else if (mi.availMem > 300 * 1024L * 1024L) {
                builder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));
            } else {
                builder.setMemoryCache(new LruResourceCache(5 * 1024 * 1024));
            }
        } else {
            builder.setMemoryCache(new LruResourceCache(20 * 1024 * 1024));
        }
        if (mi == null || mi.lowMemory || mi.availMem < 800L * 1024L * 1024L) {
            // 可用内存低于800M的
            builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_RGB_565));
        }
    }


//    @Override
//    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
//        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.readTimeout(10000, TimeUnit.SECONDS);
//        builder.writeTimeout(10000, TimeUnit.SECONDS);
//        builder.connectTimeout(10000, TimeUnit.SECONDS);
//        registry.append(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(builder.build()));
//    }

    /**
     * 关闭解析AndroidManifest
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
