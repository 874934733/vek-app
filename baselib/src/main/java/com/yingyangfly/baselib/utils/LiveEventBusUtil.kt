package com.yingyangfly.baselib.utils

import androidx.lifecycle.LifecycleOwner
import com.jeremyliao.liveeventbus.LiveEventBus

/**
 * @author wangsai
 * @date 2022/9/21 19:42
 * @description LiveEventbus封装
 */
object LiveEventBusUtil {

    inline fun <reified T : Any> send(key: String, value: T?) {
        LiveEventBus.get(key, T::class.java).post(value)
    }

    inline fun <reified T> observer(
        owner: LifecycleOwner,
        key: String,
        crossinline callback: (T) -> Unit
    ) {
        LiveEventBus.get(key, T::class.java).observe(owner) {
            callback.invoke(it)
        }
    }

    fun sendSimple(key: String, value: String?) {
        LiveEventBus.get(key, String::class.java).post(value)
    }

}