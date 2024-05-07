package com.yingyang.fragment

import android.Manifest
import com.blankj.utilcode.util.ClipboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.yingyang.home.databinding.FragmentHomeBinding
import com.yingyangfly.baselib.base.BaseFragment
import com.yingyangfly.baselib.bean.def.PermissionList
import com.yingyangfly.baselib.ext.fragmentCheck
import com.yingyangfly.baselib.ext.initCenterTitle
import com.yingyangfly.baselib.ext.setOnSingleClickListener
import com.yingyangfly.baselib.ext.setTitleDividerVisible
import com.yingyangfly.baselib.utils.CommonUtils
import com.yingyangfly.baselib.webView.WebViewActivity
import java.util.Arrays

/**
 * 首页fragment
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    // 添加存储权限的原因是 以防启动页面没有授权存储权限 相机权限，摄像头权限
    private val audioPermission = Arrays.asList(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO
    ).toTypedArray()

    override fun initViews() {
        initCenterTitle("音视频提取")
        setTitleDividerVisible(true)
    }

    override fun initListener() {
        binding {
            //视频提取
            btnVideoExtraction.setOnSingleClickListener {
                PermissionList.storagePermission.fragmentCheck(this@HomeFragment) {
                    if (judgeInfo()) {
                        val url = CommonUtils.extractUrl(binding.etUrl.getText().toString())
                        WebViewActivity.open(mContext, url, "video")
                    }
                }
            }
            btnAudioExtraction.setOnSingleClickListener {
                PermissionList.storagePermission.fragmentCheck(this@HomeFragment) {
                    if (judgeInfo()) {
                        val url = CommonUtils.extractUrl(binding.etUrl.getText().toString())
                        WebViewActivity.open(mContext, url, "audio")
                    }
                }
            }
        }
    }

    override fun initData() {

    }

    override fun onResume() {
        super.onResume()
        registerClipEvents()
    }

    private fun judgeInfo(): Boolean {
        if (binding.etUrl.getText().toString().isEmpty()) {
            ToastUtils.showShort("请输入链接！")
            return false
        }

        val url = CommonUtils.extractUrl(binding.etUrl.getText().toString())
        if (url.isEmpty()) {
            ToastUtils.showShort("没有获取到链接!")
            return false
        }
        return true
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