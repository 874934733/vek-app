package com.yingyangfly.baselib.config

import com.yingyangfly.baselib.BuildConfig

/**
 * @author gold
 * @date 2022/9/14 下午4:50
 * @description 相关账号配置
 */
object AccountConfig {

    /**
     * 是否开发环境 ture开发环境、false生产环境
     */
    val DEBUG = BuildConfig.DEBUG

    val SINGLE_MODULE = BuildConfig.SINGLE_MODULE

    /**
     * 腾讯bugly
     */
    const val BUGLY_APPID = BuildConfig.BUGLY_APPID

    /**
     * 接口请求地址
     */
    const val API_URL = BuildConfig.API_URL + "/"
}
