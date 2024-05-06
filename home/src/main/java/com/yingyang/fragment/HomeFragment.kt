package com.yingyang.fragment

import android.Manifest
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.coder.ffmpeg.annotation.MediaAttribute
import com.coder.ffmpeg.call.CommonCallBack
import com.coder.ffmpeg.jni.FFmpegCommand
import com.coder.ffmpeg.utils.FFmpegUtils
import com.coder.ffmpegtest.utils.FileUtils
import com.hjq.permissions.XXPermissions
import com.yingyang.home.databinding.FragmentHomeBinding
import com.yingyangfly.baselib.base.BaseFragment
import com.yingyangfly.baselib.bean.def.PermissionList
import com.yingyangfly.baselib.ext.fragmentCheck
import com.yingyangfly.baselib.ext.initCenterTitle
import com.yingyangfly.baselib.ext.setOnSingleClickListener
import com.yingyangfly.baselib.ext.setTitleDividerVisible
import com.yingyangfly.baselib.router.RouterUrlCommon
import com.yingyangfly.baselib.utils.ToastUtil
import com.yingyangfly.baselib.utils.ToastUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
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

    private var mAudioPath: String? = null
    private var mVideoPath: String? = null
    private var targetPath: String? = null
    private var mAudioBgPath: String? = null
    private var mImagePath: String? = null

    override fun initViews() {
        initCenterTitle("音视频提取")
        setTitleDividerVisible(true)
    }

    override fun initListener() {
        binding {
            //视频提取
            btnVideoExtraction.setOnSingleClickListener {
                ARouter.getInstance().build(RouterUrlCommon.extractingVideos).navigation()
            }
            //音频录制
            btnAudioRecording.setOnSingleClickListener {
                if (audioPermissionBool()) {
                    ARouter.getInstance().build(RouterUrlCommon.record).navigation()
                } else {
                    getAudioPermission()
                }
            }

            //音频拼接
            btnAudioSplicing.setOnSingleClickListener {
                if (audioPermissionBool()) {
                    FileUtils.copy2Memory(mContext, "test.mp3")
                    FileUtils.copy2Memory(mContext, "test.mp4")
                    FileUtils.copy2Memory(mContext, "testbg.mp3")
                    FileUtils.copy2Memory(mContext, "water.png")
                    mAudioPath = File(requireActivity().externalCacheDir, "test.mp3").absolutePath
                    mVideoPath = File(requireActivity().externalCacheDir, "test.mp4").absolutePath
                    mAudioBgPath =
                        File(requireActivity().externalCacheDir, "testbg.mp3").absolutePath
                    mImagePath = File(requireActivity().externalCacheDir, "water.png").absolutePath

                    targetPath =
                        requireActivity().externalCacheDir.toString() + File.separator + "target8.mp3"
                    GlobalScope.launch {
                        FFmpegCommand.runCmd(
                            FFmpegUtils.concatAudio(
                                mAudioPath, mAudioPath, targetPath
                            ), callback("音频拼接完成", targetPath)
                        )
                    }
                }
            }
        }
    }

    override fun initData() {

    }

    /**
     * 请求录音权限
     */
    private fun getAudioPermission() {
        PermissionList.audioPermission.fragmentCheck(this) {
            ARouter.getInstance().build(RouterUrlCommon.record).navigation()
        }
    }


    /**
     * 是否有录音权限
     */
    private fun audioPermissionBool(): Boolean {
        return XXPermissions.isGranted(mContext, audioPermission)
    }


    private fun callback(msg: String, targetPath: String?): CommonCallBack? {
        return object : CommonCallBack() {
            override fun onStart() {
            }

            override fun onComplete() {
                requireActivity().runOnUiThread {
                    ToastUtil.show(mContext, "拼接完成")
                    dimissLoading()
                }
            }

            override fun onCancel() {
                requireActivity().runOnUiThread {
                    dimissLoading()
                }
            }

            override fun onProgress(progress: Int, pts: Long) {
                var duration: Int? = FFmpegCommand.getMediaInfo(mAudioPath, MediaAttribute.DURATION)
                var progressN = pts / duration!!
                requireActivity().runOnUiThread {
                    showLoading()
                }
            }

            override fun onError(errorCode: Int, errorMsg: String?) {
                requireActivity().runOnUiThread {
                    dimissLoading()
                }
            }
        }
    }
}