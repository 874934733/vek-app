package com.yingyangfly.baselib.net

import com.yingyangfly.baselib.router.RouterUrlCommon
import com.yingyangfly.baselib.utils.JumpUtil
import com.yingyangfly.baselib.utils.User

/**
 * @author gold
 * @date 2022/9/2 下午12:24
 * @description 网络响应基类
 */
open class BaseResp<T>(
    // 响应状态码
    var code: Int = -1,
    // 响应信息
    var message: String = "接口请求错误",
    // 数据实体
    var data: T? = null

) {

    /**
     * 网络数据请求成功
     */
    inline fun y(func: (data: T?) -> Unit) {
        when (this.code) {
            ResponseCode.SUCCESS -> {
                func(data)
            }
            ResponseCode.TOKENLOSEEFFICACY -> {
                User.saveUserSex("")
                User.saveUserAge("")
                User.saveName("")
                User.saveUserId("")
                User.saveOrgCode("")
                User.savePadNo("")
                JumpUtil.jumpActivity(RouterUrlCommon.login)
            }
            else -> {
                n {
                    this.message
                }
            }
        }
    }

    /**
     * 网络数据请求失败
     */
    inline fun n(func: (message: String) -> Unit) {
        if (this.code != ResponseCode.SUCCESS) func(message)
    }
}
