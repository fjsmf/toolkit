package ss.com.toolkit.model.Response;

import android.support.annotation.Keep;

import java.util.Arrays;

/**
 * Created by yuhui2 on 2017/8/11.
 */
@Keep
public class IsNeedUploadLogRsp  {

    private FeedbackData[] feedback;
    private int isRequireLog;
    private String serverTime;
    private long maxFileSize;

    public static class FeedbackData   {
        String fbId;
        int isRequireSupplementary;
        long logBeginTime;
        long logEndTime;
        String logNameList;
        String fileType;


        public String getFbId() {
            return fbId;
        }

        public void setFbId(String fbId) {
            this.fbId = fbId;
        }

        public String getFileType() {
            return fileType;
        }

        public void setFileType(String fileType) {
            this.fileType = fileType;
        }

        public int getIsRequireSupplementary() {
            return isRequireSupplementary;
        }

        public boolean isRequireSupplementary() {
            return isRequireSupplementary == 1;
        }

        public void setIsRequireSupplementary(int isRequireSupplementary) {
            this.isRequireSupplementary = isRequireSupplementary;
        }


        public long getLogBeginTime() {
            return logBeginTime;
        }

        public void setLogBeginTime(long logBeginTime) {
            this.logBeginTime = logBeginTime;
        }

        public long getLogEndTime() {
            return logEndTime;
        }

        public void setLogEndTime(long logEndTime) {
            this.logEndTime = logEndTime;
        }

        public String getLogNameList() {
            return logNameList;
        }

        public void setLogNameList(String logNameList) {
            this.logNameList = logNameList;
        }

        @Override
        public String toString() {
            return "FeedbackData{" +
                    "fbId =" + fbId +
                    ", isRequireSupplementary: " + isRequireSupplementary +
                    '}';
        }
    }


    public FeedbackData[] getFeedback() {
        return feedback;
    }

    public void setFeedback(FeedbackData[] feedback) {
        this.feedback = feedback;
    }

    public int getIsRequireLog() {
        return isRequireLog;
    }

    public void setIsRequireLog(int isRequireLog) {
        this.isRequireLog = isRequireLog;
    }

    public boolean isRequireLog() {
        return isRequireLog == 1;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }


    @Override
    public String toString() {
        return "LogUploadRangeRsp{" +
                "feedbackDatas =" + Arrays.toString(feedback) +
                ", isRequireLog: " + isRequireLog +
                '}';
    }
}
