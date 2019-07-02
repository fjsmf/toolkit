package ss.com.toolkit.net;

import android.util.Log;

import com.apkfuns.logutils.LogUtils;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.toolkit.model.Api;
import ss.com.toolkit.retrofit.RetrofitManager;
import ss.com.toolkit.util.EncryptUtil;

/**
 * Created by yuhui2 on 2017/8/9.
 */

public class UploadLogTask {
    private static final int DEFAULT_CHUNK_SIZE = 128 * 1024;//128K
    private static final int READ_TIMEOUT = 15 * 1000;//15s
    private static final String TAG = "UploadLogTask";
    private static final int UPLOAD_FAILED = 0;
    private static final int UPLOAD_SUCCEED = 1;
    private static final int UPLOAD_PARTIAL = 2;
    private String mFbId;
    private long mLogBeginTime;
    private long mLogEndTime;
    private long mMaxFileSize;
    private List<String> mRange;

    public UploadLogTask(String fbId, long logBeginTime, long logEndTime, long maxFileSize, List<String> range) {
        this.mFbId = fbId;
        this.mLogBeginTime = logBeginTime;
        this.mLogEndTime = logEndTime;
        this.mMaxFileSize = maxFileSize;
        this.mRange = range;
    }

    public UploadLogTask(String fbId, long logBeginTime, long logEndTime, long maxFileSize) {
        this(fbId, logBeginTime, logEndTime, maxFileSize, null);
    }

    public void execute() {
        uploadLogFile();
    }


    public void uploadLogFile() {
        try {
            //如果本地没有fbid对应的log文件，而服务器有，则证明用户已经清掉原来的文件或者换了手机登录
            //这种情况不再上传新的文件
            /*if (isNeedIgnoreCurrentUpload()) {
                return;
            }*/
            final File file = LogHelper.getLogByTime(false, mFbId, 0, mLogEndTime);
            //如果客户端上传的文件大于mMaxFileSize，就不用上传该文件
            if (file == null || file.length() > mMaxFileSize) {
                LogUtils.tag("feedback").d( "file is null or size is over mMaxFileSize, so drop this upload");
                return;
            }

            String md5 = EncryptUtil.encryptFileMD5(file);
            doUploadZipFile(file, md5);
           /* int chunkNum = (int) (file.length() / DEFAULT_CHUNK_SIZE)
                    + (file.length() % DEFAULT_CHUNK_SIZE > 0 ? 1 : 0);
            LogUtils.tag("feedback").d("file %s is divided into %s chunks", file.getName(), chunkNum);
            final boolean[] noNeedUpLoadChunks = getNoNeedUploadChunks(chunkNum);

            //实现分片上传
            for (int index = 0; index < chunkNum; index++) {
                final int curIndex = index;
                //如果该分片之前已经上传到服务器，不需要再次上传
                if (noNeedUpLoadChunks[index] == true) {
                    continue;
                }
                long start = (long) (index) * DEFAULT_CHUNK_SIZE;
                int bufferSize = (int) Math.min(file.length() - start, DEFAULT_CHUNK_SIZE);
                if (bufferSize <= 0) {
                    LogUtils.tag("feedback").d( "bufferSize <= 0");
                    break;
                }
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(start);
                byte[] chunk = new byte[bufferSize];
                int length = randomAccessFile.read(chunk);
                if (length == -1) {
                    LogUtils.tag("feedback").d( "read chunk failed");
                    break;
                }
                StringBuilder mUrl = new StringBuilder(LogAutoAnalyzeConstants.LOG_UPLOAD_URL).append("?")
                        .append(LogAutoAnalyzeConstants.KEY_LOG_FBID).append("=").append(mFbId).append("&")
                        .append(LogAutoAnalyzeConstants.KEY_LOG_ISRELOAD).append("=").append("0").append("&")
                        .append(LogAutoAnalyzeConstants.KEY_LOG_MD5).append("=").append(md5).append("&")
                        .append(LogAutoAnalyzeConstants.KEY_LOG_FILESIZE).append("=").append(file.length()).append("&")
                        .append(LogAutoAnalyzeConstants.KEY_LOG_BEGIN_POSITION).append("=").append(String.valueOf(start));
                BoundaryFunction.RequestParams params = new BoundaryFunction.RequestParams();
                params.put("file", new ByteArrayInputStream(chunk), file.getName(), URLConnection.guessContentTypeFromName(file.getName()));
                DownloadTask uploadLogTask = new DownloadTask(mUrl.toString()) {
                    @Override
                    protected void onResponse(boolean success, BoundaryFunction.RequestParams params, final String response) {
                        if (success) {
                            LogUploadRsp logUploadRsp = JsonUtils.parseJson(response, LogUploadRsp.class);
                            if (logUploadRsp != null) {
                                switch (logUploadRsp.getResult()) {
                                    case UPLOAD_FAILED:
                                        LogUtils.tag("feedback").d("file %s is uploaded failed, %s", file.getName(), logUploadRsp.getDescription());
                                        break;
                                    case UPLOAD_SUCCEED:
                                        LogUtils.tag("feedback").d("file %s is upload succeed to %s, now is to delete it", file.getName(), logUploadRsp.getUrl());
                                        boolean isDelete = file.delete();
                                        LogUtils.tag("feedback").d("file %s is partial uploaded", file.getName());
                                        synchronized (noNeedUpLoadChunks) {
                                            noNeedUpLoadChunks[curIndex] = true;
                                        }
                                        if (null != mProgressListener) {
                                            mProgressListener.progress(file.length(), file.length());
                                        }
                                        LogUtils.tag("feedback").d("file %s is deleted %s", file.getName(), isDelete ? "succeed" : "failed");
                                        break;
                                    case UPLOAD_PARTIAL:
                                        LogUtils.tag("feedback").d("file %s is partial uploaded", file.getName());
                                        long finishedSize;
                                        synchronized (noNeedUpLoadChunks) {
                                            noNeedUpLoadChunks[curIndex] = true;
                                            finishedSize = calcFinisthedSize(noNeedUpLoadChunks);
                                        }
                                        if (null != mProgressListener) {
                                            mProgressListener.progress(finishedSize, file.length());
                                        }
                                        break;
                                }
                            }
                        } else {
                            if (null != mProgressListener) {
                                mProgressListener.fail();
                            }
                            LogUtils.tag("feedback").d( "uploadLogTask is failed, %s", response);
                        }
                    }
                }.setReadTimeout(READ_TIMEOUT);
                uploadLogTask.runPost(params);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LogUtils.tag("feedback").d( "log file not found error");*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doUploadZipFile(File uploadFile, String md5) {
        RequestBody fileRQ = RequestBody.create(MediaType.parse("multipart/form-data"), uploadFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("picture", uploadFile.getName(), fileRQ);

        RequestBody body=new MultipartBody.Builder()
                .addFormDataPart("fbId", mFbId)
                .addFormDataPart("fileSize",String.valueOf(uploadFile.length()))
                .addFormDataPart("beginPos", "0")
                .addFormDataPart("header",uploadFile.getName(), fileRQ)
                .build();
        Call<ResponseBody> uploadCall = RetrofitManager.getInstance()
                .get(Api.class, "http://ffilelogupload-an.huya.com")
                .upload(body);
        uploadCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("upload", response.isSuccessful() + "");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });



      /*  // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), uploadFile);
        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("txt", uploadFile.getName(), requestFile);
        // add another part within the multipart request
        String descriptionString = "hello, this is description speaking";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

        // finally, execute the request
        Call<ResponseBody> call = RetrofitManager.getInstance().get(Api.class, "http://ffilelogupload-an.huya.com")
                .upload(description, body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });*/
    }

 /*   private boolean isNeedIgnoreCurrentUpload() {
        boolean isRemoteFileHasRange = false;
        if (mRange != null && mRange.size() > 0) {
            for (String rangeUnit : mRange) {
                long chunkEndPos = DecimalUtils.safelyParseLong(rangeUnit.substring(rangeUnit.indexOf("-") + 1), 0);
                if (chunkEndPos > 0) {
                    isRemoteFileHasRange = true;
                    break;
                }
            }
        }
        LogUtils.tag("feedback").d("isRemoteFileHasRange: %s", isRemoteFileHasRange);
        return !LogHelper.isFeedBackLogFileExists(mFbId) && isRemoteFileHasRange;
    }*/


   /* private boolean[] getNoNeedUploadChunks(int chunkNum) {
        boolean[] chuckNotNeedUpLoad = new boolean[chunkNum];
        for (int i = 0; i < chunkNum; i++) {
            chuckNotNeedUpLoad[i] = false;
        }

        if (mRange != null && mRange.size() > 0) {
            for (String rangeUnit : mRange) {
                long chunkStartPos = DecimalUtils.safelyParseLong(rangeUnit.substring(0, rangeUnit.indexOf("-")), 0);
                long chunkEndPos = DecimalUtils.safelyParseLong(rangeUnit.substring(rangeUnit.indexOf("-") + 1), 0);
                if (chunkEndPos > 0) {
                    int start = (int) chunkStartPos / DEFAULT_CHUNK_SIZE;
                    int end = (int) (chunkEndPos / DEFAULT_CHUNK_SIZE);
                    if (start >= 0) {
                        for (int dex = start; dex <= end && dex < chunkNum; dex++) {
                            LogUtils.tag("feedback").d("fbId %s 's chunk %s has been uploaded before", mFbId, dex);
                            chuckNotNeedUpLoad[dex] = true;
                        }
                    }
                }
            }
        }
        return chuckNotNeedUpLoad;
    }*/

    public interface IProgressListener {
        void progress(long finishedSize, long totalSize);

        void fail();
    }

    IProgressListener mProgressListener = null;

    public void regProgressListener(IProgressListener l) {
        mProgressListener = l;
    }

    public void unregProgressListener() {
        mProgressListener = null;
    }

    private static long calcFinisthedSize(boolean[] a) {
        long result = 0;
        for (boolean b : a) {
            if (b) {
                result += DEFAULT_CHUNK_SIZE;
            }
        }
        return result;
    }
}
