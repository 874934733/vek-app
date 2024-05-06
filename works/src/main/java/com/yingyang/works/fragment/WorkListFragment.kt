package com.yingyang.works.fragment

import com.yingyang.works.adapter.WorkAdapter
import com.yingyang.works.databinding.FragmentWorkListBinding
import com.yingyangfly.baselib.base.BaseFragment
import com.yingyangfly.baselib.dialog.TipDialogFragment
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
        adapter.onDeleteClickListener = {
            TipDialogFragment.TipDialogBuilder().content("确定删除本项作品？", 0).leftBtnText("取消")
                .rightBtnText("确认").leftClick({ null }, true).rightClick({
                    delectWork(it)
                    null
                }, true).show(childFragmentManager)
        }
    }

    /**
     * 删除指定内容
     */
    private fun delectWork(it: VideoBean) {
        if (videoDao != null) {
            videoDao!!.deleteById(it.id)
            loadData()
        }
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