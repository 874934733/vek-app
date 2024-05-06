package com.yingyang.works.fragment

import com.yingyang.works.adapter.WorkAdapter
import com.yingyang.works.databinding.FragmentWorkListBinding
import com.yingyangfly.baselib.base.BaseFragment
import com.yingyangfly.baselib.room.VideoBean

/**
 * 作品列表
 */
class WorkListFragment : BaseFragment<FragmentWorkListBinding>() {

    var typeStatus = "" // 0:全部 1我的音频 2 我的视频
    var isShow = false

    private var videoBeans = mutableListOf<VideoBean>()
    private val adapter by lazy { WorkAdapter() }

    override fun initViews() {
        adapter.setData(videoBeans)
        binding.rvWork.adapter = adapter
    }

    override fun initListener() {

    }

    override fun initData() {

    }

    fun loadData() {
        if (isShow) {
            if (videoDao != null) {
                val list = videoDao!!.getAllVideoBean(typeStatus)
                if (list.isNotEmpty()) {
                    videoBeans.clear()
                    videoBeans.addAll(list)
                }
            }
            adapter.setData(videoBeans)
        }
    }
}