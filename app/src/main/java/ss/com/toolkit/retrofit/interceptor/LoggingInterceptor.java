package ss.com.toolkit.retrofit.interceptor;

import android.util.Log;

import java.io.IOException;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * http 日志拦截器
 *
 * @autor liangruijun
 * @date 2017/9/8 17:55
 */

public class LoggingInterceptor implements Interceptor {
    private static final String TAG = "LoggingInterceptor";
    private boolean open = true;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        if (open) {

            if (request.url().host().contains("wup")) {
                Log.d(TAG, String.format(Locale.ENGLISH,"Sending url= %s , header = %s ",
                        request.url(), request.headers().toString().replaceAll("\n", " ")));
            } else {
                Log.d(TAG, String.format(Locale.ENGLISH,"Sending url= %s , header = %s , body = %s",
                        request.url(), request.headers().toString().replaceAll("\n", " "), request.body()));
            }

            // 打印post请求参数
            String method = request.method();
            if ("POST".equals(method)) {
                StringBuilder sb = new StringBuilder();
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ", ");
                    }
                    sb.delete(sb.length() - 1, sb.length());
                    Log.d(TAG, "RequestParams:{" + sb.toString() + "}");
                }
            }
        }

        Response response = chain.proceed(request);

        if (open) {
            long t2 = System.nanoTime();
            Log.d(TAG, String.format(Locale.ENGLISH,"Received response for %s in %.1fms,response code:%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.code()));
        }

        return response;
    }
}
