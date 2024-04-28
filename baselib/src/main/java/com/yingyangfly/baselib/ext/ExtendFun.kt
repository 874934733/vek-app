package com.yingyangfly.baselib.ext

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.EditText
import com.alibaba.android.arouter.facade.Postcard
import com.yingyangfly.baselib.utils.ScreenUtil
import com.yingyangfly.baselib.utils.SingleClickUtil
import java.io.Serializable
import java.text.SimpleDateFormat

fun View.setOnSingleClickListener(onClickListener: View.OnClickListener) {
    SingleClickUtil.proxyOnClickListener(this) {
        onClickListener.onClick(it)
    }
}

inline fun View.setOnSingleClickListener(crossinline onclick: (View) -> Unit) {
    SingleClickUtil.proxyOnClickListener(this) {
        onclick(it)
    }
}

fun Postcard.withRxbusCode(code: Int): Postcard {
    withInt("event_code", code)
    return this
}

fun Postcard.withBeanId(id: String): Postcard {
    withString("dy_bean_id", id)
    return this
}

fun Intent.getBeanId(): String? {
    return getStringExtra("dy_bean_id")
}

fun Postcard.withBean(serializable: Serializable): Postcard {
    withSerializable("dy_bean", serializable)
    return this
}

fun Intent.getBean(): Serializable? {
    return getSerializableExtra("dy_bean")
}

fun Int.dp2pix(context: Context): Int {
    return ScreenUtil.dip2px(context, this.toFloat())
}

fun <E> List<E>.safeGet(position: Int): E? {
    if (position < 0 || position >= size || size == 0) {
        return null
    }
    return get(position)
}

fun String.toMoney(): String {
    if (this == "")
        return "0.00"
    return try {
        String.format("%.2f", this.toDouble())
    } catch (e: Exception) {
        "0.00"
    }
}

fun EditText.isEmpty(): Boolean {
    val toString = this.text.toString()
    return toString.isNullOrEmpty()
}

/**
 * 时间格式转换工具
 *
 * @param beforeTime 2022-09-04T08:56:35.000+0000
 * @return 2022-09-04 08:56:35
 */
@SuppressLint("SimpleDateFormat")
fun timeFormat(beforeTime: String): String {
    return try {
        val sdf1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val sdf2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = sdf1.parse(beforeTime)
        sdf2.format(date)
    } catch (e: Exception) {
        beforeTime
    }
}


/**
 * 时间格式转换工具
 *
 * @param beforeTime 2022-09-04T08:56:35.000+0000
 * @return 2022-09-04 08:56:35
 */
@SuppressLint("SimpleDateFormat")
fun timeFormatByTime(beforeTime: String): String {
    return try {
        val sdf1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val sdf2 = SimpleDateFormat("yyyy-MM-dd")
        val date = sdf1.parse(beforeTime)
        sdf2.format(date)
    } catch (e: Exception) {
        beforeTime
    }
}

