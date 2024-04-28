package com.yingyangfly.baselib.bean

import android.text.TextUtils

/**
 * IM自定义消息关键字
 */
data class MessageBean(
    val businessID: String,
    val data: String,
    val ext: String,
    var description: String,
    var timestamp: Long
) {
    fun getMessageData(): String {
        return if (TextUtils.isEmpty(data)) {
            if (TextUtils.equals("B", businessID)) {
                "您有新的测评任务，请前往查看！"
            } else if (TextUtils.equals("C", businessID)) {
                "您的理疗报告已生成，请前往查看！"
            } else {
                ""
            }
        } else {
            data
        }
    }
}