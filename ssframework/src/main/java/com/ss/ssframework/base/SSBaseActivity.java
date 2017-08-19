package com.ss.ssframework.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ss.ssframework.view.dialog.LoadingDialog;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by 健 on 2017/8/15.
 */

public abstract class SSBaseActivity extends AppCompatActivity implements IBaseView{

    /**
     * A disposable container that can hold onto multiple other disposables and
     * offers O(1) add and removal complexity.
     *
     * @see {@link IBaseView#onAttachView(Disposable)}
     * */
    public CompositeDisposable mDisposables = new CompositeDisposable();

    /**
     * 加载框
     * */
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(getContentView());

        if (isDisplayTitleBar()) {
            initTitleBar();
        }

        initImmersionStatusBar();

        onInit(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposables.dispose();
    }

    /**
     * 获取用户界面
     */
    public abstract @LayoutRes int getContentLayoutRes();

    /**
     * 对{@link #getContentView()}进行包装，添加标题栏
     */
    public abstract View getContentView();

    /**
     * 是否显示标题栏
     */
    public abstract boolean isDisplayTitleBar();

    /**
     * 初始化标题栏
     */
    public abstract void initTitleBar();

    /**
     * 初始化沉浸状态栏
     */
    public abstract void initImmersionStatusBar();

    /**
     * 初始化
     *
     * @param savedInstanceState
     * @see #onCreate(Bundle)
     */
    public abstract void onInit(Bundle savedInstanceState);


    @Override
    public void showLoadingDialog(boolean cancelable) {
        loadingDialogShowCheck();
        mLoadingDialog.show(cancelable);
    }

    @Override
    public void showLoadingDialog(Disposable d) {
        loadingDialogShowCheck();
        mLoadingDialog.show(d);
    }

    @Override
    public void showLoadingDialog(boolean cancelable, DialogInterface.OnDismissListener onDismissListener) {
        loadingDialogShowCheck();
        mLoadingDialog.show(cancelable, onDismissListener);
    }

    @Override
    public void dismissLoadingDialog() {
        mLoadingDialog.dismiss();
    }

    private void loadingDialogShowCheck(){
        if (mLoadingDialog == null){
            mLoadingDialog = new LoadingDialog(this);
        }
        if (mLoadingDialog.isShowing()){
            throw new IllegalStateException("Loading dialog is showing,"
                    + " Only one can be displayed at the same time !");
        }
    }
}
