package ss.com.toolkit;

import android.content.Context;
import android.net.http.HttpResponseCache;
import android.os.Process;
import android.support.multidex.MultiDexApplication;

import com.apkfuns.logutils.LogLevel;
import com.apkfuns.logutils.LogUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import java.io.File;
import java.io.IOException;

public class App extends MultiDexApplication {
    private static App instance;

    public static App getInstance(){
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.tag("nadiee").d("pid:" + Process.myPid());
        // logger
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .tag("nadiee")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        LogUtils.getLogConfig().configAllowLog(true)  // Log日志开关
                .configTagPrefix("sstool")  //设置Log日志Tag前缀:用包名
                .configShowBorders(false)
                .configLevel(LogLevel.TYPE_VERBOSE);
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return true;
            }
        });
        initSVGACache();

    }

    private void initSVGACache() {
        try {
            File cacheDir = new File(getFilesDir(), "http");
            HttpResponseCache.install(cacheDir, 1024 * 1024 * 50);
        } catch (IOException mE) {
            mE.printStackTrace();
        }
    }
}
