package com.ss.ssframework.base;

import android.app.Activity;
import android.content.DialogInterface;

import io.reactivex.disposables.Disposable;

/**
 * Created by 健 on 2017/7/25.
 */

public interface IBaseView {

    /**
     * 订阅当前view
     *
     * @param d 用于{@link Activity#onDestroy()}解除订阅
     * */
    void onAttachView(Disposable d);

    /**
     * 显示loadingDialog
     *
     * @param cancelable Whether the dialog should be canceled by user.
     * @see {@link android.app.Dialog#setCancelable(boolean)}
     */
    void showLoadingDialog(boolean cancelable);

    /**
     * 显示loadingDialog
     *
     * @param cancelable Whether the dialog should be canceled by user.
     * @see {@link android.app.Dialog#setCancelable(boolean)}
     */
    void showLoadingDialog(boolean cancelable, Disposable d);

    /**
     * 显示loadingDialog
     *
     * @param cancelable Whether the dialog should be canceled by user.
     * @param onDismissListener loadingDialog 消失监听
     */
    void showLoadingDialog(boolean cancelable, DialogInterface.OnDismissListener onDismissListener);

    void dismissLoadingDialog();
}
