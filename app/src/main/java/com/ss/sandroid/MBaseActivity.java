package com.ss.sandroid;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.ss.ssframework.base.SSBaseActivity;
import com.ss.ssframework.view.ImmersionStatusBar;

/**
 * Created by 健 on 2017/8/15.
 */

public abstract class MBaseActivity extends SSBaseActivity {

    private View baseContentView;
    private ViewGroup baseContentContainer;
    private View baseTitleView;
    private ViewStub baseVsTitle;

    private ImageView imgTitleLeft;
    private TextView tvTitleLeft;
    private ImageView imgTitleRight;
    private TextView tvTitleRight;
    private TextView tvTitleCenter;

    @Override
    public View getContentView() {
        baseContentView = getLayoutInflater().inflate(R.layout.base_activity, null);
        baseVsTitle = (ViewStub) baseContentView.findViewById(R.id.base_vs_title);
        baseContentContainer = (ViewGroup) baseContentView.findViewById(R.id.base_content_container);
        baseContentContainer.addView(getLayoutInflater().inflate(getContentLayoutRes(), null));
        return baseContentView;
    }

    @Override
    public boolean isDisplayTitleBar() {
        return true;
    }

    @Override
    public void initImmersionStatusBar() {
//        ImmersionStatusBar.initDef(this);
        ImmersionStatusBar.initDefFitSys(this);
//       ImmersionUtil.initColorfulDef(this, getResources().getColor(R.color.colorPrimaryDark),
//                ImmersionUtil.STATUS_BAR_ALPHA_TRANSLUCENT);
//        ImmersionUtil.initColorfulDefFitSys(this, getResources().getColor(R.color.colorPrimaryDark),
//                ImmersionUtil.STATUS_BAR_ALPHA_TRANSLUCENT);
//       ImmersionUtil.initDefLightFitSys(this, getResources().getColor(R.color.colorPrimaryDark),
//                ImmersionUtil.STATUS_BAR_ALPHA_TRANSLUCENT);
    }

    /**
     * 初始化标题栏，可自定义标题栏view
     *
     * @param resId the resource identifier of the title view.
     * @return return the custom custom view or default title view if the resId is not valid
     */
    protected final View initTitleView(@LayoutRes int resId) {
        if (resId > 0) {
            baseVsTitle.setLayoutResource(resId);
        }
        baseTitleView = baseVsTitle.inflate();
        baseVsTitle.setVisibility(View.VISIBLE);
        return baseTitleView;
    }

    /**
     * 初始化默认标题栏
     *
     * @return return the default title view
     */
    protected final View initTitleView() {
        baseTitleView = baseVsTitle.inflate();
        baseVsTitle.setVisibility(View.VISIBLE);
        setSupportActionBar((Toolbar) baseTitleView.findViewById(R.id.toolbar));
        return baseTitleView;
    }

    /**
     * 设置标题栏左侧img
     *
     * @param resId           the resource identifier of the drawable.
     * @param onClickListener The callback that will run
     */
    protected void setTitleImgLeft(@DrawableRes int resId, View.OnClickListener onClickListener) {
        setTitleImgLeft(getResources().getDrawable(resId), onClickListener);
    }

    /**
     * 设置标题栏左侧img
     *
     * @param drawable        the Drawable to set, or {@code null} to clear the content
     * @param onClickListener The callback that will run
     */
    protected void setTitleImgLeft(Drawable drawable, View.OnClickListener onClickListener) {
        if (!isTitleEnable()) {
            throw new IllegalStateException("Base common Title is not unavailable, set fail !");
        }
        if (imgTitleLeft == null) {
            imgTitleLeft = (ImageView) findViewById(R.id.base_title_bar).findViewById(R.id.iv_title_left);
        }
        imgTitleLeft.setImageDrawable(drawable);
        if (onClickListener != null) {
            imgTitleLeft.setOnClickListener(onClickListener);
        } else {
            imgTitleLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    /**
     * 设置标题栏左侧提示文字
     *
     * @param resId           the resource identifier of the title left text.
     * @param onClickListener The callback that will run
     */
    protected void setTitleTextLeft(@StringRes int resId, @Nullable View.OnClickListener onClickListener) {
        setTitleTextLeft(getResources().getString(resId), onClickListener);
    }

    /**
     * 设置标题栏左侧提示文字
     *
     * @param text            The string value to the textView in title left;
     * @param onClickListener The callback that will run
     */
    protected void setTitleTextLeft(CharSequence text, @Nullable View.OnClickListener onClickListener) {
        if (!isTitleEnable()) {
            throw new IllegalStateException("Base common Title is not unavailable, set fail !");
        }
        if (tvTitleLeft == null) {
            tvTitleLeft = (TextView) findViewById(R.id.base_title_bar).findViewById(R.id.tv_title_left);
            tvTitleLeft.setOnClickListener(onClickListener);
        }
        tvTitleLeft.setText(text);
    }

    /**
     * 设置标题栏右侧img
     *
     * @param resId           the resource identifier of the drawable.
     * @param onClickListener The callback that will run
     */
    protected void setTitleImgRight(@DrawableRes int resId, View.OnClickListener onClickListener) {
        setTitleImgRight(getResources().getDrawable(resId), onClickListener);
    }

    /**
     * 设置标题栏右侧img
     *
     * @param drawable        the Drawable to set, or {@code null} to clear the content
     * @param onClickListener The callback that will run
     */
    protected void setTitleImgRight(Drawable drawable, View.OnClickListener onClickListener) {
        if (!isTitleEnable()) {
            throw new IllegalStateException("Base common Title is not unavailable, set fail !");
        }
        if (imgTitleRight == null) {
            imgTitleRight = (ImageView) findViewById(R.id.base_title_bar).findViewById(R.id.iv_title_right);
        }
        imgTitleRight.setImageDrawable(drawable);
        if (onClickListener != null) {
            imgTitleLeft.setOnClickListener(onClickListener);
        }
    }

    /**
     * 设置标题栏右侧提示文字
     *
     * @param resId           the resource identifier of the title right text.
     * @param onClickListener The callback that will run
     */
    protected void setTitleTextRight(@StringRes int resId, @Nullable View.OnClickListener onClickListener) {
        setTitleTextRight(getResources().getString(resId), onClickListener);
    }

    /**
     * 设置标题栏右侧提示文字
     *
     * @param text            The string value to the textView in title right;
     * @param onClickListener The callback that will run
     */
    protected void setTitleTextRight(CharSequence text, @Nullable View.OnClickListener onClickListener) {
        if (!isTitleEnable()) {
            throw new IllegalStateException("Base common Title is not unavailable, set fail !");
        }
        if (tvTitleRight == null) {
            tvTitleRight = (TextView) findViewById(R.id.base_title_bar).findViewById(R.id.tv_title_right);
            tvTitleRight.setOnClickListener(onClickListener);
        }
        tvTitleRight.setText(text);
    }

    /**
     * 设置中间title
     *
     * @param resId the resource identifier of the title text.
     */
    protected void setTitleCenter(@StringRes int resId) {
        setTitleCenter(getResources().getString(resId));
    }

    /**
     * 设置中间title
     *
     * @param text the title text.
     */
    private void setTitleCenter(CharSequence text) {
        if (!isTitleEnable()) {
            throw new IllegalStateException("Base common Title is not unavailable, set fail !");
        }
        if (tvTitleCenter == null) {
            tvTitleCenter = (TextView) findViewById(R.id.base_title_bar).findViewById(R.id.tv_title_center);
        }
        tvTitleCenter.setText(text);
    }

    @Override
    public final void setTitle(CharSequence title) {
        setTitleCenter(title);
    }

    @Override
    public void setTitle(int titleId) {
        setTitleCenter(titleId);
    }

    /**
     * 设置标题栏背景
     */
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void setTitleBarBackground(Drawable drawable) {
        baseTitleView.setBackground(drawable);
    }

    /**
     * 设置标题栏背景
     */
    protected void setTitleBarBackgroundDrawable(Drawable drawable) {
        baseTitleView.setBackgroundDrawable(drawable);
    }

    /**
     * 设置标题栏背景
     */
    protected void setTitleBarBackgroundColor(@ColorInt int color) {
        baseTitleView.setBackgroundColor(color);
    }

    /**
     * titleView是否已经初始化
     */
    private boolean isTitleEnable() {
        return isDisplayTitleBar() && baseTitleView != null
                && baseVsTitle.getLayoutResource() == R.layout.base_title_bar;
    }
}
