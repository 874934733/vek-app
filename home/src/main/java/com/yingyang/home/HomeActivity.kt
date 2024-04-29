package com.yingyang.home

import com.alibaba.android.arouter.facade.annotation.Route
import com.yingyangfly.baselib.base.BaseActivity
import com.yingyangfly.baselib.router.RouterUrlCommon
import com.yingyangfly.home.R
import com.yingyangfly.home.databinding.ActivityHomeBinding

/**
 * app首页
 */
@Route(path = RouterUrlCommon.home)
class HomeActivity : BaseActivity<ActivityHomeBinding>() {

    private var tabTitles =
        mutableListOf(R.string.tab1, R.string.tab2, R.string.tab3, R.string.tab4)
    private var tabImgs = mutableListOf(
        R.drawable.tab1_selector,
        R.drawable.tab2_selector,
        R.drawable.tab4_selector,
        R.drawable.tab3_selector,
    )

    override fun initViews() {


    }

    override fun initListener() {

    }

    override fun initData() {

    }
}