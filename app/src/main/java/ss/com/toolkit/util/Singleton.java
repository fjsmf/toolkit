package ss.com.toolkit.util;

/**
 * Created by trs on 17-8-28.
 */

public abstract class Singleton<T, V> {
    private volatile T mInstance;

    protected abstract T newInstance(V arg);

    public final T getInstance(V arg) {
        if (mInstance == null) {
            synchronized (this) {
                if (mInstance == null)
                    mInstance = newInstance(arg);
            }
        }
        return mInstance;
    }
}
