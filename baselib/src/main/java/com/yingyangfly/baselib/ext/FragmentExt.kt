package com.yingyangfly.baselib.ext

import androidx.annotation.DrawableRes
import com.yingyangfly.baselib.base.BaseFragment

/**
 * 设置页面整体背景
 */
fun BaseFragment<*>.setBackground(@DrawableRes res: Int) {
    bindingBase.baseContainer.setBackgroundResource(res)
}