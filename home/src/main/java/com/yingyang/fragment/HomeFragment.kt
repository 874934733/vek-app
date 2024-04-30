package com.yingyang.fragment

import com.yingyang.home.databinding.FragmentHomeBinding
import com.yingyangfly.baselib.base.BaseFragment
import com.yingyangfly.baselib.ext.initCenterTitle
import com.yingyangfly.baselib.ext.setTitleDividerVisible

/**
 * 首页fragment
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun initViews() {
        initCenterTitle("音视频提取")
        setTitleDividerVisible(true)
    }

    override fun initListener() {

    }

    override fun initData() {

    }
}