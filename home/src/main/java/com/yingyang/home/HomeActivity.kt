package com.yingyang.home

import android.text.TextUtils
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ClipboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.yingyangfly.baselib.base.BaseActivity
import com.yingyangfly.baselib.bean.def.PermissionList
import com.yingyangfly.baselib.ext.check
import com.yingyangfly.baselib.ext.initTitle
import com.yingyangfly.baselib.ext.setOnSingleClickListener
import com.yingyangfly.baselib.router.RouterUrlCommon
import com.yingyangfly.baselib.utils.CommonUtils
import com.yingyangfly.baselib.webView.WebViewActivity
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
        binding.btnAudioExtraction.setOnSingleClickListener {
            PermissionList.cameraPermission.check(this) {
                var url: String = binding.etUrl.getText().toString()
                if (TextUtils.isEmpty(url)) {
                    ToastUtils.showShort("请输入链接！")
                    return@check
                }

                url = CommonUtils.extractUrl(url)
                if (TextUtils.isEmpty(url)) {
                    ToastUtils.showShort("没有获取到链接!")
                    return@check
                }
                WebViewActivity.open(mContext, url)


            }
        }
    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()
        registerClipEvents()
    }

    /**
     * 注册剪切板复制、剪切事件监听
     */
    private fun registerClipEvents() {
        val content = ClipboardUtils.getText()
        if (content.isNullOrEmpty().not()) {
            val msgFromDouYin = content.toString()
            val url: String = CommonUtils.extractUrl(msgFromDouYin)
            if (url.isNullOrEmpty().not()) {
                binding.etUrl.setText(url)
            }
        }
    }


}