package ss.com.toolkit.retrofit;

import android.os.Build;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import ss.com.toolkit.model.Api;
import ss.com.toolkit.retrofit.factory.GsonConverterFactory;
import ss.com.toolkit.retrofit.factory.StringConverterFactory;
import ss.com.toolkit.util.SSLSocketUtils;

public class RetrofitManager {
    private static final String TAG = "RetrofitManager";

    private static OkHttpClient client;
    private static final Object rLock = new Object();
    private static volatile RetrofitManager instance;

    public static RetrofitManager getInstance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }
/*
* RetrofitManager.getInstance().get(GameApi.class)
                        .tasteGame(new TastGameReq(UserManager.getUserIdModel(), gameId))
                        .subscribeOn(Schedulers.io())
                        .subscribe(RxUtils.emptyObserver());
* */
    public synchronized <T> T get(Class<T> tClass, String baseUrl) {
        ensureClient();
        Retrofit retrofit = new Retrofit.Builder().client(client)
                .baseUrl(baseUrl)
//                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();//.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        T api = retrofit.create(tClass);
        return api;
    }

    private void ensureClient() {
        if (client == null) {
            OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();

//            if (BuildConfig.DEBUG) {
//                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//                clientBuilder.addInterceptor(interceptor);
//            }

            // 设置cookie
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                X509TrustManager trustManager = SSLSocketUtils.systemDefaultTrustManager();
                SSLSocketFactory sslSocketFactory = SSLSocketUtils.sslSocketFactoryBelowLollipop(trustManager);
                if (sslSocketFactory != null) {
                    clientBuilder.sslSocketFactory(sslSocketFactory, trustManager);
                }
            }
            clientBuilder.connectTimeout(15, TimeUnit.SECONDS);
            clientBuilder.readTimeout(30, TimeUnit.SECONDS);
            clientBuilder.writeTimeout(15, TimeUnit.SECONDS);
            client = clientBuilder.build();
        }
    }
}
