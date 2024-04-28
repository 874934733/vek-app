package com.yingyangfly.baselib.net

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.yingyangfly.baselib.config.AccountConfig
import com.yingyangfly.baselib.utils.User
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * Author: Austin
 * Date: 2018/10/9
 * Description:
 */
class OkHttpHelper private constructor() {

    lateinit var okHttpClient: OkHttpClient
    private val timeUnit: TimeUnit = TimeUnit.SECONDS
    private val connectTimeOut: Long = 15
    private val readTimeOut: Long = 15
    private val writeTimeOut: Long = 15

    init {
        initHttpClient()
    }

    companion object {
        val instance: OkHttpHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            OkHttpHelper()
        }
    }

    private fun initHttpClient(): OkHttpClient? {
        val builder = OkHttpClient.Builder()
            .proxy(Proxy.NO_PROXY)
            .connectTimeout(connectTimeOut, timeUnit)
            .readTimeout(readTimeOut, timeUnit)
            .writeTimeout(writeTimeOut, timeUnit)
            .addInterceptor { chain ->
                val original = chain.request()
                val newOriginal = addParam(original)
                val request = newOriginal.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("app-header", "android")
                    .header("userToken", User.getToken())
                    .method(newOriginal.method, original.body)
                    .build()
                chain.proceed(request)
            }
        if (AccountConfig.DEBUG) {
            builder.addInterceptor(LoggingInterceptor())
        }
        builder.addInterceptor(OkHttpProfilerInterceptor())
        okHttpClient = builder.build()
        return okHttpClient
    }

    private fun addParam(oldRequest: Request): Request {
        val builder = oldRequest.url
            .newBuilder()
        val newRequest = oldRequest.newBuilder()
            .method(oldRequest.method, oldRequest.body)
            .url(builder.build())
            .build()
        return newRequest
    }
}