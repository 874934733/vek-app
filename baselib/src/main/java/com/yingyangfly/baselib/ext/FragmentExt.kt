package com.yingyangfly.baselib.ext

import android.graphics.Typeface
import android.view.View
import androidx.annotation.DrawableRes
import com.gyf.immersionbar.ktx.immersionBar
import com.yingyangfly.baselib.R
import com.yingyangfly.baselib.base.BaseFragment

/**
 *     author : dev
 *     time   : 2022/11/02
 *     desc   : fragment的扩展
 */
fun BaseFragment<*>.initTitle(value: String) {
    // 标题为空时，不显示布局
    if (value.isNotEmpty()) {
        bindingBase.layoutTitle.titleRootView.visibility = View.VISIBLE
        bindingBase.layoutTitle.tvTitle.text = value
        bindingBase.layoutTitle.imgLeft.click {
            requireActivity().onBackPressed()
        }
    }
}

/**
 *     author : dev
 *     time   : 2022/11/02
 *     desc   : fragment的扩展
 */
fun BaseFragment<*>.initCenterTitle(value: String) {
    // 标题为空时，不显示布局
    if (value.isNotEmpty()) {
        bindingBase.layoutTitle.titleRootView.visibility = View.VISIBLE
        bindingBase.layoutTitle.tvTitle.text = value
        bindingBase.layoutTitle.imgLeft.show(false)
    }
}

/**
 * 标题栏 文字样式
 */
fun BaseFragment<*>.setTitleBold() {
    bindingBase.layoutTitle.tvTitle.typeface = Typeface.DEFAULT_BOLD
}

/**
 * 设置标题栏底部分割线颜色
 */
fun BaseFragment<*>.setTitleDividerVisible(bool: Boolean) {
    bindingBase.layoutTitle.viewDivider.visibility = if (bool) View.VISIBLE else View.GONE
}

/**
 * 标题右边图片控件点击事件
 */
fun BaseFragment<*>.setTitleImgRightSrc(@DrawableRes src: Int) {
    // 标题为空时，不显示布局
    setTitleImgRightVisible(true)
    bindingBase.layoutTitle.imgRight.setImageResource(src)
}

/**
 * 标题右边图片控件点击事件
 */
fun BaseFragment<*>.setTitleImgRightVisible(bool: Boolean) {
    bindingBase.layoutTitle.imgRight.visibility = if (bool) View.VISIBLE else View.GONE
}

/**
 * 标题左边图片控件点击可见
 */
fun BaseFragment<*>.setTitleImgLeftVisible(bool: Boolean) {
    bindingBase.layoutTitle.titleRootView.visibility = View.VISIBLE
    bindingBase.layoutTitle.imgLeft.visibility = if (bool) View.VISIBLE else View.GONE
}

/**
 * 标题右边图片控件点击事件
 */
fun BaseFragment<*>.setTitleImgRightClick(listener: (View) -> Unit) {
    // 标题为空时，不显示布局
    if (bindingBase.layoutTitle.imgRight.visibility == View.VISIBLE) {
        bindingBase.layoutTitle.imgRight.click(listener)
    }
}

/**
 * 标题右边文本控件点击事件
 */
fun BaseFragment<*>.rightClick(listener: (View) -> Unit) {
    // 标题为空时，不显示布局
    if (bindingBase.layoutTitle.tvRight.visibility == View.VISIBLE) {
        bindingBase.layoutTitle.tvRight.click(listener)
    }
}

/**
 * 标题右边文案
 */
var BaseFragment<*>.titleRight: String
    get() = bindingBase.layoutTitle.tvRight.text.toString()
    set(value) {
        if (value.isNotEmpty()) {
            bindingBase.layoutTitle.tvRight.visibility = View.VISIBLE
            bindingBase.layoutTitle.tvRight.text = value
        } else {
            bindingBase.layoutTitle.tvRight.visibility = View.GONE
        }
    }

/**
 * 设置标题栏背景
 */
fun BaseFragment<*>.setTitleBackgroundResource(@DrawableRes src: Int) {
    // 标题为空时，不显示布局
    if (bindingBase.layoutTitle.titleRootView.visibility == View.VISIBLE) {
        bindingBase.layoutTitle.titleRootView.setBackgroundResource(src)
    }
}

/**
 * 沉浸式状态栏
 */
fun BaseFragment<*>.initBar(full: Boolean) {
    immersionBar {
        titleBar(bindingBase.layoutTitle.titleRootView)
        statusBarDarkFont(true)
        navigationBarColor(R.color.white)
    }
}

/**
 * 设置页面整体背景
 */
fun BaseFragment<*>.setBackground(@DrawableRes res: Int) {
    bindingBase.baseContainer.setBackgroundResource(res)
}
