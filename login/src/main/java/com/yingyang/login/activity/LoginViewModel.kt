package com.yingyang.login.activity

import com.tencent.bugly.crashreport.biz.UserInfoBean
import com.yingyang.login.net.LOGIN_API
import com.yingyangfly.baselib.mvvm.BaseViewModel

/**
 * @author gold
 * @date 2022/9/13 下午6:37
 */
class LoginViewModel : BaseViewModel() {

    fun sendCode(
        mobile: String,
        fail: ((msg: String) -> Unit)? = null,
        success: ((success: String?) -> Unit)? = null,
    ) = launchFlow(true) {
        LOGIN_API.sendCode(mobile)
    }.runUI(
        success, fail
    )

    fun loginMsg(
        mobile: String,
        checkCode: String,
        fail: ((msg: String) -> Unit)? = null,
        success: ((success: String?) -> Unit)? = null,
    ) = launchFlow(true) {
        LOGIN_API.loginMsg(mobile, checkCode)
    }.runUI(
        success, fail
    )

    fun getUserInfo(
        fail: ((msg: String) -> Unit)? = null,
        success: ((success: UserInfoBean?) -> Unit)? = null,
    ) = launchFlow(true) {
        LOGIN_API.getUserInfo()
    }.runUI(
        success, fail
    )
}
