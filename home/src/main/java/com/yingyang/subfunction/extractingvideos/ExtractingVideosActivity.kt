package com.yingyang.subfunction.extractingvideos

import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ClipboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.yingyang.home.databinding.ActivityExtractingVideosBinding
import com.yingyangfly.baselib.base.BaseActivity
import com.yingyangfly.baselib.ext.setOnSingleClickListener
import com.yingyangfly.baselib.router.RouterUrlCommon
import com.yingyangfly.baselib.utils.CommonUtils
import com.yingyangfly.baselib.webView.WebViewActivity

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
            btnConfirm.setOnSingleClickListener {
                if (binding.etUrl.getText().toString().isEmpty()) {
                    ToastUtils.showShort("请输入链接！")
                    return@setOnSingleClickListener
                }

                val url = CommonUtils.extractUrl(binding.etUrl.getText().toString())
                if (url.isEmpty()) {
                    ToastUtils.showShort("没有获取到链接!")
                    return@setOnSingleClickListener
                }
                WebViewActivity.open(mContext, url)

            }
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