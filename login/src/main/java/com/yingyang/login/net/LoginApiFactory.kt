package com.yingyang.login.net

import com.yingyangfly.baselib.net.NetConfig
import com.yingyangfly.baselib.net.initAPI

val Any.LOGIN_API: LoginApiService by lazy {
    LoginApiFactory.api
}

object LoginApiFactory {
    val api: LoginApiService by lazy {
        initAPI(NetConfig.API_URL, LoginApiService::class.java)
    }
}