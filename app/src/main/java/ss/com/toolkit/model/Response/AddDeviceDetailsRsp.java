package ss.com.toolkit.model.Response;

import android.support.annotation.Keep;

@Keep
public class AddDeviceDetailsRsp {
    private int result;
    private String description;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAddSucceed() {
        return result == 1;
    }

    @Override
    public String toString() {
        return "AddDeviceDetailsRsp { " +
                "result =" + result +
                ", description: " + description +
                '}';
    }
}
