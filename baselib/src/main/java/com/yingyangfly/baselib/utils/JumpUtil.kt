package com.yingyangfly.baselib.utils

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.yingyangfly.baselib.R
import com.yingyangfly.baselib.router.RouterUrlCommon

object JumpUtil {

    fun jumpActivity(path: String) {
        ARouter.getInstance().build(path)
            .navigation()
    }

    fun jumpActivity(path: String, content: Context) {
        if (TextUtils.equals(RouterUrlCommon.home, path)) {
            ARouter.getInstance().build(path)
                .withTransition(R.anim.rightin, R.anim.rightout)
                .navigation(content)
        } else {
            ARouter.getInstance().build(path)
                .withTransition(R.anim.leftin, R.anim.leftout)
                .navigation(content)
        }
    }

    fun jumpActivityWithUrl(path: String, url: String) {
        ARouter.getInstance().build(path)
            .withString("url", url)
            .navigation()
    }

    fun jumpActivityWithUrl(path: String, url: String, content: Context) {
        if (TextUtils.equals(RouterUrlCommon.home, path)) {
            ARouter.getInstance().build(path)
                .withString("url", url)
                .withTransition(R.anim.rightin, R.anim.rightout)
                .navigation(content)
        } else {
            ARouter.getInstance().build(path)
                .withString("url", url)
                .withTransition(R.anim.leftin, R.anim.leftout)
                .navigation(content)
        }
    }

    fun jumpActivityWithString(path: String, string: Map.Entry<String, String>) {
        ARouter.getInstance().build(path)
            .withString(string.key, string.value)
            .navigation()
    }

    /**
     * @param activityPath: activityPath
     * @param flag 模式
     * @description 跳转Activity
     */
    fun gotoActivity(
        activityPath: String,
        flag: Int? = null,
        enterAnim: Int = 0,
        exitAnim: Int = 0
    ) {
        val build = ARouter.getInstance()
            .build(activityPath)
        flag?.let {
            build.withFlags(it)
        }
        build.withTransition(enterAnim, exitAnim)
            .navigation()
    }

    /**
     * @param activityPath:String
     * @param bundle 传递的bundle，ARouter不能传递Int short等
     * @description 带参跳转Acvitity
     */
    fun gotoActivityWithParams(activityPath: String, bundle: Bundle) {
        ARouter.getInstance().build(activityPath)
            .with(bundle)
            .navigation()
    }

    /**
     * @param activityPath:String
     * @description 带参跳转Acvitity
     */
    fun gotoActivityWithInt(activityPath: String, key: String, value: Int) {
        ARouter.getInstance().build(activityPath)
            .withInt(key, value)
            .navigation()
    }

    /**
     * 获取fragment
     */
    fun getFragment(fragmentPath: String): Fragment {
        return ARouter.getInstance().build(fragmentPath).navigation() as Fragment
    }

    /**
     * 获取fragment带参数
     */
    fun getFragmentWithParams(fragmentPath: String, bundle: Bundle): Fragment {
        return ARouter.getInstance().build(fragmentPath)
            .with(bundle).navigation() as Fragment
    }
}