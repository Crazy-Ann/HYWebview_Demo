package com.webview.sdk.demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hywebview.sdk.HYChromeClient;
import com.hywebview.sdk.HYWebViewClient;
import com.hywebview.sdk.utils.BridgeUtil;
import com.hywebview.sdk.utils.LogUtil;
import com.hywebview.sdk.widget.HYWebview;

public class MainActivity extends AppCompatActivity {
    private HYWebview mHYWebview;
    private String url;
    private FrameLayout droidGap;
    private ImageView mImageView;
    public ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHYWebview = (HYWebview) findViewById(R.id.hyWebview);
        mHYWebview.setWebChromeClient(new HYChromeClient("Android", Android.class));
        mHYWebview.setWebViewClient(new HYWebViewClient(this));
        droidGap = (FrameLayout) findViewById(R.id.activity_main);
//        mHYWebview.addJavascriptInterface(new Android(this),"Android");
//        mHYWebview.loadUrl("file:///android_asset/hello.html");
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("load_url")) {
            url = intent.getStringExtra("load_url");
        }
        if (!TextUtils.isEmpty(url)) {
            mHYWebview.loadUrl(url);
        } else {
//            mHYWebview.loadUrl("https://www.baidu.com/");
//            mHYWebview.loadUrl("https://ai.m.taobao.com/index.html?pid=mm_26632357_12468113_52900877&clk1=a4fe3f2494937d3863a2422ea62d838d");
            mHYWebview.loadUrl(" http:192.168.2.95/MchMergepay/Account/Login");
        }
        findViewById(R.id.btShowJsToast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BridgeUtil.invokeJs(mHYWebview, "showJsToast()");
            }
        });

        findViewById(R.id.btShowPrompt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BridgeUtil.invokeJs(mHYWebview, "showPrompt()");
            }
        });

        findViewById(R.id.btShowConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BridgeUtil.invokeJs(mHYWebview, "showConfirm()");
            }
        });


    }


    public class MyChromeClient extends HYChromeClient {

        public MyChromeClient(String injectedName, Class injectedCls) {
            super(injectedName, injectedCls);
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }
    }


    public class MyWebviewClient extends HYWebViewClient {

        public MyWebviewClient(Activity activity) {
            super(activity);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            LogUtil.getInstance().print("---->onPageStarted:" + url);
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            LogUtil.getInstance().print("---->shouldOverrideUrlLoading:" + url);

            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LogUtil.getInstance().print("---->onPageFinished:" + url);
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mHYWebview.canGoBack()) {
            mHYWebview.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    protected ProgressDialog showProgressDialog(Context context, String title, String message) {
        mProgressDialog = ProgressDialog.show(context, title, message, false, true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setOnCancelListener(null);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        return mProgressDialog;
    }
}
