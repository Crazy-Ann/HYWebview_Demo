package com.hywebview.sdk.core;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class InjectedWebviewClient extends WebViewClient {

    private Activity mActivity;

    public InjectedWebviewClient(Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (mActivity == null || mActivity.isFinishing()) {
            return;
        }
        view.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (mActivity == null || mActivity.isFinishing()) {
            return;
        }
        view.setLayerType(View.LAYER_TYPE_NONE, null);
        super.onPageFinished(view, url);
    }
}
