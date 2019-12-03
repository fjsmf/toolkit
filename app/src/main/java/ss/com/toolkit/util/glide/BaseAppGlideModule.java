package ss.com.toolkit.util.glide;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

/**
 * 自定义GlideModule
 */

@GlideModule
public class BaseAppGlideModule extends AppGlideModule {

    /**
     * 设置内存缓存大小50M
     */
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int size = 50 * 1024 * 1024;
        builder.setMemoryCache(new LruResourceCache(size));
        builder.setLogLevel(Log.ERROR);
//        builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, size));

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        LogUtils.tag("BaseAppGlideModule").i("availMem = %s, totalMem = %s", mi.availMem, mi.totalMem);
        if (mi.availMem < 500L * 1024L * 1024L) {
            // 可用内存低于500M的
            builder.setDefaultRequestOptions(new RequestOptions().format(DecodeFormat.PREFER_RGB_565));
        }
    }

    /**
     * 关闭解析AndroidManifest
     */
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
