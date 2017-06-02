package com.hywebview.sdk;

import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.hywebview.sdk.core.InjectedChromeClient;
import com.hywebview.sdk.core.JsCallJava;
import com.hywebview.sdk.utils.LogUtil;

/**
 * Created by huyj on 2017/2/20.
 */

public class HYChromeClient extends InjectedChromeClient {
    private ProgressBar mProgressBar;

    public void setProgressBar(ProgressBar progressBar) {
        mProgressBar = progressBar;
    }

    public HYChromeClient(String injectedName, Class injectedCls) {
        super(injectedName, injectedCls);
    }

    public HYChromeClient(JsCallJava JsCallJava) {
        super(JsCallJava);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
        // to do your work
        // ...
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        // to do your work
        // ...
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        LogUtil.getInstance().d("--->", "progress:" + newProgress);
        if (mProgressBar != null) {
            mProgressBar.setProgress(newProgress);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        LogUtil.getInstance().d("--->", "onReceivedTitle:" + title);
    }
}
