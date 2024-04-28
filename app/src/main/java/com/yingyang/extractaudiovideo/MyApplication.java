package com.yingyang.extractaudiovideo;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //腾讯Bugly初始化
//        CrashReport.initCrashReport(getApplicationContext(), getString(R.string.bugly_app_id), false);
    }
}
