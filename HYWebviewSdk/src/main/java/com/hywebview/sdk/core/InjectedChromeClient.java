package com.hywebview.sdk.core;

import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.hywebview.sdk.utils.LogUtil;


public class InjectedChromeClient extends WebChromeClient {

    private JsCallJava mJsCallJava;
    private boolean mIsInjectedJS;

    public InjectedChromeClient(String injectedName, Class injectedCls) {
        mJsCallJava = new JsCallJava(injectedName, injectedCls);
    }

    public InjectedChromeClient(JsCallJava JsCallJava) {
        mJsCallJava = JsCallJava;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        if (newProgress <= 25) {
            mIsInjectedJS = false;
        } else if (!mIsInjectedJS) {
            view.loadUrl(mJsCallJava.getPreloadInterfaceJS());
            mIsInjectedJS = true;
            LogUtil.getInstance().d("---->", " inject js interface completely on progress " + newProgress);

        }
        super.onProgressChanged(view, newProgress);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        result.confirm(mJsCallJava.call(view, message));
        return true;
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        result.confirm();
        return true;
    }


    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }
}
