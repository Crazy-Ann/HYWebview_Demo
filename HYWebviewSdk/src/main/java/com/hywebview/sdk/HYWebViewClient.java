package com.hywebview.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.Animation;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.hywebview.sdk.core.InjectedWebviewClient;
import com.hywebview.sdk.utils.AnimationUtil;
import com.hywebview.sdk.utils.LogUtil;


/**
 * Created by huyj on 2017/2/20.
 */

public class HYWebViewClient extends InjectedWebviewClient {
    private Activity mActivity;
    private ProgressBar mProgressBar;
    private boolean isFistLoading = true;
    private FrameLayout flRoot;
    private ImageView mImageView;
    private boolean isGoback;

    public void setFlRoot(FrameLayout flRoot) {
        this.flRoot = flRoot;
    }

    public void setGoback(boolean goback) {
        isGoback = goback;
    }


    public void setProgressBar(ProgressBar progressBar) {
        mProgressBar = progressBar;
    }

    public HYWebViewClient(Activity activity) {
        super(activity);
        this.mActivity = activity;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
//        view.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
        doStartAnimation(view);
        LogUtil.getInstance().print("---->onPageStarted:" + url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
        doFindshAnimation(view);
        LogUtil.getInstance().print("---->onPageFinished:" + url);

    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        LogUtil.getInstance().print("---->onReceivedError:" + failingUrl + ",errorCode:" + errorCode);
        LogUtil.getInstance().print("---->onReceivedError:" + failingUrl);

    }

    @TargetApi(23)
    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request,
                                    WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        LogUtil.getInstance().print(
                "---->onReceivedHttpError:" + view.getUrl() + ",errorCode:" + errorResponse.getStatusCode());
    }

    private void doStartAnimation(final WebView view) {
        if (!isFistLoading) {
            if (null == mImageView) {
                LogUtil.getInstance().d("--->", "截屏前进");
                mImageView = new ImageView(view.getContext());
                mImageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                mImageView.setVisibility(View.VISIBLE);
                view.setDrawingCacheEnabled(true);
                Bitmap bitmap = view.getDrawingCache();
                if (null != bitmap) {
                    LogUtil.getInstance().d("--->", "截屏1前进");
                    Bitmap b = Bitmap.createBitmap(bitmap);
                    mImageView.setImageBitmap(b);
//                    mProgressDialog = showProgressDialog(MainActivity.this, "", "页面正在加载...");
                }
                flRoot.addView(mImageView);
            }
        }
        isFistLoading = false;
    }

    private void doFindshAnimation(final WebView view) {
        LogUtil.getInstance().print("---->是否是后退" + isGoback);
        if (!isFistLoading) {
            if (mImageView != null) {
                view.setVisibility(View.GONE);
                System.out.println("加载完成");
                Animation translate_out;
                if (!isGoback) {
                    AnimationUtil.getInstance().translateIn(view.getContext(), R.anim.translate_in, view);
                    translate_out = AnimationUtil.getInstance().translateOut(view.getContext(), R.anim.translate_out, mImageView);
                } else {
                    AnimationUtil.getInstance().translateIn(view.getContext(), R.anim.translate_in2, view);
                    translate_out = AnimationUtil.getInstance().translateOut(view.getContext(), R.anim.translate_out2, mImageView);
                }
                translate_out.setAnimationListener(new CustomerAnimationListener());
            }
            isGoback = false;
        }
        isFistLoading = false;
    }

    private class CustomerAnimationListener implements Animation.AnimationListener {

        public CustomerAnimationListener() {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (null != mImageView) {
                flRoot.removeView(mImageView);
                mImageView = null;
//                            hideProgressDialog();
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

    }


}

