package com.hy.encrypt;

/**
 * Created by huyj on 2017/5/27.
 */

public class HyEncryptUtil {

    static {
        System.loadLibrary("hyencrypt");
    }

    public static native String generateRequestData(String json, String apk, boolean isEncrypt);
}
