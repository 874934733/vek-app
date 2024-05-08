package com.yingyang.login.net

import com.tencent.bugly.crashreport.biz.UserInfoBean
import com.yingyangfly.baselib.net.BaseResp
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginApiService {

    /**
     * 获取个人中心信息接口
     */
    @POST("vek/user/info")
    suspend fun getUserInfo(): BaseResp<UserInfoBean>

    /**
     * 验证码登录
     */
    @POST("vek/pub/loginMsg")
    suspend fun loginMsg(
        @Query("mobile") mobile: String, @Query("checkCode") checkCode: String
    ): BaseResp<String>

    /**
     * 获取验证码
     */
    @POST("vek/pub/getCheckCode")
    suspend fun sendCode(@Query("mobile") mobile: String): BaseResp<String>
}