package ss.com.toolkit.retrofit.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author charles
 * @date 2018/5/30
 */

public class SaveCookiesInterceptor implements Interceptor {
    private static final String TAG = "SaveCookiesInterceptor";

    private static final String COOKIE_PREF = "cookies_prefs";
    private Context mContext;

    public SaveCookiesInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        String url = request.url().toString();
        if (!response.headers("Set-Cookie").isEmpty() && url.endsWith("/user/login")) { // 登录接口返回的cookie
            List<String> cookies = response.headers("Set-cookie");
            String cookie = encodeCookie(cookies);
            saveCookie(AddCookiesInterceptor.COOKIE_KEY, cookie);
        }
        return response;
    }

    /**
     * 如果set-cookie有多个，则去重，整合成一个
     *
     * @param cookies
     * @return
     */
    private String encodeCookie(List<String> cookies) {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> set = new HashSet<>();
        for (String cookie : cookies) {
            String[] arr = cookie.split(";");
            for (String s : arr) {
                if (set.contains(s)) continue;
                set.add(s);
            }
        }

        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String cookie = iterator.next();
            stringBuilder.append(cookie).append(";");
        }

        int last = stringBuilder.lastIndexOf(";");
        if (stringBuilder.length() - 1 == last) {
            stringBuilder.deleteCharAt(last);
        }

        return stringBuilder.toString();
    }

    private void saveCookie(String key, String cookies) {
        LogUtils.tag(TAG).i("nadiee", "saveCookie:" + cookies);
        SharedPreferences sp = mContext.getSharedPreferences(COOKIE_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (TextUtils.isEmpty(key)) {
            throw new NullPointerException("url is null.");
        } else {
            editor.putString(key, cookies);
        }
        editor.apply();
    }
}
