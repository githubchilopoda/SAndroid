package com.ss.ssframework.view.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
        setOnDismissListener(this);
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        if(cancelable){
            if (!disposable.isDisposed()){
                disposable.dispose();
            }
        }
    }

    public void show(boolean cancelable, Disposable disposable){
        this.cancelable = cancelable;
        this.disposable = disposable;
        setCancelable(cancelable);
        setCanceledOnTouchOutside(cancelable);
        super.show();
    }

    @Override
    public void show() {
        this.cancelable = false;
        setCancelable(cancelable);
        setCanceledOnTouchOutside(cancelable);
        super.show();
    }

    public void setCacelable(boolean cacelable) {
        this.cancelable = cacelable;
    }

    public void setDisposable(Disposable disposable) {
        this.disposable = disposable;
    }
}
