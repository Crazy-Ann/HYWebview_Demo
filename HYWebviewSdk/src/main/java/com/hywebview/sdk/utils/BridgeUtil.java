package com.hywebview.sdk.utils;

import com.hywebview.sdk.widget.HYWebview;

/**
 * Created by huyj on 2017/5/13.
 */

public class BridgeUtil {
    private static BridgeUtil mBridgeUtil;

    private BridgeUtil() {
        // cannot be instantiated
    }

    public static synchronized BridgeUtil getInstance() {
        if (mBridgeUtil == null) {
            mBridgeUtil = new BridgeUtil();
        }
        return mBridgeUtil;
    }

    public static void releaseInstance() {
        if (mBridgeUtil != null) {
            mBridgeUtil = null;
        }
    }

    public static void invokeJs(HYWebview webview, String jsMethod) {
        webview.loadUrl("javascript:" + jsMethod);
    }

    public void translate_in(){

    }




}
