package com.hynet.mergepay.utils;

/**
 * Log工具
 *
 * @author yjt
 */
public class LogUtil {

    private static LogUtil mInstance;

    private LogUtil() {
        // cannot be instantiated
    }

    public static synchronized LogUtil getInstance() {
        if (mInstance == null) {
            mInstance = new LogUtil();
        }
        return mInstance;
    }

    public static void releaseInstance() {
        if (mInstance != null) {
            mInstance = null;
        }
    }

    public void v(String tag, String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.v(tag, msg);
    }

    public void v(String tag, String msg, Throwable t) {
        if (BuildConfig.DEBUG)
            android.util.Log.v(tag, msg, t);
    }

    public void d(String tag, String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.d(tag, msg);
    }

    public void d(String tag, String msg, Throwable t) {
        if (BuildConfig.DEBUG)
            android.util.Log.d(tag, msg, t);
    }

    public void i(String tag, String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.i(tag, msg);
    }

    public void i(String tag, String msg, Throwable t) {
        if (BuildConfig.DEBUG)
            android.util.Log.i(tag, msg, t);
    }

    public void w(String tag, String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.w(tag, msg);
    }

    public void w(String tag, String msg, Throwable t) {
        if (BuildConfig.DEBUG)
            android.util.Log.w(tag, msg, t);
    }

    public void e(String tag, String msg) {
        if (BuildConfig.DEBUG)
            android.util.Log.e(tag, msg);
    }

    public void e(String tag, String msg, Throwable t) {
        if (BuildConfig.DEBUG)
            android.util.Log.e(tag, msg, t);
    }

    public void print(Object object) {
//        if (BuildConfig.DEBUG) {
        if (object != null) {
            System.out.println("---->" + object.toString());
        }
//        }
    }
}
