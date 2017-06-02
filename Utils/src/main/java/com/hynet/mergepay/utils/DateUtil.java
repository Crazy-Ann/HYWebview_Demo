package com.hynet.mergepay.utils;


import com.hynet.mergepay.utils.constant.Regex;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

    public static String getCurrentTime() {
        return getCurrentTime(new Date(System.currentTimeMillis()), Regex.DATE_FORMAT_ALL.getRegext());
    }

    public static String getCurrentTime(Date date, String regex) {
        return new SimpleDateFormat(regex, Locale.getDefault()).format(date);
    }
}
