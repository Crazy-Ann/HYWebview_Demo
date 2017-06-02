package com.hywebview.sdk.utils;

import android.content.Context;
import android.support.annotation.AnimRes;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * 动画特效工具
 *
 * @author yjt
 */
public class AnimationUtil {

    private final int Default = -1;

    private static AnimationUtil mAnimationUtil;

    private AnimationUtil() {
        // cannot be instantiated
    }

    public static synchronized AnimationUtil getInstance() {
        if (mAnimationUtil == null) {
            mAnimationUtil = new AnimationUtil();
        }
        return mAnimationUtil;
    }

    public static void releaseInstance() {
        if (mAnimationUtil != null) {
            mAnimationUtil = null;
        }
    }

    public void translateIn(Context context, @AnimRes int resoure, View view) {
        Animation translate_in = AnimationUtils.loadAnimation(context, resoure);
        translate_in.setFillAfter(true);
        translate_in.setDuration(500);
        translate_in.setDetachWallpaper(true);
        view.setAnimation(translate_in);
    }

    public Animation translateOut(Context context, int resoure, View view) {
        Animation translate_out = AnimationUtils.loadAnimation(context, resoure);
        translate_out.setFillAfter(true);
        translate_out.setDuration(500);
        translate_out.setDetachWallpaper(true);
        view.setAnimation(translate_out);
        return translate_out;
    }


}
