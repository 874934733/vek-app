package com.yingyang.fragment

import android.Manifest
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.permissions.XXPermissions
import com.yingyang.home.databinding.FragmentHomeBinding
import com.yingyangfly.baselib.base.BaseFragment
import com.yingyangfly.baselib.bean.def.PermissionList
import com.yingyangfly.baselib.ext.fragmentCheck
import com.yingyangfly.baselib.ext.initCenterTitle
import com.yingyangfly.baselib.ext.setOnSingleClickListener
import com.yingyangfly.baselib.ext.setTitleDividerVisible
import com.yingyangfly.baselib.router.RouterUrlCommon
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
            //音频录制
            btnAudioRecording.setOnSingleClickListener {
                if (audioPermissionBool()) {
                    ARouter.getInstance().build(RouterUrlCommon.record).navigation()
                } else {
                    getAudioPermission()
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
}