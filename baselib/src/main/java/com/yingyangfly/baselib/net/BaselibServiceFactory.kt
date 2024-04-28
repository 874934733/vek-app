package com.yingyangfly.baselib.net

import com.yingyangfly.baselib.config.AccountConfig

/**
 * Author: Austin
 * Date: 19-4-11
 * Description:
 */
object BaselibServiceFactory {

    fun getService(): BaselibApiService {
        if (apiService != null) {
            return apiService!!
        }
        return ServiceFactory.getService(AccountConfig.API_URL, BaselibApiService::class.java)
    }

    var apiService: BaselibApiService? = null
}
