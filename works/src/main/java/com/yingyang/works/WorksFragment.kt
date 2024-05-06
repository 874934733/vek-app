package com.yingyang.works

import com.alibaba.android.arouter.facade.annotation.Route
import com.yingyang.works.adapter.WorkAdapter
import com.yingyang.works.databinding.FragmentWorksBinding
import com.yingyangfly.baselib.base.BaseFragment
import com.yingyangfly.baselib.ext.initCenterTitle
import com.yingyangfly.baselib.room.VideoBean
import com.yingyangfly.baselib.router.RouterUrlCommon

/**
 * 作品fragment
 */
@Route(path = RouterUrlCommon.works)
class WorksFragment : BaseFragment<FragmentWorksBinding>() {

    private var videoBeans = mutableListOf<VideoBean>()
    private val adapter by lazy { WorkAdapter() }

    override fun initViews() {
        initCenterTitle("我的作品")
        val videoBean = VideoBean()
        videoBean.url = "https://v.douyin.com/i2kC1hba/"
        videoBean.date = System.currentTimeMillis().toString()
        videoBean.name = "课堂"
        videoBean.url = "https://v.douyin.com/i2kC1hba/"
        videoBeans.add(videoBean)
        videoBeans.add(videoBean)
        videoBeans.add(videoBean)
        videoBeans.add(videoBean)
        videoBeans.add(videoBean)
        videoBeans.add(videoBean)
        videoBeans.add(videoBean)
        adapter.setData(videoBeans)
        binding.rvWork.adapter = adapter
    }

    override fun initListener() {

    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()
//        if (videoDao != null) {
//            val list = videoDao!!.getAllVideoBean()
//            if (list.isNotEmpty()) {
//                videoBeans.clear()
//                videoBeans.addAll(list)
//            }
//        }
//        adapter.setData(videoBeans)
    }
}