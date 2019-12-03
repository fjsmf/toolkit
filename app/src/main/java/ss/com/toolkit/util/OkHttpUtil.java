package ss.com.toolkit.util;

import okhttp3.OkHttpClient;

/**
 * 提供统一的OkHttpClient
 * <p>
 * Created by trs on 18-2-7.
 */

public final class OkHttpUtil {
    private static final Singleton<OkHttpClient, Void> sClient = new Singleton<OkHttpClient, Void>() {
        @Override
        protected OkHttpClient newInstance(Void arg) {
            final OkHttpClient.Builder builder = new OkHttpClient.Builder();
            return builder.build();
        }
    };

    public static OkHttpClient getOkHttpClient() {
        return sClient.getInstance(null);
    }
}
