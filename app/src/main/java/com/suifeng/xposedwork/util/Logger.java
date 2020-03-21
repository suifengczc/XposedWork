package com.suifeng.xposedwork.util;

import android.util.Log;

/**
 * 简单封装log
 *
 * @author suifengczc
 */
public class Logger {
    private Logger() {
    }

    public static final String TAG = "HookDemo";

    public static void logi(String msg) {
        Log.i(TAG, msg);
    }

    public static void loge(String msg) {
        Log.e(TAG, msg);
    }

    public static void logw(String msg) {
        Log.w(TAG, msg);
    }

    public static void logd(String msg) {
        Log.d(TAG, msg);
    }

    public static void logv(String msg) {
        Log.v(TAG, msg);
    }


}
