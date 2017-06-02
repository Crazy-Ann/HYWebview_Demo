package com.heepay.test.demotest;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private MyWebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebView = (MyWebView) findViewById(R.id.wvDemo);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("https://www.baidu.com/");
        mWebView.setWebChromeClient(new MyWebChromeClient());

    }

    public class MyWebViewClient extends WebViewClient {

        //        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//        @SuppressLint("LongLogTag")
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//            Log.d("---->shouldOverrideUrlLoading--1", request.getUrl().toString());
//            view.loadUrl(view.getUrl());
//            return super.shouldOverrideUrlLoading(view,request);
//        }
//
        @SuppressLint("LongLogTag")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("---->shouldOverrideUrlLoading--2", view.getUrl());
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d("---->onPageStarted", url);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Log.d("---->onPageFinished", url);
//            mWebView.stringByEvaluatingJavaScriptFromString("document.readyState");
            mWebView.setOnLoadFinishListener(new OnLoadFinishListener() {
                @Override
                public void onLoadFinish() {
                    Log.d("---->","onLoadFinish");
                }
            });
            super.onPageFinished(view, url);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            Log.d("---->onProgressChanged", ":" + newProgress);
        }
    }


}
