package com.yingyangfly.baselib.utils

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ConvertUtils

/**
 * @author wangsai
 * @date 2022/9/15 18:11
 * @description 屏幕相关工具类
 */
object ScreenUtil {


    /**
     * 获取状态栏高度
     */
    //获取虚拟按键的高度
    fun getNavigationBarHeight(): Int {
        return BarUtils.getNavBarHeight()
    }

    @JvmStatic
    fun getStatusHeight() = BarUtils.getStatusBarHeight()

    @JvmStatic
    fun getScreenWidth() = com.blankj.utilcode.util.ScreenUtils.getScreenWidth()

    @JvmStatic
    fun getScreenHeight() = com.blankj.utilcode.util.ScreenUtils.getScreenHeight()

    /**
     * 透明导航栏
     */
    fun transparentNavBar(window: Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.apply {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.navigationBarColor = Color.parseColor("#01000000")
            }
        }
    }

    /**
     * dp转px
     */
    @JvmStatic
    fun dp2px(dpVal: Float) = ConvertUtils.dp2px(dpVal)

    /**
     * px转dp
     */
    @JvmStatic
    fun px2dp(px: Float) = ConvertUtils.px2dp(px)

    //dp转px
    fun dip2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

}