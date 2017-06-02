package com.heepay.test.demotest;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by huyj on 2017/5/27.
 */

public class MyWebView extends WebView {
    private OnLoadFinishListener mOnLoadFinishListener;
    private boolean hasIntercepted = true;
    private boolean isRendered = false;
    public MyWebView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isRendered){
            Log.d("ArticleWebView", "getContentHeight():"+getContentHeight());
            isRendered = getContentHeight() >0;
            if(mOnLoadFinishListener!= null){
                mOnLoadFinishListener.onLoadFinish();
            }
        }
    }
    public void setOnLoadFinishListener(OnLoadFinishListener onLoadFinishListener){
        this.mOnLoadFinishListener = onLoadFinishListener;
    }

    /**
     * 只对4.4以下有效
     * @param script
     * @return
     */
    public String stringByEvaluatingJavaScriptFromString(String script) {
        Method stringByEvaluatingJavaScriptFromString = null, sendMessageMethod;
        Object webViewCore, browserFrame = null;
        Object webViewObject = this;
        Class webViewClass = WebView.class;
        try {
            Field mProvider = webViewClass.getDeclaredField("mProvider");
            mProvider.setAccessible(true);
            webViewObject = mProvider.get(this);
            webViewClass = webViewObject.getClass();
            Field mWebViewCore = webViewClass.getDeclaredField("mWebViewCore");
            mWebViewCore.setAccessible(true);
            webViewCore = mWebViewCore.get(webViewObject);
            if (webViewCore != null) {
                Log.d("--->", "webviewCore存在");
                sendMessageMethod = webViewCore.getClass().getDeclaredMethod("sendMessage", Message.class);
                sendMessageMethod.setAccessible(true);
                Field mBrowserFrame = webViewCore.getClass().getDeclaredField("mBrowserFrame");
                mBrowserFrame.setAccessible(true);
                browserFrame = mBrowserFrame.get(webViewCore);
                stringByEvaluatingJavaScriptFromString = browserFrame.getClass().getDeclaredMethod("stringByEvaluatingJavaScriptFromString", String.class);
                if (stringByEvaluatingJavaScriptFromString != null) {
                    Log.d("--->", "stringByEvaluatingJavaScriptFromString存在");
                    stringByEvaluatingJavaScriptFromString.setAccessible(true);
                }
            }
            hasIntercepted = true;
        } catch (Throwable e) {
            hasIntercepted = false;
        }
        Object argument = (Object) ((String) script);
        try {
            Object _tagName = stringByEvaluatingJavaScriptFromString.invoke(browserFrame, argument);
            String tagName = (String) _tagName;
            Log.d("---->返回值", ":" + tagName);
            return tagName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
