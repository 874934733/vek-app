package com.yingyang.subfunction.extractingvideos

import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ClipboardUtils
import com.yingyang.home.databinding.ActivityExtractingVideosBinding
import com.yingyangfly.baselib.base.BaseActivity
import com.yingyangfly.baselib.router.RouterUrlCommon
import com.yingyangfly.baselib.utils.CommonUtils

/**
 * 提取视频
 */
@Route(path = RouterUrlCommon.extractingVideos)
class ExtractingVideosActivity : BaseActivity<ActivityExtractingVideosBinding>() {

    override fun initViews() {
        title = "视频提取"
    }

    override fun initListener() {
        binding {

        }

    }

    override fun initData() {
        binding {

        }
    }

    override fun onResume() {
        super.onResume()
        registerClipEvents()
    }

    /**
     * 读取剪切板数据
     */
    private fun registerClipEvents() {
        val content = ClipboardUtils.getText()
        if (content.isNullOrEmpty().not()) {
            val msgFromDouYin = content.toString()
            val url: String = CommonUtils.extractUrl(msgFromDouYin)
            if (url.isNotEmpty()) {
                binding.etUrl.setText(url)
            }
        }
    }
}