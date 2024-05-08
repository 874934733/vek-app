package com.yingyang.fragment

import android.util.Log
import com.blankj.utilcode.util.ClipboardUtils
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.coder.ffmpeg.annotation.MediaAttribute
import com.coder.ffmpeg.call.CommonCallBack
import com.coder.ffmpeg.jni.FFmpegCommand
import com.coder.ffmpeg.utils.FFmpegUtils
import com.yingyang.home.databinding.FragmentHomeBinding
import com.yingyangfly.baselib.base.BaseFragment
import com.yingyangfly.baselib.bean.DownLoadVideoEvent
import com.yingyangfly.baselib.bean.def.PermissionList
import com.yingyangfly.baselib.ext.fragmentCheck
import com.yingyangfly.baselib.ext.initCenterTitle
import com.yingyangfly.baselib.ext.setOnSingleClickListener
import com.yingyangfly.baselib.ext.setTitleDividerVisible
import com.yingyangfly.baselib.utils.CommonUtils
import com.yingyangfly.baselib.utils.RxBusCodes
import com.yingyangfly.baselib.webView.WebViewActivity
import gorden.rxbus2.Subscribe
import gorden.rxbus2.ThreadMode
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

/**
 * 首页fragment
 */
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

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

    /**
     * 发送患者问诊消息
     */
    @Subscribe(code = RxBusCodes.loadVideoSuccess, threadMode = ThreadMode.MAIN)
    fun sendPatientMessage(event: DownLoadVideoEvent) {
        GlobalScope.launch {
            Log.e("wpp", "下载成功")
            val targetPath =
                mContext.externalCacheDir.toString() + File.separator + event.name + ".mp3"
            FFmpegCommand.runCmd(
                FFmpegUtils.extractAudio(event.url, targetPath),
                callback("抽取音频完成", targetPath, event.url)
            )
        }
    }

    private fun callback(msg: String, targetPath: String?, localPath: String): CommonCallBack? {
        return object : CommonCallBack() {
            override fun onStart() {
                ThreadUtils.runOnUiThread {
                    Log.e("wpp", "开始抽取")
                }
            }

            override fun onComplete() {
                Log.d("FFmpegCmd", "onComplete")
                ThreadUtils.runOnUiThread {
                    Log.e("wpp", "音频提取完成")
                }

            }

            override fun onCancel() {
                Log.d("FFmpegCmd", "Cancel")
                ThreadUtils.runOnUiThread {
                    Log.e("wpp", "取消抽取")
                }
            }

            override fun onProgress(progress: Int, pts: Long) {
                var duration: Int? = FFmpegCommand.getMediaInfo(localPath, MediaAttribute.DURATION)
                var progressN = pts / duration!!
                ThreadUtils.runOnUiThread { Log.d("wpp", progress.toString() + "") }
            }

            override fun onError(errorCode: Int, errorMsg: String?) {
                Log.d("FFmpegCmd", errorMsg + "")
                ThreadUtils.runOnUiThread {
                    Log.e("wpp", "抽取错误")
                }
            }
        }
    }
}