package ss.com.toolkit.model.Response;

import android.support.annotation.Keep;

/**
 * Created by yuhui2 on 2017/8/9.
 */
@Keep
public class LogUploadRsp {
    private int result;
    private String url;
    private String description;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "LogUploadRangeRsp{" +
                "result=" + result +
                ", url: " + url +
                ", description: " + description +
                '}';
    }
}
