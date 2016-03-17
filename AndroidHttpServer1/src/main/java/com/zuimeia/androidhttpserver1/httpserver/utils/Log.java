package com.zuimeia.androidhttpserver1.httpserver.utils;


import com.zuimeia.androidhttpserver1.BuildConfig;

public class Log {
    private final static String LOGTAG = "httpserver";

    public static final boolean DEBUG = BuildConfig.DEBUG;

    public static void v(String msg) {
        if (DEBUG) {
            if (msg == null)
                msg = "null";
            android.util.Log.v(LOGTAG, msg);
        }
    }

    public static void v(String TAG, String msg) {
        if (DEBUG) {
            if (msg == null)
                msg = "null";
            android.util.Log.v(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG) {
            if (msg == null)
                msg = "null";
            android.util.Log.e(LOGTAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (DEBUG) {
            if (msg == null)
                msg = "null";
            android.util.Log.e(TAG, msg);
        }
    }

    public static void e(String TAG, String msg, Throwable ex) {
        if (DEBUG) {
            if (msg == null)
                msg = "null";
            android.util.Log.e(TAG, msg, ex);
        }
    }

    public static void i(String msg) {
        if (DEBUG) {
            if (msg == null)
                msg = "null";
            android.util.Log.i(LOGTAG, msg);
        }
    }


    public static void i(String TAG, String msg) {
        if (DEBUG) {
            if (msg == null)
                msg = "null";
            android.util.Log.i(TAG, msg);
        }
    }

    public static void i(String TAG, String msg, Throwable ex) {
        if (DEBUG) {
            if (msg == null)
                msg = "null";
            android.util.Log.i(TAG, msg, ex);
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            if (msg == null)
                msg = "null";
            android.util.Log.d(LOGTAG, msg);
        }
    }

    public static void d(String TAG, String msg) {
        if (DEBUG) {
            if (msg == null)
                msg = "null";
            android.util.Log.d(TAG, msg);
        }
    }


    public static void d(String TAG, String msg, Throwable ex) {
        if (DEBUG) {
            if (msg == null)
                msg = "null";
            android.util.Log.d(TAG, msg, ex);
        }
    }


    public static void w(String msg) {
        if (DEBUG) {
            if (msg == null)
                msg = "null";
            android.util.Log.w(LOGTAG, msg);
        }
    }

    public static void w(String TAG, String msg) {
        if (DEBUG) {
            if (msg == null)
                msg = "null";
            android.util.Log.w(TAG, msg);
        }
    }

    public static void w(String TAG, Throwable ex) {
        if (DEBUG) {
            android.util.Log.w(TAG, ex);
        }
    }

    public static void w(String TAG, String msg, Throwable ex) {
        if (DEBUG) {
            if (msg == null)
                msg = "null";
            android.util.Log.w(TAG, msg, ex);
        }
    }

    public static int VERBOSE = 2;

    public static boolean isLoggable(String str, int VERBOSE) {
        return DEBUG;
    }


}
