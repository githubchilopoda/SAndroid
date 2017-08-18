package com.ss.ssframework;

import android.app.Application;
import android.util.Log;

/**
 * Created by 健 on 2017/8/4.
 */

public class SApplication extends Application {

    private final static String TAG = "FApplication";
    private static SApplication instance;

    public static SApplication getAppContext()
    {
        return instance;
    }

    @Override public void onCreate()
    {
        super.onCreate();
        instance = this;
    }

    @Override public void onLowMemory()
    {
        super.onLowMemory();
        Log.e(TAG, "onLowMemory !!");
    }

    @Override public void onTrimMemory(int level)
    {
        super.onTrimMemory(level);
        Log.e(TAG, "onTrimMemory...level: " + level);
        switch (level)
        {
            case TRIM_MEMORY_COMPLETE:
                break;
            default:
                break;
        }
    }

    @Override public void onTerminate()
    {
        super.onTerminate();
        Log.e(TAG, "onTerminate !!");
    }
}
