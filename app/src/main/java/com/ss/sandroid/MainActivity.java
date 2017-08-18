package com.ss.sandroid;

import android.os.Bundle;

import io.reactivex.disposables.Disposable;

public class MainActivity extends MBaseActivity {

    @Override
    public int getContentLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    public void initTitleBar() {
        initTitleView();
        setTitleTextLeft("反回");
        setTitle(R.string.app_name);
    }

    @Override
    public void onInit(Bundle savedInstanceState) {

    }

    @Override
    public void onAttachView(Disposable d) {

    }
}
