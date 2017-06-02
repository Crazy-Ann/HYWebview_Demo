package com.webview.sdk.demo;

import android.webkit.WebView;
import android.widget.Toast;

import com.hywebview.sdk.utils.LogUtil;

/**
 * Created by huyj on 2017/5/13.
 */

public class Android {
    //    private Context mContext;
//    public Android(Context context){
//        this.mContext = context;
//    }
//    @JavascriptInterface
    public static void showToast(WebView webView, String data) {
        Toast.makeText(webView.getContext(), data, Toast.LENGTH_SHORT).show();
        LogUtil.getInstance().d("--->", "data" + data);
    }

    public static void showPrompt(WebView webView, String data) {
        Toast.makeText(webView.getContext(), data, Toast.LENGTH_SHORT).show();
        LogUtil.getInstance().d("--->", "data" + data);
    }

}
