package com.yingyangfly.baselib.bean

import androidx.annotation.IntDef
import com.yingyangfly.baselib.bean.StatusViewType.Companion.DEFAULT
import com.yingyangfly.baselib.bean.StatusViewType.Companion.DISMISS
import com.yingyangfly.baselib.bean.StatusViewType.Companion.EMPTY
import com.yingyangfly.baselib.bean.StatusViewType.Companion.ERROR
import com.yingyangfly.baselib.bean.StatusViewType.Companion.LOADING

/**
 * @author: gold
 * @time: 2022/2/21 下午3:00
 * @description: View状态枚举类：
 */

@Retention(AnnotationRetention.SOURCE)
@IntDef(DEFAULT, LOADING, DISMISS, EMPTY, ERROR)
annotation class StatusViewType {
    companion object {
        const val DEFAULT = 0
        const val LOADING = 1
        const val DISMISS = 2
        const val EMPTY = 3
        const val ERROR = 4
    }
}
