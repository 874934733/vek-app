package com.yingyangfly.baselib.net

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.yingyangfly.baselib.config.AccountConfig
import com.yingyangfly.baselib.net.convert.GsonConverterFactory
import com.yingyangfly.baselib.utils.User
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * @author gold
 * @date 2022/9/2 上午11:37
 * @description 创建Retrofit Okhttp
 */
fun <T> Any.initAPI(url: String, cla: Class<T>): T = BaseNetWork.initRetrofit(url).create(cla)

object BaseNetWork {
    private val timeUnit: TimeUnit = TimeUnit.SECONDS
    private const val connectTimeOut: Long = 15
    private const val readTimeOut: Long = 15
    private const val writeTimeOut: Long = 15

    /**
     * 初始化Retrofit
     */
    fun initRetrofit(url: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(initHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * 初始化OkHttpClient
     */
    private fun initHttpClient(): OkHttpClient {
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
                    .method(newOriginal.method, newOriginal.body)
                    .build()
                val response = chain.proceed(request)
                response
            }
        builder.addInterceptor(OkHttpProfilerInterceptor())
        if (AccountConfig.DEBUG) {
            builder.addInterceptor(LoggingInterceptor())
        }
        return builder.build()
    }

    /**
     * @param oldRequest Request
     * @return Request
     * @date 2022/9/2 上午11:45
     * @description 统一添加参数
     */
    private fun addParam(oldRequest: Request): Request {
        val builder = oldRequest.url
            .newBuilder()
        return oldRequest.newBuilder()
            .method(oldRequest.method, oldRequest.body)
            .url(builder.build())
            .build()
    }
}
