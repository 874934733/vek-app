package com.yingyangfly.baselib.net

import com.yingyangfly.baselib.config.AccountConfig
import com.yingyangfly.baselib.net.convert.XlGsonConverterFactory
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * Author: Austin
 * Date: 2018/10/8
 * Description: 单例形式创建Retrofit实例
 */
class RetrofitHelper private constructor() {
    lateinit var retrofit: Retrofit

    init {
        initRetrofit()
    }

    companion object {
        val INSTANCE: RetrofitHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            RetrofitHelper()
        }

        fun initRetrofit(url: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(url)
                .client(OkHttpHelper.instance.okHttpClient)
                .addConverterFactory(XlGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }
    }

    private fun initRetrofit() {
        retrofit = initRetrofit(AccountConfig.API_URL)
    }

}
