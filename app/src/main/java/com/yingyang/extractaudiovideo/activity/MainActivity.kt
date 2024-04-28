package com.yingyang.extractaudiovideo.activity

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.yingyang.extractaudiovideo.databinding.ActivityMainBinding
import com.yingyangfly.baselib.base.BaseActivity
import com.yingyangfly.baselib.router.RouterUrlCommon

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().build(RouterUrlCommon.home)
            .navigation()
        finish()
    }

    override fun initViews() {

    }

    override fun initListener() {

    }

    override fun initData() {

    }
}