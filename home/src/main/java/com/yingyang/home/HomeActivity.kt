package com.yingyang.home

import com.alibaba.android.arouter.facade.annotation.Route
import com.yingyangfly.baselib.base.BaseActivity
import com.yingyangfly.baselib.ext.initTitle
import com.yingyangfly.baselib.router.RouterUrlCommon
import com.yingyangfly.home.databinding.ActivityHomeBinding

/**
 * app首页
 */
@Route(path = RouterUrlCommon.home)
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    override fun initViews() {
        initTitle("音视频提取器", showLeftButton = false)

    }

    override fun initListener() {

    }

    override fun initData() {

    }

}