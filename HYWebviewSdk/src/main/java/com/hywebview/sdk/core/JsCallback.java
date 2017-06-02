/**
 * Summary: 异步回调页面JS函数管理对象
 * Version 1.0
 * Date: 13-11-26
 * Time: 下午7:55
 * Copyright: Copyright (c) 2013
 */

package com.hywebview.sdk.core;

import android.webkit.WebView;

import com.hywebview.sdk.utils.LogUtil;

import java.lang.ref.WeakReference;

public class JsCallback {

    private int mIndex;
    private boolean mCouldGoOn;
    private WeakReference<WebView> mWebViewRef;
    private int mIsPermanent;
    private String mInjectedName;

    public JsCallback(WebView view, String injectedName) {
        mCouldGoOn = true;
        mWebViewRef = new WeakReference<>(view);
        mInjectedName = injectedName;
    }

    public JsCallback(WebView view, String injectedName, int index) {
        mCouldGoOn = true;
        mWebViewRef = new WeakReference<>(view);
        mInjectedName = injectedName;
        mIndex = index;
    }

    public void apply(Object... args) throws JsCallbackException {
        if (mWebViewRef.get() == null) {
            throw new JsCallbackException("the WebView related to the JsCallback has been recycled");
        }
        if (!mCouldGoOn) {
            throw new JsCallbackException("the JsCallback isn't permanent,cannot be called more than once");
        }
        StringBuilder builder = new StringBuilder();
        for (Object arg : args) {
            builder.append(",");
            boolean isStrArg = arg instanceof String;
            if (isStrArg) {
                builder.append("\"");
            }
            builder.append(String.valueOf(arg));
            if (isStrArg) {
                builder.append("\"");
            }
        }
        LogUtil.getInstance().d("---->mInjectedName", mInjectedName);
        LogUtil.getInstance().d("---->mIndex", String.valueOf(mIndex));
        LogUtil.getInstance().d("---->mIsPermanent", String.valueOf(mIsPermanent));
        LogUtil.getInstance().d("---->builder", builder.toString());
        String execJs = String.format(Constant.CALLBACK_JS_FORMAT, mInjectedName, mIndex, mIsPermanent, builder.toString());
//        String execJs = String.format(Constant.CALLBACK_JS_FORMAT, mIsPermanent, builder.toString());
        LogUtil.getInstance().d("---->", execJs);
        mWebViewRef.get().loadUrl(execJs);
        mCouldGoOn = mIsPermanent > 0;
    }

    public void setPermanent(boolean value) {
        mIsPermanent = value ? 1 : 0;
    }
}
