package com.yingyangfly.baselib.adapter

import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.yingyangfly.baselib.R
import com.yingyangfly.baselib.ext.click
import com.yingyangfly.baselib.utils.ImageUtil.loadUrl
import com.yingyangfly.baselib.utils.ScreenUtil

/**
 * 加载图片
 */
@BindingAdapter(value = ["loadHeadImg", "isCircle"], requireAll = false)
fun ImageView.loadHeadImg(url: String?, isCircle: Boolean? = false) {
    url?.let {
        loadUrl(url, resId = R.mipmap.core_default_user_icon_lively, isCircle = isCircle)
    }
}

/**
 * 加载图片
 */
@BindingAdapter(value = ["loadImg", "roundRadius"], requireAll = false)
fun ImageView.loadImg(url: String?, roundRadius: Float = 0F) {
    url?.let {
        loadUrl(url, roundRadius = roundRadius)
    }
}

/**
 * View的可见
 */
@BindingAdapter(value = ["isVisible"], requireAll = false)
fun View.isVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE
    else View.INVISIBLE
}

/**
 * View的可见
 */
@BindingAdapter(value = ["isGone"], requireAll = false)
fun View.isGone(isGone: Boolean) {
    visibility = if (isGone) View.GONE
    else View.VISIBLE
}

fun String.StringSizeSpan(startIndex: Int, endIndex: Int, size: Int): SpannableString {
    val ss = SpannableString(this)
    val absSize = AbsoluteSizeSpan(ScreenUtil.dp2px(size.toFloat()))
    ss.setSpan(absSize, startIndex, endIndex, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
    return ss
}


@BindingAdapter(value = ["singleClick"], requireAll = false)
fun View.singleClick(func: () -> Unit) {
    click {
        func.invoke()
    }
}

