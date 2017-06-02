package com.hynet.mergepay.utils;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Json解析
 *
 * @author yjt
 */
public class ParserUtil {

    private static ParserUtil mInstance;

    private ParserUtil() {
        // cannot be instantiated
    }

    public static synchronized ParserUtil getInstance() {
        if (mInstance == null) {
            mInstance = new ParserUtil();
        }
        return mInstance;
    }

    public static void releaseInstance() {
        if (mInstance != null) {
            mInstance = null;
        }
    }

    private Object parseToObject(Object obj) throws JSONException {
        if (obj != null) {
            if (obj instanceof JSONArray) {
                return parseToList((JSONArray) obj);
            } else if (obj instanceof JSONObject) {
                return parseToMap((JSONObject) obj);
            } else if (obj instanceof String || obj instanceof Boolean
                    || obj instanceof Integer) {
                return obj;
            } else {
                return obj;
            }
        }
        return null;
    }

    public Map<String, Object> parseToMap(String data) {
        try {
            if (!TextUtils.isEmpty(data)) {
                return parseToMap(new JSONObject(data));
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Object> parseToList(String data) {
        try {
            return parseToList(new JSONArray(data));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Object> parseToList(JSONArray array)
            throws JSONException {
        List<Object> list = null;
        if (array != null) {
            list = new ArrayList<Object>();
            for (int i = 0; i < array.length(); i++) {
                Object object = array.get(i);
                if (object != null) {
                    list.add(parseToObject(object));
                }
            }
        }
        return list;
    }

    private Map<String, Object> parseToMap(JSONObject obj)
            throws JSONException {
        Map<String, Object> object = null;
        if (obj != null) {
            object = new HashMap<String, Object>();
            Iterator<String> iterator = obj.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object item = obj.opt(key);
                if (item != null) {
                    object.put(key, parseToObject(item));
                }
            }
        }
        return object;
    }

    public int getJsonIntValue(JSONObject obj, String key, int defValue) {
        try {
            if (obj.has(key)) {
                return obj.getInt(key);
            } else {
                return defValue;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return defValue;
        }
    }

    public long getJsonLongValue(JSONObject obj, String key, long defValue) {
        try {
            if (obj.has(key)) {
                return obj.getLong(key);
            } else {
                return defValue;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return defValue;
        }
    }

    public String getJsonStringValue(JSONObject obj, String key) {
        try {
            if (obj.has(key)) {
                return obj.getString(key);
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean getJsonBooleanValue(JSONObject obj, String key) {
        try {
            return obj.has(key) && obj.getBoolean(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void removeValue(JSONObject obj, String key) {
        if (obj.has(key)) {
            obj.remove(key);
        }
    }
}
