package com.webview.sdk.demo;

import android.os.Bundle;
import android.support.test.espresso.core.deps.guava.collect.Lists;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.http.sdk.base.RequestParametersFormat;
import com.http.sdk.base.ResponseFormat;
import com.hynet.mergepay.http.Configuration;
import com.hynet.mergepay.http.CustomHttpClient;
import com.hynet.mergepay.http.model.Parameter;
import com.hynet.mergepay.http.request.HttpRequest;
import com.hynet.mergepay.http.request.RequestParameter;
import com.hywebview.sdk.utils.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    String url = "http://mchmergepay.jyallpay.com/Api/ClientApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CustomHttpClient.getInstance().initialize(new Configuration.Builder()
                .setParameters(Lists.<Parameter>newArrayList())
                .setHeaders(new Headers.Builder().build())
                .setTimeout(6000)
                .setInterceptors(Lists.<Interceptor>newArrayList())
                .setDebug(true).build());
        findViewById(R.id.btTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String params = "{\"biz_content\":\"{\\\"device_type\\\":\\\"Android\\\"}\",\"client_id\":\"869336027711868\",\"client_info\":\"{\\\"device_type\\\":\\\"Android_Mobile\\\",\\\"uid\\\":\\\"869336027711868\\\",\\\"client_ver\\\":3,\\\"os_ver\\\":\\\"5.1.1\\\",\\\"os_type\\\":\\\"Android\\\"}\",\"client_ver\":\"3\",\"mch_uid\":\"\",\"method\":\"heemoney.client.get.version\",\"timestamp\":" + getTimeStamp() + "}";

                final JSONObject object = new JSONObject();
                JSONObject bizObject = new JSONObject();
                try {
                    bizObject.put("device_type", "Android");
                    object.put("client_id", "869336027711868");
                    object.put("timestamp", getTimeStamp());
                    object.put("client_ver", "3");
                    object.put("method", "heemoney.client.get.version");
                    object.put("client_info", "{\\\"device_type\\\":\\\"Android_Mobile\\\",\\\"uid\\\":\\\"869336027711868\\\",\\\"client_ver\\\":3,\\\"os_ver\\\":\\\"5.1.1\\\",\\\"os_type\\\":\\\"Android\\\"}");
                    object.put("biz_content", bizObject.toString());
                    RequestParameter requestParameter = RequestParametersFormat.formatParameters(object, false);
                    HttpRequest.getInstance().doPost(LoginActivity.this, url, requestParameter, new ResponseFormat() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            super.onSuccess(jsonObject);
                            LogUtil.getInstance().d("--->成功：", "--" + jsonObject.toString());
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onProgress(int progress, long speed, boolean isDone) {

                        }

                        @Override
                        public void onEnd() {

                        }

                        @Override
                        public void onResponse(Response httpResponse, String response, Headers headers) {

                        }

                        @Override
                        public void onResponse(String response, Headers headers) {

                        }

                        @Override
                        public void onSuccess(Headers headers, com.alibaba.fastjson.JSONObject jsonObject) {

                        }

                        @Override
                        public void onFailed(int code, String message) {

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        findViewById(R.id.btTest2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String params = "{\"method\" : \"heemoney.client.send.sms\",\"client_info\" : \"{\\\"carrierName\\\":\\\"460020264454993\\\",\\\"countCores\\\":6,\\\"ipAddress\\\":\\\"172.17.79.98\\\",\\\"phoneID\\\":\\\"869336027711868\\\",\\\"phoneModel\\\":\\\"1\\\",\\\"phoneName\\\":\\\"Mi-4c\\\",\\\"systemName\\\":\\\"REL\\\",\\\"systemVersion\\\":\\\"5.1.1\\\"}\",\"client_ver\" : \"1\",\"biz_content\" : \"{ \\n \\\"mobile\\\" : \\\"13426236872\\\",\\\"sms_type\\\" : \\\"1\\\"}\",\"version\" : \"1\",\"timestamp\" : \"20170509162916\",\"client_id\" : \"869336027711868\"}";
                final JSONObject object = new JSONObject();
                JSONObject bizObject = new JSONObject();
                /**
                 * {"emp_account":"","login_account":"13264131514","login_ext_code":"23","password":"11111111"}
                 */
                try {
                    bizObject.put("login_account", "13264131514");
                    bizObject.put("login_ext_code", "517");
                    bizObject.put("password", "11111111");
                    object.put("client_id", "869336027711868");
                    object.put("timestamp", getTimeStamp());
                    object.put("client_ver", "3");
                    object.put("method", "heemoney.client.login");
                    object.put("client_info", "{\\\"device_type\\\":\\\"Android_Mobile\\\",\\\"uid\\\":\\\"869336027711868\\\",\\\"client_ver\\\":3,\\\"os_ver\\\":\\\"5.1.1\\\",\\\"os_type\\\":\\\"Android\\\"}");
                    object.put("biz_content", bizObject.toString());
                    RequestParameter requestParameter = RequestParametersFormat.formatParameters(object, true);
                    HttpRequest.getInstance().doPost(LoginActivity.this, url, requestParameter, new ResponseFormat() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            super.onSuccess(jsonObject);
                            LogUtil.getInstance().d("--->成功：", "--" + object.toString());
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onProgress(int progress, long speed, boolean isDone) {

                        }

                        @Override
                        public void onEnd() {

                        }

                        @Override
                        public void onResponse(Response httpResponse, String response, Headers headers) {

                        }

                        @Override
                        public void onResponse(String response, Headers headers) {

                        }

                        @Override
                        public void onSuccess(Headers headers, com.alibaba.fastjson.JSONObject jsonObject) {

                        }

                        @Override
                        public void onFailed(int code, String message) {

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btGeturl).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final JSONObject object = new JSONObject();
                JSONObject bizObject = new JSONObject();
                try {
                    bizObject.put("code", "payresult");
                    object.put("client_id", "869336027711868");
                    object.put("timestamp", getTimeStamp());
                    object.put("client_ver", "3");
                    object.put("method", "heemoney.client.redirect.url");
                    object.put("client_info", "{\\\"device_type\\\":\\\"Android_Mobile\\\",\\\"uid\\\":\\\"869336027711868\\\",\\\"client_ver\\\":3,\\\"os_ver\\\":\\\"5.1.1\\\",\\\"os_type\\\":\\\"Android\\\"}");
                    object.put("biz_content", bizObject.toString());
                    RequestParameter requestParameter = RequestParametersFormat.formatParameters(object, true);
                    HttpRequest.getInstance().doPost(LoginActivity.this, url, requestParameter, new ResponseFormat() {
                        @Override
                        public void onSuccess(JSONObject jsonObject) {
                            super.onSuccess(jsonObject);
                            LogUtil.getInstance().d("--->成功：", "--" + jsonObject.toString());
                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onProgress(int progress, long speed, boolean isDone) {

                        }

                        @Override
                        public void onEnd() {

                        }

                        @Override
                        public void onResponse(Response httpResponse, String response, Headers headers) {

                        }

                        @Override
                        public void onResponse(String response, Headers headers) {

                        }

                        @Override
                        public void onSuccess(Headers headers, com.alibaba.fastjson.JSONObject jsonObject) {

                        }

                        @Override
                        public void onFailed(int code, String message) {

                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private String getTimeStamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date(System.currentTimeMillis()));
    }
}
