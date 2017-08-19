package com.ss.ssframework.view.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.ss.ssframework.R;

import io.reactivex.disposables.Disposable;

/**
 * Created by 健 on 2017/7/26.
 */

public class LoadingDialog extends ProgressDialog implements DialogInterface.OnDismissListener {

    private boolean cancelable;
    private Disposable disposable;

    public LoadingDialog(Context context) {
        super(context);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(getContext());
    }

    private void init(Context context) {
        setContentView(R.layout.loading_dialog);//loading的xml文件
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
        getWindow().setBackgroundDrawable(new ColorDrawable());
        this.getWindow().setDimAmount(0);   //背景高亮，否则是灰色的
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (cancelable) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }

    /**
     * 显示loadingDialog
     *
     * @param cancelable Whether the dialog should be canceled by user.
     * @see {@link #setCancelable(boolean)}
     */
    public void show(boolean cancelable) {
        show(cancelable, null);
    }

    /**
     * 显示loadingDialog，用户可以手动取消
     * dialog消失时自动解除订阅
     *
     * @param disposable 用于解除订阅
     */
    public void show(@Nullable Disposable disposable) {
        show(true, disposable, this);
    }

    /**
     * 显示loadingDialog
     *
     * @param cancelable        dialog是否可以被用户主动取消.
     * @param onDismissListener loadingDialog 消失监听
     */
    public void show(boolean cancelable, @Nullable OnDismissListener onDismissListener) {
        show(cancelable, null, onDismissListener);
    }

    /**
     * 显示loadingDialog
     *
     * @param cancelable        dialog是否可以被用户主动取消.
     * @param disposable        用于解除订阅
     * @param onDismissListener loadingDialog 消失监听
     */
    private void show(boolean cancelable, @Nullable Disposable disposable, @Nullable OnDismissListener onDismissListener) {
        this.cancelable = cancelable;
        this.disposable = disposable;
        setCancelable(cancelable);
        setCanceledOnTouchOutside(cancelable);
        setOnDismissListener(onDismissListener);
        super.show();
    }
}
