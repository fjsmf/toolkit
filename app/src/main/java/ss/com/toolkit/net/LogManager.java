package ss.com.toolkit.net;

import android.os.Environment;
import android.util.Log;

import com.apkfuns.logutils.LogUtils;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.toolkit.App;
import ss.com.toolkit.model.Api;
import ss.com.toolkit.model.Response.AddFeedBackRsp;
import ss.com.toolkit.model.Response.IsNeedUploadLogRsp;
import ss.com.toolkit.model.Response.LogUploadRangeRsp;
import ss.com.toolkit.retrofit.RetrofitManager;

public class LogManager {
    public static final String TAG = "LogManager";
//    public static String LOG_DIR = App.getInstance().getExternalFilesDir("log").getAbsolutePath();
public static String LOG_DIR = Environment.getExternalStorageDirectory() + "/log";
    private static LogManager instance;
    public static final long MAX_FILE_SIZE_WIFI = 4*1024*1024;
    public static final long MAX_FILE_SIZE_4G = 2*1024*1024;
    public static final long MAX_FILE_SIZE_3G = 1024*1024;
    public static final long INTERVAL_TIME_WIFI = 6; // 单位 时
    public static final long INTERVAL_TIME_4G = 24;
    public static final long INTERVAL_TIME_3G = 48;
    private static final String LOG_CONFIG = "{\n" +
            "  \"wifi\": {\n" +
            "    \"size\": 4096,\n" +
            "    \"interval\": 24\n" +
            "  },\n" +
            "  \"m4g\": {\n" +
            "    \"size\": 2048,\n" +
            "    \"interval\": 48\n" +
            "  },\n" +
            "  \"m3g\": {\n" +
            "    \"size\": 1024,\n" +
            "    \"interval\": 48\n" +
            "  }\n" +
            "}";

    public static LogManager getInstance() {
        if (instance == null) {
            synchronized (LogManager.class) {
                if (instance == null) {
                    instance = new LogManager();
                    instance.init();
                }
            }
        }
        return instance;
    }

    private void init() {
        Log.i("nadiee", "LOG_DIR："+LOG_DIR);
        File logDir = new File(LOG_DIR);
        File[] files;
        if (logDir.exists()) {
            files = logDir.listFiles();
        }
        File log = new File(Environment.getExternalStorageDirectory(), "log");
        if (log.exists()) {
            Log.i("nadiee", " log  "+log.listFiles().length);
        }
//        File file = App.getInstance().getExternalFilesDir("log");
//        if (file != null) {
//            LOG_DIR = file.getAbsolutePath();
//        }
    }

    public long getMaxFileSize() {
        return 1024;
    }

    public long getIntervalTime() {
        long intervalTime;
        return 60*60*1000;
    }

    /**
     * 压缩并上传日志
     */
    public void zipAndUploadLog() {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                try {
                    doUpload();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void doUpload() {
        addFeedBack();
    }

    public void addFeedBack() {
        RetrofitManager.getInstance().get(Api.class, "http://ffilelogapp.huya.com")
                .getAdInfo("sstest", "长连接异常", "125", "2", "6000")
                .enqueue(new Callback<AddFeedBackRsp>() {
                    @Override
                    public void onResponse(Call<AddFeedBackRsp> call, Response<AddFeedBackRsp> response) {
                        if (response == null || response.body() == null) return;
                        LogUtils.tag("nadiee").d("res:"+response.body());
                        AddFeedBackRsp addFeedBackRsp = response.body();
                        if (addFeedBackRsp != null) {
                            if (addFeedBackRsp.getResult().equals("1")
                                    && addFeedBackRsp.getIsRequireLog().equals("1")) {
                                LogUtils.tag(TAG).d("sendfeedback onResponse fbId:"+addFeedBackRsp.getFbId());
//                                getRemoteFileRange(addFeedBackRsp);
                                new UploadLogTask(addFeedBackRsp.getFbId(),
                                        addFeedBackRsp.getLogBeginTime(),
                                        addFeedBackRsp.getLogEndTime(),
                                        addFeedBackRsp.getMaxFileSize()).execute();
                                return;
                            }
                            LogUtils.tag(TAG).e("AddFeedBack onResponse， result is 0, fail;description:"+addFeedBackRsp.getDescription());
                        }
                    }
                    @Override
                    public void onFailure(Call<AddFeedBackRsp> call, Throwable t) {
                        LogUtils.tag("nadiee").d(t.getMessage());
                    }
                });
    }

    public void isNeedUploadLog() {
        RetrofitManager.getInstance().get(Api.class, "http://ffilelogapp.huya.com")
                .isNeedUploadLog("1", "123", "2", "6000")
                .enqueue(new Callback<IsNeedUploadLogRsp>() {
                    @Override
                    public void onResponse(Call<IsNeedUploadLogRsp> call, Response<IsNeedUploadLogRsp> response) {
                        LogUtils.tag("nadiee").d("res:"+response.body());

                    }
                    @Override
                    public void onFailure(Call<IsNeedUploadLogRsp> call, Throwable t) {
                        LogUtils.tag("nadiee").d(t.getMessage());
                    }
                });
    }

    public void getRemoteFileRange(AddFeedBackRsp addFeedBackRsp) {
        RetrofitManager.getInstance().get(Api.class, "http://ffilelogupload-an.huya.com")
                .getRemoteFileRange(addFeedBackRsp.getFbId())
                .enqueue(new Callback<LogUploadRangeRsp>() {
                    @Override
                    public void onResponse(Call<LogUploadRangeRsp> call, Response<LogUploadRangeRsp> response) {
                        LogUtils.tag("nadiee").d("res:"+response.body());
                        if (response == null || response.body() != null) return;
                        LogUploadRangeRsp logUploadRangeRsp = response.body();
                        if (logUploadRangeRsp.getStatus() != 1) {
                            List<String> range = logUploadRangeRsp.getRange();
                            new UploadLogTask(addFeedBackRsp.getFbId(),
                                    addFeedBackRsp.getLogBeginTime(),
                                    addFeedBackRsp.getLogEndTime(),
                                    addFeedBackRsp.getMaxFileSize(), range).execute();
                        }
                    }
                    @Override
                    public void onFailure(Call<LogUploadRangeRsp> call, Throwable t) {
                        LogUtils.tag("nadiee").d(t.getMessage());
                    }
                });
    }

    /**
     * 清理掉1天前的日志
     */
    public void clearOldLog() {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                File logdir = new File(LogManager.LOG_DIR);
                if (!logdir.exists()) return;
                File[] logfiles = logdir.listFiles();
                if (logfiles == null || logfiles.length < 1) return;
                for (File file : logfiles) {
                    if (System.currentTimeMillis() - file.lastModified() > 24 * 60 * 60 * 1000) {
                        file.delete();
                    }
                }
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    /**
     * 清理掉所有日志
     */
    public void clearAllLog() {
        Schedulers.io().scheduleDirect(new Runnable() {
            @Override
            public void run() {
                File logdir = new File(LogManager.LOG_DIR);
                if (!logdir.exists()) return;
                File[] logfiles = logdir.listFiles();
                if (logfiles == null || logfiles.length < 1) return;
                for (File file : logfiles) {
                    file.delete();
                }
            }
        });
    }

    /**
     * 1.只上传1天内的日志
     * 2.从上次上传时间后开始
     * @return
     */
  /*  public long getUploadStartTime() {
        long lastUploadTime = PrefUtil.getInstance().getLongPref("log_last_upload_file_modify_time");
        if (lastUploadTime > System.currentTimeMillis() - 24*60*60 || lastUploadTime < 0) {
            lastUploadTime = System.currentTimeMillis() - 24*60*60;
        }
        return lastUploadTime;
    }

    public void saveUploadTime(long time) {
        PrefUtil.getInstance().savePref("log_last_upload_file_modify_time", time);
    }*/
}
