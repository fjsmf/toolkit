package ss.com.toolkit.model.Response;

import android.support.annotation.Keep;

import java.util.List;

/**
 * Created by yuhui2 on 2017/8/9.
 */
@Keep
public class LogUploadRangeRsp   {
    private List<String> range;
    private int status;

    public List<String> getRange() {
        return range;
    }

    public void setRange(List<String> range) {
        this.range = range;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LogUploadRangeRsp{" +
                "status=" + status +
                ", range length: " + range +
                '}';
    }
}
