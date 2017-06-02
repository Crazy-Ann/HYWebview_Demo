package com.http.sdk.base;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.hy.encrypt.HyCache;
import com.hy.encrypt.HyEncryptUtil;
import com.hynet.mergepay.http.request.RequestParameter;
import com.hynet.mergepay.utils.LogUtil;

import java.util.Iterator;
import java.util.Set;

public class RequestParametersFormat {

    protected RequestParametersFormat() {
        // cannot be instantiated
    }

    public static RequestParameter formatParameters(JSONObject params, boolean isEncrypt) throws Exception {
        LogUtil.getInstance().print("--->请求前参数"+params.toString());
        RequestParameter parameter = new RequestParameter();
        parameter.setJsonType(true);
        String requestData = HyEncryptUtil.generateRequestData(params.toString(), HyCache.encryptKey, isEncrypt);
        LogUtil.getInstance().print("---->key" + HyCache.encryptKey);
        LogUtil.getInstance().print("---->签名加密后：requestData" + requestData);
        params = JSONObject.parseObject(requestData);
        Set<String> keySetJson = params.keySet();
        Iterator param_keys = keySetJson.iterator();
        while (param_keys.hasNext()) {
            String key = param_keys.next().toString();
            String value = params.getString(key);
            if (!TextUtils.isEmpty(value)) {
                parameter.addFormDataParameter(key, value);
                LogUtil.getInstance().print("---->" + key + "---" + value);
            }
        }
        return parameter;
    }

}
