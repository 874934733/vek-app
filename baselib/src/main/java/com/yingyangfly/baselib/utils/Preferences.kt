package com.yingyangfly.baselib.utils

import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 * @author gold
 * @date 2022/9/6 下午6:31
 * @description MMKV二次封装
 */
object Preferences {
    init {
        MMKV.initialize(AppUtil.getContext().applicationContext)
    }

    fun put(key: String, value: Any?) {
        when (value) {
            is String -> {
                MMKV.defaultMMKV().encode(key, value)
            }
            is Int -> {
                MMKV.defaultMMKV().encode(key, value)
            }
            is Long -> {
                MMKV.defaultMMKV().encode(key, value)
            }
            is Float -> {
                MMKV.defaultMMKV().encode(key, value)
            }
            is Boolean -> {
                MMKV.defaultMMKV().encode(key, value)
            }
            is Double -> {
                MMKV.defaultMMKV().encode(key, value)
            }
            is ByteArray -> {
                MMKV.defaultMMKV().encode(key, value)
            }
            is Parcelable -> {
                MMKV.defaultMMKV().encode(key, value)
            }
        }
    }

    fun getString(key: String): String? {
        return MMKV.defaultMMKV().decodeString(key)
    }

    fun putParcelable(key: String, value: Parcelable) {
        MMKV.defaultMMKV().encode(key, value)
    }

    inline fun <reified T : Parcelable> getParcelable(key: String): T? {
        return MMKV.defaultMMKV().decodeParcelable(key, T::class.java)
    }

    fun getBool(key: String, defaultValue: Boolean): Boolean {
        return MMKV.defaultMMKV().decodeBool(key, defaultValue)
    }

    fun getBool(key: String): Boolean {
        return getBool(key, false)
    }

    fun clearAll() {
        MMKV.defaultMMKV().clearAll()
    }

    fun clearByKey(key: String) {
        MMKV.defaultMMKV().removeValueForKey(key)
    }
}
