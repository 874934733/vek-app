package com.yingyangfly.baselib.ext

import android.text.InputFilter
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.webkit.WebView
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.yingyangfly.baselib.dialog.TipDialogFragment
import java.util.regex.Pattern


/**
 * @author: gold
 * @time: 2022/8/29 上午10:47
 * @description:
 */

/**
 * 防止控件快速点击
 */
inline fun View.click(crossinline listener: (View) -> Unit) {
    // 间隔时间
    val intervalTime = 1000L
    // 最后一次点击时间
    var lastTime = 0L
    this.setOnClickListener {
        // 响应时间
        val tmpTime = System.currentTimeMillis()
        if (tmpTime - lastTime > intervalTime) {
            lastTime = tmpTime
            listener.invoke(this)
        } else { // 点击过快，取消触发
        }
    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

/**
 * 是否显示
 */
fun View.show(show: Boolean) = this.apply {
    this.visibility = if (show) View.VISIBLE else View.GONE
}

/**
 * 是否显示
 */
fun View.invisible(invisible: Boolean) = this.apply {
    this.visibility = if (invisible) View.VISIBLE else View.INVISIBLE
}


/**
 * Kotlin  TextView 扩展函数判空
 */
private fun TextView?.checkNotEmpty(): Boolean {
    if (this == null) {
        return false
    }
    if (this.text.toString().isNotEmpty()) {
        return true
    }
    return false
}

/**
 *  Kotlin  EditText 扩展函数判空
 */
private fun EditText?.checkNotEmpty(): Boolean {
    if (this == null) {
        return false
    }
    if (this.text.toString().isNotEmpty()) {
        return true
    }
    return false
}

fun TextView.setTextColorResource(res: Int) {
    this.setTextColor(context.resources.getColor(res))
}

fun Array<String>.check(act: FragmentActivity, func: () -> Unit) = this.apply {
    XXPermissions.with(act)
        .permission(this)
        .request(object : OnPermissionCallback {
            override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                if (doNotAskAgain) {
                    getPermissions()
                }
            }

            fun getPermissions() {
                TipDialogFragment.TipDialogBuilder()
                    .content("当前应用缺少必要权限,请点击“设置”-“权限”-“权限管理”打开所需权限", 0)
                    .leftBtnText("退出")
                    .rightBtnText("设置")
                    .leftClick({ null }, true)
                    .rightClick({
                        XXPermissions.startPermissionActivity(act)
                        null
                    }, true)
                    .show(act.supportFragmentManager)
            }

            override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                if (allGranted) { // 全部获取权限成功
                    func()
                } else { // 部分获取权限成功
                    getPermissions()
                }
            }
        })
}


/**
 * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
 */
fun WebView.imgReset() {
    loadUrl(
        "javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++) " +
                "{"
                + "var img = objs[i]; " +
                " img.style.maxWidth = '100%'; img.style.height = 'auto'; " +
                "}" +
                "})()"
    )
}

/**
 * EditText限制输入 中英文数字
 * "[0-9a-zA-Z|\u4e00-\u9fa5]+"
 */
fun EditText.typeFilter(pattern: String): InputFilter {
    val typeFilter = InputFilter { source, _, _, _, _, _ ->
        val p = Pattern.compile(pattern)
        val m = p.matcher(source.toString())
        if (!m.matches()) "" else null
    }
    return typeFilter
}


/**
 * 按钮按下动画
 * @return
 */
fun getScaleAnimation(): Animation {
    val end = 0.94f
    val start = 1.0f
    val scaleAnimation: Animation = ScaleAnimation(
        start, end, start, end,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
    )
    scaleAnimation.duration = 100
    scaleAnimation.fillAfter = true
    return scaleAnimation
}

/**
 * 按钮弹起动画
 * @return
 */
fun getEndAnimation(): Animation {
    val end = 0.94f
    val start = 1.0f
    val endAnimation: Animation = ScaleAnimation(
        end, start, end, start,
        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
    )
    endAnimation.duration = 100
    endAnimation.fillAfter = true
    return endAnimation
}


