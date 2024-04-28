package com.yingyangfly.baselib.net

/**
 * Author: Austin
 * Date: 2018/10/9
 * Description:
 */
class ServiceFactory {
    companion object {
        fun getService(url: String): ApiService {
            return RetrofitHelper.initRetrofit(url).create(ApiService::class.java)
        }

        fun <T> getService(service: Class<T>): T {
            return RetrofitHelper.INSTANCE.retrofit.create(service)
        }

        fun <T> getService(url: String, service: Class<T>): T {
            return RetrofitHelper.initRetrofit(url).create(service)
        }

        fun getService(): ApiService {
            return RetrofitHelper.INSTANCE.retrofit.create(ApiService::class.java)
        }
    }


}