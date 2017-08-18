package com.ss.ssframework.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by 健 on 2017/8/15.
 */

public abstract class SSBaseActivity extends AppCompatActivity{

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
}
