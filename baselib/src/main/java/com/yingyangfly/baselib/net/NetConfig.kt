package com.yingyangfly.baselib.net

import com.yingyangfly.baselib.BuildConfig

/**
 * @author gold
 * @date 2022/9/13 下午6:45
 * @copyright (C) 2019-2022, All Rights Reserved
 * @description 网络配置类 ，module组件需要重新赋值
 */
object NetConfig {
    /**
     * 域名
     */
    val DO_MAIN by lazy {
        doMain()
    }

    /**
     * 接口地址
     */
    val API_URL by lazy {
        "$DO_MAIN/"
    }

    private fun doMain(): String {
        return BuildConfig.API_URL
    }
}
