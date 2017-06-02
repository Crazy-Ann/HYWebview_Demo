package com.hynet.mergepay.http;

import android.text.TextUtils;

import com.google.common.collect.Lists;
import com.hynet.mergepay.utils.Base64Util;
import com.hynet.mergepay.utils.JsonFormatUtil;
import com.hynet.mergepay.utils.LogUtil;
import com.hynet.mergepay.utils.ParserUtil;
import com.hynet.mergepay.utils.SecurityUtil;
import com.hynet.mergepay.utils.constant.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class HttpTaskUtil {

    private static HttpTaskUtil mHttpTaskUtil;
    private Map<String, List<HttpTask>> mHttpTasks;

    private HttpTaskUtil() {
        mHttpTasks = new ConcurrentHashMap<>();
    }

    public static synchronized HttpTaskUtil getInstance() {
        if (mHttpTaskUtil == null) {
            mHttpTaskUtil = new HttpTaskUtil();
        }
        return mHttpTaskUtil;
    }

    public static void releaseInstance() {
        if (mHttpTaskUtil != null) {
            mHttpTaskUtil = null;
        }
    }

    public void removeTask(String key) {
        if (mHttpTasks.containsKey(key)) {
            mHttpTasks.remove(key);
        }
    }

    public void addTask(String key, HttpTask task) {
        if (mHttpTasks.containsKey(key)) {
            List<HttpTask> tasks = mHttpTasks.get(key);
            tasks.add(task);
            mHttpTasks.put(key, tasks);
        } else {
            List<HttpTask> tasks = Lists.newArrayList();
            tasks.add(task);
            mHttpTasks.put(key, tasks);
        }
    }

    public boolean contains(String key) {
        return mHttpTasks.containsKey(key);
    }

}
