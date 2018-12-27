package ss.com.toolkit.net;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ss.com.toolkit.R;

public class NetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);

    }

    public void queryIp(View view) {
        IpQuery.queryFromIfconfigMe()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ipInfo ->
                                Log.i("nadiee", String.format("ip:%s, region:%s, provider:%s", ipInfo.ip, ipInfo.region, ipInfo.provider == null ? "" : ipInfo.provider)),
                        err -> {
                            Log.i("nadiee", "request ip info error :" + err.getMessage());
                            err.printStackTrace();
                        });
    }


}
