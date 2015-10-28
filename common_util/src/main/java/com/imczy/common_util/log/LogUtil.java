package com.imczy.common_util.log;

import android.util.Log;

/**
 * Created by chenzhiyong on 15/10/27.
 */
public class LogUtil {

    public static int VERBOSE = 1;
    public static int DEBUG = 2;
    public static int INFO = 3;
    public static int WARN = 4;
    public static int ERROR = 5;
    private static int level = VERBOSE;
    private static boolean isLog = true;

    public static void i(String msg) {
        if (isPrintLog() && level <= INFO)
            Log.i("LogUtil", msg + "");
    }

    public static void i(String tag, String msg) {
        if (isPrintLog() && level <= INFO)
            Log.i(tag, msg + "");
    }

    public static void d(String msg) {
        if (isPrintLog() && level <= DEBUG)
            Log.d("LogUtil", msg + "");
    }

    public static void d(String tag, String msg) {
        if (isPrintLog() && level <= DEBUG)
            Log.d(tag, msg + "");
    }

    public static void w(String msg) {
        if (isPrintLog() && level <= WARN)
            Log.w("LogUtil", msg + "");
    }

    public static void w(String tag, String msg) {
        if (isPrintLog() && level <= WARN)
            Log.w(tag, msg + "");
    }

    public static void e(String msg) {
        if (isPrintLog() && level <= ERROR)
            Log.e("LogUtil", msg + "");
    }

    public static void e(String msg, Throwable tr) {
        if (isPrintLog() && level <= ERROR)
            Log.e("LogUtil", msg + "", tr);
    }

    public static void e(String tag, String msg) {
        if (isPrintLog() && level <= ERROR)
            Log.e(tag, msg + "");
    }

    public static void v(String msg) {
        if (isPrintLog() && level <= VERBOSE)
            Log.v("LogUtil", msg + "");
    }

    public static void v(String tag, String msg) {
        if (isPrintLog() && level <= VERBOSE)
            Log.v(tag, msg + "");
    }

    public static boolean isPrintLog() {
        return isLog;
    }
}
