package com.rxjava;

public abstract class CustomObserver<T> extends BaseObserver<T> {

    @Override
    public final void onNext(T t) {
        success(t);
    }

    @Override
    public final void onError(Throwable e) {
        onFailed(e);
    }

    public abstract void success(T t);

    public void onFailed(Throwable e) {
    }
}
