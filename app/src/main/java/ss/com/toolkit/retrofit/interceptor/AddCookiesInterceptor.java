package ss.com.toolkit.retrofit.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * http cookies拦截器
 *
 * @author rj-liang
 * @date 2018/1/29 16:13
 */

public class AddCookiesInterceptor implements Interceptor {
    private static final String TAG = "AddCookiesInterceptor";

    public static final String COOKIE_KEY = "login_cookie";
    private static final String COOKIE_PREF = "cookies_prefs";
    private static final String REGION_COOKIE_PREF = "region_cookie_pref";
    private Context mContext;

    public AddCookiesInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
        String cookies = getAllCookie(COOKIE_KEY);
        LogUtils.tag(TAG).d("add cookies: %s", cookies);
        if (!TextUtils.isEmpty(cookies)) {
            builder.addHeader("Cookie", cookies);
        }

        return chain.proceed(builder.build());
    }

    private String getCookie(String key) {
        SharedPreferences sp = mContext.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(key) && sp.contains(key) && !TextUtils.isEmpty(sp.getString(key, ""))) {
            return sp.getString(key, "");
        }
        return null;
    }

    private String getAllCookie(String key) {
        StringBuilder sb = new StringBuilder();

//        String regionCookie = "region=" + BasePrefUtils.getCookieRegion(mContext);
//        String languageCookie = "language=" + BasePrefUtils.getCookieLanguage(mContext);
        if (!TextUtils.isEmpty(getCookie(key))) {
            sb.append(";");
            sb.append(getCookie(key));
        }
        return sb.toString();
    }
}
