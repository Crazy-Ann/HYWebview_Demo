package com.hynet.mergepay.http.listener;

import android.content.Context;

public interface OnHttpRequestTaskListener {

    String getHttpTaskKey();

    Context getHttpTasContext();
}
