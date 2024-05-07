package com.yingyang.main.fragment

import com.alibaba.android.arouter.facade.annotation.Route
import com.yingyang.main.databinding.FragmentMainBinding
import com.yingyangfly.baselib.base.BaseFragment
import com.yingyangfly.baselib.ext.initCenterTitle
import com.yingyangfly.baselib.router.RouterUrlCommon

/**
 * 我的fragment
 */
@Route(path = RouterUrlCommon.main)
class MainFragment : BaseFragment<FragmentMainBinding>() {

    override fun initViews() {
        initCenterTitle("我的")


    }

    override fun initListener() {

    }

    override fun initData() {

    }
}