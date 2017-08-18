package com.ss.ssframework.http.callback;

import android.util.Log;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by 健 on 2017/7/24.
 */

public abstract class HttpResObserver<T> implements Observer<T> {
    private final static String TAG = HttpResObserver.class.getSimpleName();

    @Override
    public final void onSubscribe(@NonNull Disposable d) {
        Log.e(TAG, "onSubscribe : " + Thread.currentThread().getName() + " thread");
        onStart(d);
    }

    @Override
    public final void onNext(@NonNull T data) {
        Log.e(TAG, "onNext : " + Thread.currentThread().getName() + " thread");
        onSuccess(data);
    }

    @Override
    public final void onError(@NonNull Throwable e) {
        Log.e(TAG, "onError : " + Thread.currentThread().getName() + " thread");
        Log.e(TAG, "onError : " + e.getMessage());
        if (e instanceof TimeoutException || e instanceof SocketTimeoutException
                || e instanceof ConnectException){
            onFail("网络异常，请检查网络连接！");
        }else {
            onFail(e.getMessage());
        }
    }

    @Override
    public final void onComplete() {
        Log.i(TAG, "onComplete");
    }

    public abstract void onStart(Disposable d);
    public abstract void onSuccess(T data);
    public abstract void onFail(String msg);
}
