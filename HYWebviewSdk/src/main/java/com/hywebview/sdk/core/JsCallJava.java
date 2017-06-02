package com.hywebview.sdk.core;

import android.text.TextUtils;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.hywebview.sdk.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

public class JsCallJava {

    private HashMap<String, Method> mMethodsMap;
    private String mInjectedName;
    private String mPreloadInterfaceJS;
    private Gson mGson;

    public JsCallJava(String injectedName, Class injectedCls) {
        try {
            if (TextUtils.isEmpty(injectedName)) {
                throw new JsCallJavaException("injected name can not be null");
            }
            mInjectedName = injectedName;
            mMethodsMap = new HashMap<>();
            StringBuilder builder = new StringBuilder("javascript:(function(b){console.log(\"");
            builder.append(mInjectedName);
            builder.append(" initialization begin\");var a={queue:[],callback:function(){var d=Array.prototype.slice.call(arguments,0);var c=d.shift();var e=d.shift();this.queue[c].apply(this,d);if(!e){delete this.queue[c]}}};");
            for (Method method : injectedCls.getDeclaredMethods()) {
                String sign;
                if (method.getModifiers() != (Modifier.PUBLIC | Modifier.STATIC) || (sign = genJavaMethodSign(method)) == null) {
                    continue;
                }
                LogUtil.getInstance().d("---->methodName", method.getName());
                mMethodsMap.put(sign, method);
                builder.append(String.format("a.%s=", method.getName()));
                LogUtil.getInstance().d("---->builder", builder.toString());
            }

            builder.append("function(){var f=Array.prototype.slice.call(arguments,0);if(f.length<1){throw\"");
            builder.append(mInjectedName);
            builder.append(" call error, message:miss method name\"}var e=[];for(var h=1;h<f.length;h++){var c=f[h];var j=typeof c;e[e.length]=j;if(j==\"function\"){var d=a.queue.length;a.queue[d]=c;f[h]=d}}var g=JSON.parse(prompt(JSON.stringify({method:f.shift(),types:e,args:f})));if(g.code!=200){throw\"");
            builder.append(mInjectedName);
            builder.append(" call error, code:\"+g.code+\", message:\"+g.result}return g.result};Object.getOwnPropertyNames(a).forEach(function(d){var c=a[d];if(typeof c===\"function\"&&d!==\"callback\"){a[d]=function(){return c.apply(a,[d].concat(Array.prototype.slice.call(arguments,0)))}}});b.");
            builder.append(mInjectedName);
            builder.append("=a;console.log(\"");
            builder.append(mInjectedName);
            builder.append(" initialization end\")})(window);");
            mPreloadInterfaceJS = builder.toString();
            LogUtil.getInstance().d("---->3", mPreloadInterfaceJS);
        } catch (JsCallJavaException e) {
            LogUtil.getInstance().e("---->", "js initialization error:" + e.getMessage());
        }
    }

    private String genJavaMethodSign(Method method) {
        String sign = method.getName();
        Class[] argsTypes = method.getParameterTypes();
        int len = argsTypes.length;
        if (len < 1 || argsTypes[0] != WebView.class) {
            LogUtil.getInstance().w("---->", "method(" + sign + ") must use webview to be first parameter, will be pass");
            return null;
        }
        for (int k = 1; k < len; k++) {
            Class cls = argsTypes[k];
            if (cls == String.class) {
                sign += "_S";
            } else if (cls == int.class || cls == long.class || cls == float.class || cls == double.class) {
                sign += "_N";
            } else if (cls == boolean.class) {
                sign += "_B";
            } else if (cls == JSONObject.class) {
                sign += "_O";
            } else if (cls == JsCallback.class) {
                sign += "_F";
            } else {
                sign += "_P";
            }
        }
        return sign;
    }

    public String getPreloadInterfaceJS() {
        return mPreloadInterfaceJS;
    }

    public String call(WebView webView, String jsonStr) {
        if (!TextUtils.isEmpty(jsonStr)) {
            try {
                JSONObject callJson = new JSONObject(jsonStr);
                String methodName = callJson.getString("method");
                JSONArray argsTypes = callJson.getJSONArray("types");
                JSONArray argsValues = callJson.getJSONArray("args");
                String sign = methodName;
                int length = argsTypes.length();
                Object[] values = new Object[length + 1];
                int numIndex = 0;
                String currType;

                values[0] = webView;

                for (int k = 0; k < length; k++) {
                    currType = argsTypes.optString(k);
                    if ("string".equals(currType)) {
                        sign += "_S";
                        values[k + 1] = argsValues.isNull(k) ? null : argsValues.getString(k);
                    } else if ("number".equals(currType)) {
                        sign += "_N";
                        numIndex = numIndex * 10 + k + 1;
                    } else if ("boolean".equals(currType)) {
                        sign += "_B";
                        values[k + 1] = argsValues.getBoolean(k);
                    } else if ("object".equals(currType)) {
                        sign += "_O";
                        values[k + 1] = argsValues.isNull(k) ? null : argsValues.getJSONObject(k);
                    } else if ("function".equals(currType)) {
                        sign += "_F";
                        values[k + 1] = new JsCallback(webView, mInjectedName, argsValues.getInt(k));
//                        values[k + 1] = new JsCallback(webView, mInjectedName);
                    } else {
                        sign += "_P";
                    }
                }

                Method method = mMethodsMap.get(sign);
                // 方法匹配失败
                if (method == null) {
                    return getReturn(jsonStr, 500, "not found method(" + sign + ") with valid parameters");
                }
                // 数字类型细分匹配
                if (numIndex > 0) {
                    Class[] methodTypes = method.getParameterTypes();
                    int currIndex;
                    Class currCls;
                    while (numIndex > 0) {
                        currIndex = numIndex - numIndex / 10 * 10;
                        currCls = methodTypes[currIndex];
                        if (currCls == int.class) {
                            values[currIndex] = argsValues.getInt(currIndex - 1);
                        } else if (currCls == long.class) {
                            //WARN: argsJson.getLong(k + defValue) will return a bigger incorrect number
                            values[currIndex] = Long.parseLong(argsValues.getString(currIndex - 1));
                        } else {
                            values[currIndex] = argsValues.getDouble(currIndex - 1);
                        }
                        numIndex /= 10;
                    }
                }
                return getReturn(jsonStr, 200, method.invoke(null, values));
            } catch (IllegalAccessException | JSONException | InvocationTargetException e) {
                e.printStackTrace();
                //优先返回详细的错误信息
                if (e.getCause() != null) {
                    return getReturn(jsonStr, 500, "method execute error:" + e.getCause().getMessage());
                }
                return getReturn(jsonStr, 500, "method execute error:" + e.getMessage());
            }
        } else {
            return getReturn(jsonStr, 500, "call data empty");
        }
    }

    private String getReturn(String reqJson, int stateCode, Object result) {
        String insertRes;
        if (result == null) {
            insertRes = "null";
        } else if (result instanceof String) {
            result = ((String) result).replace("\"", "\\\"");
            insertRes = "\"" + result + "\"";
            LogUtil.getInstance().d("---->是否有进入String","------");
        } else if (!(result instanceof Integer)
                && !(result instanceof Long)
                && !(result instanceof Boolean)
                && !(result instanceof Float)
                && !(result instanceof Double)
                && !(result instanceof JSONObject)) {    // 非数字或者非字符串的构造对象类型都要序列化后再拼接
            if (mGson == null) {
                mGson = new Gson();
            }
            insertRes = mGson.toJson(result);
            LogUtil.getInstance().d("---->是否有进入mGson","------");
        } else {  //数字直接转化
            insertRes = String.valueOf(result);
        }
        String resStr = String.format(Constant.RETURN_RESULT_FORMAT, stateCode, insertRes);
        LogUtil.getInstance().d("---->", mInjectedName + " call json: " + reqJson + " result:" + resStr);
        return resStr;
    }
}