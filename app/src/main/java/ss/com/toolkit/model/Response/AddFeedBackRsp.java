package ss.com.toolkit.model.Response;

import android.support.annotation.Keep;

@Keep
public class AddFeedBackRsp {

    private String result;
    private String isRequireLog;
    private String fbId;
    private long logBeginTime;
    private long logEndTime;
    private String description;
    private String serverTime;
    private long maxFileSize;
    private String status;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public long getLogBeginTime() {
        return logBeginTime;
    }

    public void setLogBeginTime(long logBeginTime) {
        this.logBeginTime = logBeginTime;
    }

    public String getIsRequireLog() {
        return isRequireLog;
    }

    public void setIsRequireLog(String isRequireLog) {
        this.isRequireLog = isRequireLog;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public long getLogEndTime() {
        return logEndTime;
    }

    public void setLogEndTime(long logEndTime) {
        this.logEndTime = logEndTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "NewAddQuestionRsp{" +
                "status=" + status +
                ", result='" + result + '\'' +
                ", fbId=" + fbId +
                ", isRequireLog=" + isRequireLog +
                ", maxFileSize=" + maxFileSize +
                ", description=" + description +
                ", logBeginTime=" + logBeginTime +
                ", logEndTime=" + logEndTime +
                '}';
    }

}
