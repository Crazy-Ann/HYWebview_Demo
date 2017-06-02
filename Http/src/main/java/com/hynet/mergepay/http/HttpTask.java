package com.hynet.mergepay.http;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hynet.mergepay.http.constant.HttpRequestType;
import com.hynet.mergepay.http.constant.ResponseCode;
import com.hynet.mergepay.http.listener.OnUpdateProgressListener;
import com.hynet.mergepay.http.model.Parameter;
import com.hynet.mergepay.http.request.ProgressRequestBody;
import com.hynet.mergepay.http.request.RequestParameter;
import com.hynet.mergepay.http.response.HttpResponse;
import com.hynet.mergepay.http.response.ResponseParameter;
import com.hynet.mergepay.utils.JsonFormatUtil;
import com.hynet.mergepay.utils.LogUtil;
import com.hynet.mergepay.utils.constant.Constant;
import com.hynet.mergepay.utils.constant.Regex;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpTask implements Callback, OnUpdateProgressListener {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private HttpRequestType mType;
    private String mUrl;
    private RequestParameter mParameter;
    private OkHttpClient mClient;
    private HttpResponse mResponse;
    private Headers mHeaders;
    private String mHttpTaskKey;

    public HttpTask(HttpRequestType type, String url, RequestParameter parameter, OkHttpClient.Builder builder, HttpResponse response) {
        this.mType = type;
        this.mUrl = url;
        if (parameter == null) {
            this.mParameter = new RequestParameter();
        } else {
            this.mParameter = parameter;
        }
        this.mClient = builder.build();
        this.mResponse = response;
        this.mHttpTaskKey = mParameter.getHttpTaskKey();
        if (TextUtils.isEmpty(mHttpTaskKey)) {
            mHttpTaskKey = Constant.HttpTask.DEFAULT_TASK_KEY;
        }
        HttpTaskUtil.getInstance().addTask(mHttpTaskKey, this);
    }

    public void execute() {
        LogUtil.getInstance().print("execute invoked!!");
        if (mParameter.mBuilder != null) {
            mHeaders = mParameter.mBuilder.build();
        }
        if (mResponse != null) {
            mResponse.onStart();
        }
        enqueue();
    }

    private void enqueue() {
        LogUtil.getInstance().print("enqueue invoked!!");
        String original = mUrl;
        Request.Builder builder = new Request.Builder();
        switch (mType) {
            case GET:
                mUrl = getCompleteUrl(mUrl, mParameter.getParameters(), mParameter.isUrlEncode());
                builder.get();
                break;
            case POST:
                RequestBody post = mParameter.getRequestBody();
                if (post != null) {
                    builder.post(new ProgressRequestBody(post, this));
                }
                break;
            case PUT:
                RequestBody put = mParameter.getRequestBody();
                if (put != null) {
                    builder.put(new ProgressRequestBody(put, this));
                }
                break;
            case DELETE:
                mUrl = getCompleteUrl(mUrl, mParameter.getParameters(), mParameter.isUrlEncode());
                builder.delete();
                break;
            case HEAD:
                mUrl = getCompleteUrl(mUrl, mParameter.getParameters(), mParameter.isUrlEncode());
                builder.head();
                break;
            case PATCH:
                RequestBody bodyPatch = mParameter.getRequestBody();
                if (bodyPatch != null) {
                    builder.put(new ProgressRequestBody(bodyPatch, this));
                }
                break;
            default:
                break;
        }
        if (mParameter.mCacheControl != null) {
            builder.cacheControl(mParameter.mCacheControl);
        }
        builder.url(mUrl).tag(original).headers(mHeaders);
        Request request = builder.build();
        LogUtil.getInstance().print("original:" + original);
        LogUtil.getInstance().print("url:" + mUrl);
        LogUtil.getInstance().print("parameter:" + mParameter.toString());
        LogUtil.getInstance().print("header:" + mHeaders.toString());
        Call call = mClient.newCall(request);
        HttpCallUtil.getInstance().addCall(mUrl, call);
        call.enqueue(this);
    }

    @Override
    public void updateProgress(final int progress, final long speed, final boolean isDone) {
        LogUtil.getInstance().print("updateProgress invoked!!");
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mResponse != null) {
                    mResponse.onProgress(progress, speed, isDone);
                }
            }
        });
    }

    @Override
    public void onFailure(Call call, IOException exception) {
        LogUtil.getInstance().print("onFailure invoked!!");
        ResponseParameter parameter = new ResponseParameter();
        if (exception instanceof SocketTimeoutException) {
            parameter.setTimeout(true);
        } else if (exception instanceof InterruptedIOException && TextUtils.equals(exception.getMessage(), Constant.HttpTask.TIME_OUT)) {
            parameter.setTimeout(true);
        }
        handleResponse(parameter, null);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        LogUtil.getInstance().print("onResponse invoked!!");
        handleResponse(new ResponseParameter(), response);
    }

    private void handleResponse(final ResponseParameter parameter, Response response) {
        LogUtil.getInstance().print("handleResponse invoked!!");
        if (response != null) {
            try {
                parameter.setNoResponse(false);
                parameter.setResponseCode(response.code());
                parameter.setResponseMessage(response.message());
                parameter.setSuccess(response.isSuccessful());
                parameter.setResponseResult(response.body().string());
                parameter.setHeaders(response.headers());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            parameter.setNoResponse(true);
            parameter.setResponseCode(Integer.parseInt(ResponseCode.RESPONSE_CODE_UNKNOWN.getContent()));
            if (parameter.isTimeout()) {
                parameter.setResponseMessage(ResponseCode.RESPONSE_MESSAGE_TIME_OUT.getContent());
            } else {
                parameter.setResponseMessage(ResponseCode.RESPONSE_MESSAGE_UNKNOWN.getContent());
            }
        }
        parameter.setResponse(response);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                onPostExecute(parameter);
            }
        });
    }

    protected void onPostExecute(ResponseParameter parameter) {
        LogUtil.getInstance().print("onPostExecute invoked!!");
        HttpCallUtil.getInstance().removeCall(mUrl);
        if (!HttpTaskUtil.getInstance().contains(mHttpTaskKey)) {
            return;
        }

        if (mResponse != null) {
            mResponse.setHeaders(parameter.getHeaders());
            mResponse.onResponse(parameter.getResponse(), parameter.getResponseResult(), parameter.getHeaders());
            mResponse.onResponse(parameter.getResponseResult(), parameter.getHeaders());
        }
        int code = parameter.getResponseCode();
        String messge = parameter.getResponseMessage();
        LogUtil.getInstance().print("code:" + code + ",messge:" + messge + ",parameter:" + parameter.isNoResponse());
        if (!parameter.isNoResponse()) {
            if (parameter.isSuccess()) {
                LogUtil.getInstance().print("url=" + mUrl + ",result=" + JsonFormatUtil.formatJson(parameter.getResponseResult()) + ",headers=" + parameter.getHeaders().toString());
                parseResponseBody(parameter, mResponse);
            } else {
                LogUtil.getInstance().print("url=" + mUrl + ",response failure code=" + code + ",messge=" + messge);
                if (mResponse != null) {
                    mResponse.onFailed(code, messge);
                }
            }
        } else {
            LogUtil.getInstance().print("url=" + mUrl + "\n response failure code=" + code + " msg=" + messge);
            if (mResponse != null) {
                mResponse.onFailed(code, messge);
            }
        }
        if (mResponse != null) {
            mResponse.onEnd();
        }
    }

    private void parseResponseBody(ResponseParameter parameter, HttpResponse response) {
        LogUtil.getInstance().print("parseResponseBody invoked!!");
        if (response == null) {
            return;
        }
        String result = parameter.getResponseResult();
        LogUtil.getInstance().print("result:" + result);
        LogUtil.getInstance().print("type:" + response.type);
        if (response.type == String.class) {
            response.onSuccess(parameter.getHeaders(), result);
            response.onSuccess(result);
            return;
        } else if (response.type == JSONObject.class) {
            JSONObject object = JSON.parseObject(result);
            if (object != null) {
                response.onSuccess(parameter.getHeaders(), object);
                response.onSuccess(object);
                return;
            }
        } else if (mResponse.type == JSONArray.class) {
            JSONArray array = JSON.parseArray(result);
            if (array != null) {
                response.onSuccess(parameter.getHeaders(), array);
                response.onSuccess(array);
                return;
            }
        } else {
            Object object = JSON.parseObject(result, mResponse.type);
            if (object != null) {
                response.onSuccess(parameter.getHeaders(), object);
                response.onSuccess(object);
                return;
            }
        }
        response.onFailed(Integer.parseInt(ResponseCode.RESPONSE_CODE_DATA_PARSE_ERROR.getContent()), ResponseCode.RESPONSE_MESSAGE_DATA_PARSE_ERROR.getContent());
    }

    private String getCompleteUrl(String url, List<Parameter> parameters, boolean isUrlEncode) {
        StringBuilder builder = new StringBuilder();
        builder.append(url);
        if (builder.indexOf(Regex.QUESTION_MARK.getRegext(), 0) < 0 && parameters.size() > 0) {
            builder.append(Regex.QUESTION_MARK.getRegext());
        }
        int flag = 0;
        for (Parameter parameter : parameters) {
            String key = parameter.getKey();
            String value = parameter.getValue();
            if (isUrlEncode) {
                try {
                    key = URLEncoder.encode(key, Regex.UTF_8.getRegext());
                    value = URLEncoder.encode(value, Regex.UTF_8.getRegext());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            builder.append(key).append(Regex.EQUALS.getRegext()).append(value);
            if (++flag != parameters.size()) {
                builder.append(Regex.AND.getRegext());
            }
        }
        return builder.toString();
    }
}
