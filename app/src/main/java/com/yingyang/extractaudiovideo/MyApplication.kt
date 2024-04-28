package com.yingyang.extractaudiovideo

import android.content.Context
import androidx.multidex.MultiDex
import com.yingyangfly.baselib.BaseApplication

class MyApplication : BaseApplication() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        //如果需要使用MultiDex，需要在此处调用。
        MultiDex.install(this)
    }
}