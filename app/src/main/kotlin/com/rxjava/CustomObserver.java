package com.rxjava;

import android.text.TextUtils;

import ss.com.toolkit.App;
import ss.com.toolkit.R;
import ss.com.toolkit.retrofit.exception.ServerException;
import ss.com.toolkit.retrofit.exception.UdbLoginException;
import ss.com.toolkit.util.ToastUtil;

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
        if (e == null) return;
        ToastUtil.showRedTopToast("something error!!!");

        e.printStackTrace();
    }
}
