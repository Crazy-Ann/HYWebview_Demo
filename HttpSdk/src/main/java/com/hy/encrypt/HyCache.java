package com.hy.encrypt;

import com.hynet.mergepay.utils.ParserUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by huyj on 2017/5/9.
 */

public class HyCache {
    public static String encryptKey = "";

    /**
     * 保存encryptKey到SDK
     *
     * @param object
     */
    public static void setEncryptKey(com.alibaba.fastjson.JSONObject object) {
        try {
            JSONObject resutlt = new JSONObject(object.toJSONString());
            if (resutlt.has("apk")) {
                encryptKey = ParserUtil.getInstance().getJsonStringValue(resutlt, "apk");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
