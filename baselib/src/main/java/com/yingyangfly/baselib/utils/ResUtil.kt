package com.yingyangfly.baselib.utils

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.StringUtils

/**
 * @author wangsai
 * @date 2022/9/13 16:50
 * @description
 */
object ResUtil {


    fun getDrawable(@DrawableRes resId: Int): Drawable = ResourceUtils.getDrawable(resId)

    fun getColor( resId: Int) = ColorUtils.getColor(resId)

    fun getArray(resId: Int) = AppUtil.getContext().resources.getStringArray(resId)

    fun getString(@StringRes resId:Int)=StringUtils.getString(resId)?:""
}