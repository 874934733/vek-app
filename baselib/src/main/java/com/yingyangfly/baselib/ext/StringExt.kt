package com.yingyangfly.baselib.ext

import android.content.Context
import com.yingyangfly.baselib.BaseApplication
import com.yingyangfly.baselib.utils.LogUtil
import com.yingyangfly.baselib.utils.ToastUtil

/**
 * @author: gold
 * @time: 2021/12/8 下午3:04
 * @description: String相关扩展函数
 */

/**
 * Toast封装
 */
fun String.show() {
    ToastUtil.show(BaseApplication.appContext, this)
}

/**
 * Toast封装
 */
fun String.toast() {
    ToastUtil.show(BaseApplication.appContext, this)
}

/**
 * ** 不能为空 Toast封装
 */
fun String.toastNotNull() {
    ToastUtil.show(BaseApplication.appContext, this + "不能为空")
}

/**
 * LogUtil封装
 */
fun String.logi() {
    LogUtil.i(this)
}

/**
 * LogUtil封装
 */
fun String.logd() {
    LogUtil.d(this)
}

/**
 * LogUtil封装
 */
fun String.loge() {
    LogUtil.e(this)
}

/**
 * LogUtil封装
 */
fun String.logv() {
    LogUtil.v(this)
}

/**
 *设置字符串颜色
 */
fun String.setColor(color: String): String {
    return "<font color='${color}'>$this</font>"
}

/**
 * 删除字符串最后一个字符
 */
fun String.removeLast(): String {
    if (this.isNotEmpty()) {
        return this.substring(0, this.length - 1)
    }
    return this
}

/**
 *根据手机的分辨率从 dip 的单位 转成为 px(像素)
 */
fun Float.dip2px(context: Context): Float {
    return (this * context.resources.displayMetrics.density + 0.5f)
}

fun Int.dip2px(context: Context): Int {
    return (this * context.resources.displayMetrics.density + 0.5f).toInt()
}
