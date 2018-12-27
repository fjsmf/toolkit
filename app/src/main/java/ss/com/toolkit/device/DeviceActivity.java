package ss.com.toolkit.device;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ss.com.toolkit.R;
import ss.com.toolkit.net.IpQuery;
import ss.com.toolkit.net.NetWorkUtils;

public class DeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        TextView country = findViewById(R.id.country);
        country.setText(String.format("country :%s", getCountry()));
    }
    public String getCountry(){
        try {
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String countryCode = telephonyManager.getSimCountryIso();
            Log.i("nadiee", "country:"+countryCode);
            if (TextUtils.isEmpty(countryCode)) {
                countryCode = getResources().getConfiguration().locale.getCountry();
            }
            return countryCode == null ? "" : countryCode.toLowerCase();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
