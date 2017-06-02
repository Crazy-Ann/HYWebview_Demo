package com.hynet.mergepay.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Process;
import android.support.annotation.NonNull;
import android.view.View;

import com.google.common.collect.Lists;

import java.lang.ref.WeakReference;
import java.util.List;

public class ActivityUtil {

    private ActivityUtil() {
        // cannot be instantiated
    }

    private static List<WeakReference<Activity>> allActList = Lists.newArrayList();

    @NonNull
    public static Activity getActivity(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }

        throw new IllegalStateException("View " + view + " is not attached to an Activity");
    }

    public static void add(Activity activity) {
        if (!allActList.contains(activity)) {
            allActList.add(new WeakReference<>(activity));
        }
    }

    public static void remove() {
        for (int i = 0; i < allActList.size(); i++) {
            if (allActList.get(i).get() != null) {
                allActList.get(i).get().finish();
            }
        }
        allActList.clear();
        Process.killProcess(Process.myPid());
    }
}
