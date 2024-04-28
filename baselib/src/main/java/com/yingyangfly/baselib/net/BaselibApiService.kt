package com.yingyangfly.baselib.net

import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Author: Austin
 * Date: 19-4-1
 * Description:
 */
interface BaselibApiService {

    /**
     * 获取声音 url
     */
    @POST("app/video/getVoiceUrl")
    fun getVoiceUrl(
        @Query("voiceMsg") voiceMsg: String
    ): Observable<XBaseEntity<String>>

    /**
     * 新增评价
     */
    @POST("patient_review/save")
    fun addPatientReview(
        @Body requestBody: RequestBody
    ): Observable<XBaseEntity<String>>

    /**
     * 获取腾讯IM密钥
     */
    @POST("im/user_sign")
    fun getUserSign(@Query("userId") userId: String): Observable<XBaseEntity<String>>

    /**
     * 获取验证码
     */
    @POST("app/getCheckCode")
    fun sendCode(
        @Query("mobile") mobile: String
    ): Observable<XBaseEntity<Any>>

    /**
     * 验证码登录
     */
    @POST("app/loginMsg")
    fun loginMsg(
        @Query("mobile") mobile: String,
        @Query("checkCode") checkCode: String
    ): Observable<XBaseEntity<String>>

    /**
     * 获取验证码
     */
    @POST("large-screen/getCheckCode")
    fun getCheckCode(
        @Query("mobile") mobile: String
    ): Observable<XBaseEntity<Any>>

    /**
     * 验证码登录
     */
    @POST("large-screen/loginMsg")
    fun largeLoginMsg(
        @Query("mobile") mobile: String,
        @Query("checkCode") checkCode: String
    ): Observable<XBaseEntity<String>>

}