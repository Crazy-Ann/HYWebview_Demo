package com.hynet.mergepay.http.listener;

public interface OnUpdateProgressListener {

    void updateProgress(int progress, long speed, boolean isDone);
}
