package com.yingyangfly.baselib.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * @author 王鹏鹏
 */
public class Density {

    public static final int width = 1194;
    public static float appWidthDensity;
    public static float appWidthScaleDensity;

    public static final int height = 834;
    public static float appHeightDensity;
    public static float appHeightScaleDensity;

    public static void setWidthDensity(final Application application, Activity activity) {
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (appWidthDensity == 0) {
            appWidthDensity = displayMetrics.density;
            appWidthScaleDensity = displayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体发生更改
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appWidthDensity = application.getResources().getDisplayMetrics().density;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        float targetDensity = displayMetrics.widthPixels / width;
        float targetScaleDensity = targetDensity / appWidthDensity * appWidthScaleDensity;
        int targetDensityDpi = (int) (targetDensity * 160);
        DisplayMetrics displayMetrics1 = activity.getResources().getDisplayMetrics();
        displayMetrics1.density = targetDensity;
        displayMetrics1.scaledDensity = targetScaleDensity;
        displayMetrics1.densityDpi = targetDensityDpi;
    }

    public static void setHeightDensity(final Application application, Activity activity) {
        DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (appHeightDensity == 0) {
            appHeightDensity = displayMetrics.density;
            appHeightScaleDensity = displayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体发生更改
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appHeightDensity = application.getResources().getDisplayMetrics().density;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        float targetDensity = displayMetrics.heightPixels / height;
        float targetScaleDensity = targetDensity / appHeightDensity * appHeightScaleDensity;
        int targetDensityDpi = (int) (targetDensity * 160);
        DisplayMetrics displayMetrics1 = activity.getResources().getDisplayMetrics();
        displayMetrics1.density = targetDensity;
        displayMetrics1.scaledDensity = targetScaleDensity;
        displayMetrics1.densityDpi = targetDensityDpi;
    }
}
