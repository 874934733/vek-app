package com.yingyangfly.baselib.net

import java.io.Serializable

/**
 * Author: Austin
 * Date: 2018/10/9
 * Description:
 */
class XBaseEntity<T> : Serializable {
    var code: Int = -1
    var message: String = ""
    var data: T? = null
    var success: Boolean = false
    override fun toString(): String {
        return "XBaseEntity(code=$code, message='$message', data=$data, success=$success)"
    }

}
