package com.yingyangfly.baselib.utils

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

/**
 * Author: Austin
 * Date: 2018/10/8
 * Description: 日志工具类
 */

class LogUtil {

    companion object {
        private var isDebug = false
        fun  initLogger() {
            initLogger(true)
        }
        fun openLog(bool : Boolean) {
            isDebug = bool
        }
        fun initLogger(debug: Boolean) {
//        Logger.addLogAdapter(AndroidLogAdapter())
            isDebug = debug
            val formatStrategy = PrettyFormatStrategy.newBuilder()
                    .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                    .methodCount(2)         // (Optional) How many method line to show. Default 2
//                    .methodOffset(10)        // (Optional) Hides internal method calls up to offset. Default 5
//                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                    .tag("cp")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                    .build()

//            Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
            Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
                override fun isLoggable(priority: Int, tag: String?): Boolean {
                    return isDebug
                }

            })
        }

        fun d(str: String) {
            if (isDebug)
                Logger.d(str)
        }

        fun i(str: String) {
            if (isDebug)
                Logger.i(str)
        }

        fun e(str: String) {
            if (isDebug) {
                Logger.e(str)
            }
        }

        fun v(str: String) {
            if (isDebug) {
                Logger.e(str)
            }
        }

        fun w(str: String) {
            if (isDebug) {
                Logger.e(str)
            }
        }
    }


}