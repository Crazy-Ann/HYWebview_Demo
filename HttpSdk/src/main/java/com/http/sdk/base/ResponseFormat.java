package com.http.sdk.base;

import com.alibaba.fastjson.JSONObject;
import com.hy.encrypt.HyCache;
import com.hynet.mergepay.http.response.HttpResponse;

public abstract class ResponseFormat extends HttpResponse<JSONObject> {


    public ResponseFormat() {
        super();
        type = JSONObject.class;
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        HyCache.setEncryptKey(jsonObject);
    }
}
