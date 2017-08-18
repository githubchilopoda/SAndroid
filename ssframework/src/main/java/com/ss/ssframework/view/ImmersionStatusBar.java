package com.ss.ssframework.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ss.ssframework.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Created by 健 on 2017/8/16.
 * 沉浸状态栏工具类
 */

public class ImmersionStatusBar {

    public static final int DEFAULT_STATUS_BAR_TRANSLUCENT = 112; //半透明
    public static final int DEFAULT_STATUS_BAR_ALPHA = 0;   //全透明
    public static final int DEFAULT_STATUS_BAR_COLOR = Color.TRANSPARENT;

    private static final int IMMERSION_STATUS_BAR_VIEW_ID = R.id.immersion_status_bar_view;
    private static final int IMMERSION_STATUS_BAR_SHADE_VIEW_ID = R.id.immersion_status_bar_shade_view;

    /**
     * 默认沉浸状态栏，状态栏不支持改变颜色
     * view会与状态栏重叠
     *
     * @param activity {@link Activity}
     */
    public static void initDef(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //设置系统状态栏处于可见状态
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }

    /**
     * 默认沉浸状态栏，状态栏不支持改变颜色
     * view在状态栏下
     *
     * @param activity {@link Activity}
     */
    public static void initDefFitSys(Activity activity) {
        initDef(activity);
        setHeaderFitSysWin(activity);
    }

    /**
     * 支持变色的沉浸状态栏，状态栏字体颜色为系统默认（一般为白色）
     * view会与状态栏重叠
     *
     * @param activity       {@link Activity}
     * @param color          状态栏颜色
     * @param statusBarAlpha 状态栏透明度
     */
    public static void initColorfulDef(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        setColorFulDef(activity, color, statusBarAlpha);
    }

    /**
     * 支持变色的沉浸状态栏，状态栏字体颜色为系统默认（一般为白色）
     * view在状态栏下面
     *
     * @param activity       {@link Activity}
     * @param color          状态栏颜色
     * @param statusBarAlpha 状态栏透明度
     */
    public static void initColorfulDefFitSys(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        initColorfulDef(activity, color, statusBarAlpha);
        setHeaderFitSysWin(activity);
    }

    /**
     * 支持变色的沉浸状态栏，状态栏字体颜色为深色（只支持MIUI6.0、Flym4.0一级Android6.0以上系统）
     * view与状态栏重叠
     *
     * @param activity       {@link Activity}
     * @param color          状态栏颜色
     * @param statusBarAlpha 状态栏透明度
     */
    public static void initDefLight(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        if (isFlyme4Later()) {
            darkModeForFlyme4(activity, true);
            setColorFulDef(activity, color, statusBarAlpha);
        } else if (isMIUI6Later()) {
            darkModeForMIUI6(activity, true);
            setColorFulDef(activity, color, statusBarAlpha);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            darkModeForM(activity, true);
            setColorFulDef(activity, color, statusBarAlpha);
        } else if (Build.VERSION.SDK_INT >= 19) {
            setColorFulDef(activity, color, statusBarAlpha);
        } else {
            setColorFulDef(activity, color, statusBarAlpha);
        }
    }

    /**
     * 支持变色的沉浸状态栏，状态栏字体颜色为深色（只支持MIUI6.0、Flym4.0一级Android6.0以上系统）
     * view在状态栏下面
     *
     * @param activity       {@link Activity}
     * @param color          状态栏颜色
     * @param statusBarAlpha 状态栏透明度
     */
    public static void initDefLightFitSys(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        initDefLight(activity, color, statusBarAlpha);
        setHeaderFitSysWin(activity);
    }

    /**
     * 为DrawerLayout 布局设置状态栏变色
     * view与状态栏重叠
     *
     * @param activity       需要设置的activity
     * @param drawerLayout   DrawerLayout
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    public static void initColorfulForDrawer(Activity activity, DrawerLayout drawerLayout, @ColorInt int color,
                                             @IntRange(from = 0, to = 255) int statusBarAlpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置系统状态栏处于可见状态
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
        // 生成一个状态栏大小的矩形
        // 添加 statusBarView 到布局中
        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        View fakeStatusBarView = contentLayout.findViewById(IMMERSION_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
            fakeStatusBarView.setBackgroundColor(color);
        } else {
            fakeStatusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
            fakeStatusBarView.setLayoutParams(lp);
            fakeStatusBarView.setId(IMMERSION_STATUS_BAR_VIEW_ID);
            fakeStatusBarView.setBackgroundColor(color);
            contentLayout.addView(fakeStatusBarView, 0);
        }

        setShadeView(activity, statusBarAlpha);
    }

    /**
     * 为DrawerLayout 布局设置状态栏变色
     * view在状态栏下
     *
     * @param activity       需要设置的activity
     * @param drawerLayout   DrawerLayout
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    public static void initColorfulForDrawerFitSys(Activity activity, DrawerLayout drawerLayout, @ColorInt int color,
                                             @IntRange(from = 0, to = 255) int statusBarAlpha) {
        initColorfulForDrawer(activity, drawerLayout, color, statusBarAlpha);
        // 内容布局不是 LinearLayout 时,设置padding top
        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
        if (contentLayout instanceof LinearLayout) {
            if (((LinearLayout) contentLayout).getOrientation() != LinearLayout.VERTICAL && contentLayout.getChildAt(1) != null) {
                contentLayout.getChildAt(1)
                        .setPadding(contentLayout.getPaddingLeft(), getStatusBarHeight(activity) + contentLayout.getPaddingTop(),
                                contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
            }
        } else if (contentLayout.getChildAt(1) != null) {
            contentLayout.getChildAt(1)
                    .setPadding(contentLayout.getPaddingLeft(), getStatusBarHeight(activity) + contentLayout.getPaddingTop(),
                            contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
        }
    }

    /**
     * 添加半透明矩形条,遮罩彩色statusbar
     *
     * @param activity       需要设置的 activity
     * @param statusBarAlpha 透明值
     */
    private static void setShadeView(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        View shadeTranslucentView = contentView.findViewById(IMMERSION_STATUS_BAR_SHADE_VIEW_ID);
        if (shadeTranslucentView != null) {
            if (shadeTranslucentView.getVisibility() == View.GONE) {
                shadeTranslucentView.setVisibility(View.VISIBLE);
            }
            shadeTranslucentView.setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0));
        } else {
            shadeTranslucentView = new View(activity);
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
            shadeTranslucentView.setLayoutParams(params);
            shadeTranslucentView.setBackgroundColor(Color.argb(statusBarAlpha, 0, 0, 0));
            shadeTranslucentView.setId(IMMERSION_STATUS_BAR_SHADE_VIEW_ID);
            contentView.addView(shadeTranslucentView);
        }
    }

    /**
     * 设置状态栏颜色,仅在初始化之后才可用
     * 如果使用如下两个方法初始化，则此方法无效{@link #initDef(Activity)},{@link #initDefFitSys(Activity)
     */
    public static void setImmersionStatusColor(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(blendStatusColor(color, statusBarAlpha));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View fakeStatusBarView = decorView.findViewById(IMMERSION_STATUS_BAR_VIEW_ID);
            if (fakeStatusBarView != null) {
                fakeStatusBarView.setBackgroundColor(blendStatusColor(color, statusBarAlpha));
            } else {
                Log.e(activity.getClass().getSimpleName(),
                        "setImmersionStatusColor fail, Maybe you haven't initialized yet!");
            }
        }
    }

    /**
     * 设置沉浸状态栏，状态栏颜色可自由设置
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 状态栏透明度
     */
    private static void setColorFulDef(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(blendStatusColor(color, statusBarAlpha));

            int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            activity.getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setImmersionView(activity, color, statusBarAlpha);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT > 16) {
            int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            activity.getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
        }
    }

    /**
     * 在状态栏添加一个与状态栏大小相同的彩色矩形条
     *
     * @param activity       需要设置的activity
     * @param color          状态栏颜色值
     * @param statusBarAlpha 透明值
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void setImmersionView(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewById(IMMERSION_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView != null) {
            if (fakeStatusBarView.getVisibility() == View.GONE) {
                fakeStatusBarView.setVisibility(View.VISIBLE);
            }
        } else {
            fakeStatusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
            fakeStatusBarView.setLayoutParams(lp);
            fakeStatusBarView.setId(IMMERSION_STATUS_BAR_VIEW_ID);
            decorView.addView(fakeStatusBarView);
        }
        if (fakeStatusBarView != null) {
            fakeStatusBarView.setBackgroundColor(blendStatusColor(color, statusBarAlpha));
        }
    }

    /**
     * android 6.0设置字体颜色
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private static void darkModeForM(Activity activity, boolean darkMode) {
        //如果是6.0以上将状态栏文字改为黑色，并设置状态栏颜色
        /*activity.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        activity.getWindow().setStatusBarColor(color);*/

        int systemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
        if (darkMode) {
            systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            systemUiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
    }

    /**
     * 设置Flyme4+的darkMode,darkMode时候字体颜色及icon变黑
     * http://open-wiki.flyme.cn/index.php?title=Flyme%E7%B3%BB%E7%BB%9FAPI
     */
    private static boolean darkModeForFlyme4(Activity activity, boolean darkMode) {
        boolean result = false;
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
            darkFlag.setAccessible(true);
            meizuFlags.setAccessible(true);
            int bit = darkFlag.getInt(null);
            int value = meizuFlags.getInt(lp);
            if (darkMode) {
                value |= bit;
            } else {
                value &= ~bit;
            }

            meizuFlags.setInt(lp, value);
            activity.getWindow().setAttributes(lp);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("StatusBar", "darkIcon: failed");
        }
        return result;
    }

    /**
     * MIUI 6的沉浸支持透明白色字体和透明黑色字体
     * 设置MIUI6+的状态栏是否为darkMode,darkMode时候字体颜色及icon变黑
     * http://dev.xiaomi.com/doc/p=4769/
     */
    private static boolean darkModeForMIUI6(Activity activity, boolean darkMode) {
        boolean result = false;
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), darkMode ? darkModeFlag : 0, darkModeFlag);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 设置activity根布局参数
     * 使跟布局在statusbar下面
     */
    public static void setRootFitSysWin(Activity activity) {
        ViewGroup root = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        for (int i = 0, count = root.getChildCount(); i < count; i++) {
            View childView = root.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

    /**
     * 设置view根布局参数
     * 使view在statusbar下面
     */
    public static void setHeaderFitSysWin(Activity activity) {
        ViewGroup root = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        if (root.getChildAt(0) instanceof ViewGroup) {
            ViewGroup header = (ViewGroup) root.getChildAt(0);
            for (int i = 0, count = header.getChildCount(); i < count; i++) {
                View childView = header.getChildAt(i);
                if (childView instanceof ViewGroup) {
                    childView.setFitsSystemWindows(true);
                    ((ViewGroup) childView).setClipToPadding(true);
                }
            }
        } else {
            for (int i = 0, count = root.getChildCount(); i < count; i++) {
                View childView = root.getChildAt(i);
                if (childView instanceof ViewGroup) {
                    childView.setFitsSystemWindows(true);
                    ((ViewGroup) childView).setClipToPadding(true);
                }
            }
        }
    }

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int blendStatusColor(@ColorInt int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    /**
     * 判断是否Flyme4以上
     */
    private static boolean isFlyme4Later() {
        return Build.FINGERPRINT.contains("Flyme_OS_4")
                || Build.VERSION.INCREMENTAL.contains("Flyme_OS_4")
                || Pattern.compile("Flyme OS [4|5]", Pattern.CASE_INSENSITIVE).matcher(Build.DISPLAY).find();
    }

    /**
     * 判断是否为MIUI6以上
     */
    private static boolean isMIUI6Later() {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method mtd = clz.getMethod("get", String.class);
            String val = (String) mtd.invoke(null, "ro.miui.ui.version.name");
            val = val.replaceAll("[vV]", "");
            int version = Integer.parseInt(val);
            return version >= 6;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取状态栏高度
     */
    private static int getStatusBarHeight(Context context) {
        int result = 24;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelSize(resId);
        } else {
            result = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    result, Resources.getSystem().getDisplayMetrics());
        }
        return result;
    }
}
