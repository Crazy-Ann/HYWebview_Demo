package com.hynet.mergepay.http.request;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.hynet.mergepay.http.CustomHttpClient;
import com.hynet.mergepay.http.HttpCallUtil;
import com.hynet.mergepay.http.HttpTask;
import com.hynet.mergepay.http.R;
import com.hynet.mergepay.http.constant.HttpRequestType;
import com.hynet.mergepay.http.response.HttpResponse;
import com.hynet.mergepay.utils.NetworkUtil;
import com.hynet.mergepay.utils.ToastUtil;
import com.hynet.mergepay.utils.constant.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

public final class HttpRequest {

    private static HttpRequest mHttpRequest;

    private HttpRequest() {
        // cannot be instantiated
    }

    public static synchronized HttpRequest getInstance() {
        if (mHttpRequest == null) {
            mHttpRequest = new HttpRequest();
        }
        return mHttpRequest;
    }

    public void releaseInstance() {
        if (mHttpRequest != null) {
            mHttpRequest = null;
        }
    }

    private void executeRequest(Context ctx, HttpRequestType type, String url, RequestParameter parameter, OkHttpClient.Builder builder, HttpResponse response) {
        if (NetworkUtil.getInstance().isInternetConnecting(ctx)) {
            if (!TextUtils.isEmpty(url)) {
                if (builder == null) {
                    builder = CustomHttpClient.getInstance().getHttpClientBuilder();
                }
                HttpTask task = new HttpTask(type, url, parameter, builder, response);
                task.execute();
            }
        } else {
            ToastUtil.getInstance().showToast(ctx, ctx.getString(R.string.http_error_prompt1), Toast.LENGTH_SHORT);
        }
    }

    /****************** GET ******************/

    public void doGet(Context ctx, String url) {
        doGet(ctx, url, null, null);
    }

    public void doGet(Context ctx, String url, RequestParameter parameter) {
        doGet(ctx, url, parameter, null);
    }

    public void doGet(Context ctx, String url, HttpResponse response) {
        doGet(ctx, url, null, response);
    }

    /**
     * Get请求
     *
     * @param url
     * @param parameter
     * @param response
     */
    public void doGet(Context ctx, String url, RequestParameter parameter, HttpResponse response) {
        doGet(ctx, url, parameter, Constant.HttpTask.REQUEST_TIME_OUT_PERIOD, response);
    }

    public void doGet(Context ctx, String url, RequestParameter parameter, long timeout, HttpResponse response) {
        OkHttpClient.Builder builder = CustomHttpClient.getInstance().getHttpClientBuilder();
        builder.readTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.connectTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.writeTimeout(timeout, TimeUnit.MILLISECONDS);
        executeRequest(ctx, HttpRequestType.GET, url, parameter, builder, response);
    }

    public void doGet(Context ctx, String url, RequestParameter parameter, OkHttpClient.Builder builder, HttpResponse response) {
        executeRequest(ctx, HttpRequestType.GET, url, parameter, builder, response);
    }

    /****************** POST ******************/

    public void doPost(Context ctx, String url) {
        doPost(ctx, url, null, null);
    }

    public void doPost(Context ctx, String url, RequestParameter parameter) {
        doPost(ctx, url, parameter, null);
    }

    public void doPost(Context ctx, String url, HttpResponse response) {
        doPost(ctx, url, null, response);
    }

    public void doPost(Context ctx, String url, RequestParameter parameter, HttpResponse response) {
        doPost(ctx, url, parameter, Constant.HttpTask.REQUEST_TIME_OUT_PERIOD, response);
    }

    public void doPost(Context ctx, String url, RequestParameter parameter, long timeout, HttpResponse response) {
        OkHttpClient.Builder builder = CustomHttpClient.getInstance().getHttpClientBuilder();
        builder.readTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.connectTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.writeTimeout(timeout, TimeUnit.MILLISECONDS);
        executeRequest(ctx, HttpRequestType.POST, url, parameter, builder, response);
    }

    public void doPost(Context ctx, String url, RequestParameter parameter, OkHttpClient.Builder builder, HttpResponse response) {
        executeRequest(ctx, HttpRequestType.POST, url, parameter, builder, response);
    }

    /****************** PATCH ******************/

    public void doPatch(Context ctx, String url, RequestParameter parameter, OkHttpClient.Builder builder, HttpResponse response) {
        executeRequest(ctx, HttpRequestType.PATCH, url, parameter, builder, response);
    }

    public void doPatch(Context ctx, String url, RequestParameter parameter, HttpResponse response) {
        doPatch(ctx, url, parameter, Constant.HttpTask.REQUEST_TIME_OUT_PERIOD, response);
    }

    public void doPatch(Context ctx, String url, RequestParameter parameter, long timeout, HttpResponse response) {
        OkHttpClient.Builder builder = CustomHttpClient.getInstance().getHttpClientBuilder();
        builder.readTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.connectTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.writeTimeout(timeout, TimeUnit.MILLISECONDS);
        executeRequest(ctx, HttpRequestType.PATCH, url, parameter, builder, response);
    }

    public void doPatch(Context ctx, String url) {
        doPatch(ctx, url, null, null);
    }

    public void doPatch(Context ctx, String url, RequestParameter parameter) {
        doPatch(ctx, url, parameter, null);
    }

    public void doPatch(Context ctx, String url, HttpResponse response) {
        doPatch(ctx, url, null, response);
    }

    /****************** HEAD ******************/

    public void doHead(Context ctx, String url) {
        doHead(ctx, url, null, null);
    }

    public void doHead(Context ctx, String url, RequestParameter parameter) {
        doHead(ctx, url, parameter, null);
    }

    public void doHead(Context ctx, String url, HttpResponse response) {
        doHead(ctx, url, null, response);
    }

    public void doHead(Context ctx, String url, RequestParameter parameter, HttpResponse response) {
        doHead(ctx, url, parameter, Constant.HttpTask.REQUEST_TIME_OUT_PERIOD, response);
    }

    public void doHead(Context ctx, String url, RequestParameter parameter, long timeout, HttpResponse response) {
        OkHttpClient.Builder builder = CustomHttpClient.getInstance().getHttpClientBuilder();
        builder.readTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.connectTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.writeTimeout(timeout, TimeUnit.MILLISECONDS);
        executeRequest(ctx, HttpRequestType.HEAD, url, parameter, builder, response);
    }

    public void doHead(Context ctx, String url, RequestParameter parameter, OkHttpClient.Builder builder, HttpResponse response) {
        executeRequest(ctx, HttpRequestType.HEAD, url, parameter, builder, response);
    }

    /****************** DELETE ******************/

    public void doDelete(Context ctx, String url) {
        doDelete(ctx, url, null, null);
    }

    public void doDelete(Context ctx, String url, RequestParameter parameter) {
        doDelete(ctx, url, parameter, null);
    }

    public void doDelete(Context ctx, String url, HttpResponse response) {
        doDelete(ctx, url, null, response);
    }

    public void doDelete(Context ctx, String url, RequestParameter parameter, HttpResponse response) {
        doDelete(ctx, url, parameter, Constant.HttpTask.REQUEST_TIME_OUT_PERIOD, response);
    }

    public void doDelete(Context ctx, String url, RequestParameter parameter, long timeout, HttpResponse response) {
        OkHttpClient.Builder builder = CustomHttpClient.getInstance().getHttpClientBuilder();
        builder.readTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.connectTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.writeTimeout(timeout, TimeUnit.MILLISECONDS);
        executeRequest(ctx, HttpRequestType.DELETE, url, parameter, builder, response);
    }

    public void doDelete(Context ctx, String url, RequestParameter parameter, OkHttpClient.Builder builder, HttpResponse response) {
        executeRequest(ctx, HttpRequestType.DELETE, url, parameter, builder, response);
    }

    /****************** PUT ******************/

    public void doPut(Context ctx, String url) {
        doPut(ctx, url, null, null);
    }

    public void doPut(Context ctx, String url, RequestParameter parameter) {
        doPut(ctx, url, parameter, null);
    }

    public void doPut(Context ctx, String url, HttpResponse response) {
        doPut(ctx, url, null, response);
    }

    public void doPut(Context ctx, String url, RequestParameter parameter, HttpResponse response) {
        doPut(ctx, url, parameter, Constant.HttpTask.REQUEST_TIME_OUT_PERIOD, response);
    }

    public void doPut(Context ctx, String url, RequestParameter parameter, long timeout, HttpResponse response) {
        OkHttpClient.Builder builder = CustomHttpClient.getInstance().getHttpClientBuilder();
        builder.readTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.connectTimeout(timeout, TimeUnit.MILLISECONDS);
        builder.writeTimeout(timeout, TimeUnit.MILLISECONDS);
        executeRequest(ctx, HttpRequestType.PUT, url, parameter, builder, response);
    }

    public void doPut(Context ctx, String url, RequestParameter parameter, OkHttpClient.Builder builder, HttpResponse response) {
        executeRequest(ctx, HttpRequestType.PUT, url, parameter, builder, response);
    }

    public void cancel(String url) {
        if (!TextUtils.isEmpty(url)) {
            Call call = HttpCallUtil.getInstance().getCall(url);
            if (call != null) {
                call.cancel();
            }
            HttpCallUtil.getInstance().removeCall(url);
        }
    }
}
