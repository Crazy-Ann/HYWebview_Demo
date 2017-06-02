package com.hywebview.sdk.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hywebview.sdk.HYChromeClient;
import com.hywebview.sdk.HYWebViewClient;
import com.hywebview.sdk.R;
import com.hywebview.sdk.callback.WebSettingCallback;

/**
 * Created by huyj on 2017/5/12.
 */

public class HYWebview extends FrameLayout {

    private WebView mWebView;

    private ProgressBar mProgressBar;

    private LinearLayout mLinearLayout;

    private Context mContext;

    private WebSettingCallback mWebSettingCallback;

    private HYChromeClient mHYChromeClient;

    private HYWebViewClient mHYWebViewClient;

    private String url;

    //	private String errorHtml = "<html><head><meta charset='UTF-8'></head><body><br><br><br><br><br><br><br><div align='center' style='font-size: smaller'  onclick='window.android.refresh()' ><a href='http://www.baidu.com' style='text-decoration: none'>暂无数据 <br/> 点击调用android方法 </a></div></body></html>";

//	@JavascriptInterface
//	public void refresh() {
//		Toast.makeText(mContext, "js 调用方法", Toast.LENGTH_SHORT).show();
//	}

    public void setWebChromeClient(HYChromeClient hyChromeClient) {
        hyChromeClient.setProgressBar(mProgressBar);
        mWebView.setWebChromeClient(hyChromeClient);
        this.mHYChromeClient = hyChromeClient;
    }

    public void setUserAgentString(String userAgent) {
        mWebView.getSettings().setUserAgentString(mWebView.getSettings().getUserAgentString() + userAgent);
    }

    public void setWebViewClient(HYWebViewClient hyWebViewClient) {
        hyWebViewClient.setFlRoot(this);
        hyWebViewClient.setProgressBar(mProgressBar);
        mWebView.setWebViewClient(hyWebViewClient);
        this.mHYWebViewClient = hyWebViewClient;
    }

    public void setWebSettingCallback(WebSettingCallback webSettingCallback) {
        mWebSettingCallback = webSettingCallback;
    }

    public void setProgressbarRes(@LayoutRes int progressbarRes) {
        if (progressbarRes != 0) {
            mProgressBar = (ProgressBar) LayoutInflater.from(mContext).inflate(progressbarRes, null);
        }
    }

    public HYWebview(Context context) {
        this(context, null);
    }

    public HYWebview(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HYWebview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init();
    }

    private void initView() {
        mLinearLayout = new LinearLayout(mContext);
        mLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(mLinearLayout);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        // 初始化进度条
        if (mProgressBar == null) {
            mProgressBar = (ProgressBar) LayoutInflater.from(mContext).inflate(R.layout.view_web_progress, null);
        }
        mLinearLayout.addView(mProgressBar);
        // 初始化webview
        if (mWebView == null) {
            mWebView = new WebView(mContext);
        }
        mLinearLayout.addView(mWebView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void loadUrl(String url) {
        if (url == null) {
            //可以设置默认加载的Url
//            url = "http://blog.csdn.net/gyw520gyw";
        }
        mWebView.loadUrl(url);
    }


    @SuppressLint("JavascriptInterface")
    public void init() {
        initView();
        WebSettings webSettings = mWebView.getSettings();
        if (mWebSettingCallback != null) {
            mWebSettingCallback.setWebStting(webSettings);
        } else {
            webSettings.setJavaScriptEnabled(true);
            // 设置可以访问文件
            webSettings.setAllowFileAccess(true);
            // 设置可以支持缩放
            webSettings.setSupportZoom(true);
            // 设置默认缩放方式尺寸是far
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
            // 设置出现缩放工具
            webSettings.setBuiltInZoomControls(false);
            webSettings.setDefaultFontSize(16);
            webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        }

        // 设置WebViewClient
        HYWebViewClient hyWebViewClient = new HYWebViewClient((Activity) mContext);
        hyWebViewClient.setFlRoot(this);
        hyWebViewClient.setProgressBar(mProgressBar);
        mWebView.setWebViewClient(hyWebViewClient);

        // 设置WebChromeClient
        HYChromeClient hyChromeClient = new HYChromeClient(null, null);
        hyChromeClient.setProgressBar(mProgressBar);
        mWebView.setWebChromeClient(hyChromeClient);
    }

    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    public void goBack() {
//        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        mHYWebViewClient.setGoback(true);
        mWebView.goBack();
    }

    @SuppressLint("JavascriptInterface")
    public void addJavascriptInterface(Object object, String callName) {
        mWebView.addJavascriptInterface(object, callName);
    }


}
