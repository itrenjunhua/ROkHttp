package com.renj.okhttp;

import android.util.Log;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-03-21   0:44
 * <p>
 * 描述：自定义的日志处理器
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RLog {
    private static String TAG = "ROkHttp Log";
    private static boolean showLog = BuildConfig.DEBUG;
    private static boolean IS_FULL_CLASSNAME;
    private static int LOG_LEVEL = Log.VERBOSE;

    public static void setShowLog(boolean showLog) {
        RLog.showLog = showLog;
    }

    public static void setFullClassName(boolean isFullClassName) {
        RLog.IS_FULL_CLASSNAME = isFullClassName;
    }

    public static void setLogLevel(int level) {
        RLog.LOG_LEVEL = level;
    }

    public static void setAppTAG(String tag) {
        RLog.TAG = tag;
    }

    public static void v(String msg) {
        if (showLog && LOG_LEVEL <= Log.VERBOSE) {
            Log.v(TAG, getLogTitle() + msg);
        }
    }


    public static void d(String msg) {
        if (showLog && LOG_LEVEL <= Log.DEBUG) {
            Log.d(TAG, getLogTitle() + msg);
        }
    }

    public static void i(String msg) {
        if (showLog && LOG_LEVEL <= Log.INFO) {
            Log.i(TAG, getLogTitle() + msg);
        }
    }

    public static void w(String msg) {
        if (showLog && LOG_LEVEL <= Log.WARN) {
            Log.w(TAG, getLogTitle() + msg);
        }
    }

    public static void e(String msg) {
        if (showLog && LOG_LEVEL <= Log.ERROR) {
            Log.e(TAG, getLogTitle() + msg);
        }
    }

    private static String getLogTitle() {
        StackTraceElement elm = Thread.currentThread().getStackTrace()[4];
        String className = elm.getClassName();
        if (!IS_FULL_CLASSNAME) {
            int dot = className.lastIndexOf('.');
            if (dot != -1) {
                className = className.substring(dot + 1);
            }
        }
        return className + "." + elm.getMethodName() + "(" + elm.getLineNumber() + ")" + ": ";
    }
}
