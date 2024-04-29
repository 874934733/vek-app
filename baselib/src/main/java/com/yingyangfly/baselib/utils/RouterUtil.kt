package com.yingyangfly.baselib.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author wpp
 * @description 路由工具类
 */
object RouterUtil {

    /**
     * @param activityPath: activityPath
     * @param flag 模式
     * @description 跳转Activity
     */
    fun gotoActivity(
        activityPath: String, flag: Int? = null, enterAnim: Int = 0, exitAnim: Int = 0
    ) {
        val build = ARouter.getInstance().build(activityPath)
        flag?.let {
            build.withFlags(it)
        }
        build.withTransition(enterAnim, exitAnim).navigation()
    }

    /**
     * @param activityPath:String
     * @param bundle 传递的bundle，ARouter不能传递Int short等
     * @description 带参跳转Acvitity
     */
    fun gotoActivityWithParams(activityPath: String, bundle: Bundle) {
        ARouter.getInstance().build(activityPath).with(bundle).navigation()
    }

    /**
     * @param activityPath:String
     * @description 带参跳转Acvitity
     */
    fun gotoActivityWithInt(activityPath: String, key: String, value: Int) {
        ARouter.getInstance().build(activityPath).withInt(key, value).navigation()
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
        return ARouter.getInstance().build(fragmentPath).with(bundle).navigation() as Fragment
    }
}
