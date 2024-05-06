package com.yingyang.works.webview

import android.webkit.JavascriptInterface
import com.yingyangfly.baselib.webview.utils.log

/**
 * @Author: leavesC
 * @Date: 2021/9/21 15:08
 * @Desc:
 * @Github：https://github.com/leavesC
 */
class JsInterface {

    @JavascriptInterface
    fun showToastByAndroid(log: String) {
        log("showToastByAndroid:$log")
//        showToast(log)
    }

}