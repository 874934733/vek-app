package com.yingyangfly.baselib.ext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.yingyangfly.baselib.R
import com.yingyangfly.baselib.base.BaseActivity
import com.yingyangfly.baselib.base.BaseFragment
import com.yingyangfly.baselib.base.BaseFragmentActivity
import com.yingyangfly.baselib.dialog.BaseDialogFragment
import java.lang.reflect.ParameterizedType

/**
 * @author gold
 * @date 2022/9/5 下午1:57
 * @description Activity 扩展函数  扩展属性
 */

/**
 * 沉浸式状态栏
 */
fun BaseActivity<*>.initBar(full: Boolean) {
    immersionBar {
        titleBar(bindingBase.layoutTitle.titleRootView)
        statusBarDarkFont(true)
        navigationBarColor(R.color.white)
    }
}

/**
 * 沉浸式状态栏
 */
fun BaseDialogFragment<*>.initBar(full: Boolean) {
    immersionBar {
        hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
        statusBarDarkFont(true)
        navigationBarColor(R.color.transparent)
    }
}

/**
 * 沉浸式状态栏
 */
fun BaseFragmentActivity<*>.initBar(full: Boolean) {
    immersionBar {
        hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
        statusBarDarkFont(true)
        navigationBarColor(R.color.transparent)
    }
}

/**
 * 状态栏文字颜色深色
 */
fun BaseActivity<*>.statusBarDarkFont(isDarkFont: Boolean) {
    immersionBar {
        statusBarDarkFont(isDarkFont)
        statusBarDarkFont(true)
        statusBarColor(R.color.white)
    }
}

/**
 * 是否全屏 true: 全屏  false: 非全屏
 */
var BaseActivity<*>.fullScreen: Boolean
    get() {
        TODO()
    }
    set(value) {
        if (value) {
            initBar(value)
        }
    }

/**
 * 关闭软键盘
 */
fun AppCompatActivity.hideSoftKeyboard(focusView: View? = null) {
    val focusV = focusView ?: currentFocus
    focusV?.apply {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}

/**
 * 关闭软键盘
 */
fun Fragment.hideSoftKeyboard(focusView: View? = null) {
    val focusV = focusView ?: activity?.currentFocus
    focusV?.apply {
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
}

/**
 * 打开软键盘
 */
fun AppCompatActivity.showSoftKeyboard(editText: EditText) {
    editText.postDelayed({
        editText.requestFocus()
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
    }, 30)
}

fun Fragment.showSoftKeyboard(editText: EditText) {
    editText.postDelayed({
        editText.requestFocus()
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_FORCED)
    }, 30)
}

/**
 * 添加Fragment
 */
fun BaseActivity<*>.addFragment(viewId: Int, fragment: Fragment) {
    val fragmentTransaction = supportFragmentManager.beginTransaction()
    fragmentTransaction.replace(viewId, fragment)
    fragmentTransaction.commitAllowingStateLoss()
}

/**
 * 添加Fragment
 */
fun BaseFragment<*>.addFragment(viewId: Int, fragment: Fragment) {
    val fragmentTransaction = childFragmentManager.beginTransaction()
    fragmentTransaction.replace(viewId, fragment)
    fragmentTransaction.commitAllowingStateLoss()
}

/**
 * 根据传入的Databinding泛型 获取 Databingding
 */
fun <DB> BaseActivity<*>.getDbClass(t: Any): DB {
    // 使用反射得到ViewBinding的class
    // 当前对象的直接超类的 Type  并 参数化类型
    val type = t.javaClass.genericSuperclass as ParameterizedType
    // 返回表示此类型实际类型参数的 Type 对象的数组  目前泛型只有一个，所以拿第0 个
    val bClass = type.actualTypeArguments[0] as Class<*>
    // 获得私有方法
    val method = bClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
    return method?.invoke(null, layoutInflater) as DB
}

/**
 * 标题栏初始化
 */
fun BaseActivity<*>.initTitle(value: String, showLeftButton: Boolean = true) {
    // 标题为空时，不显示布局
    if (value.isNotEmpty()) {
        bindingBase.layoutTitle.titleRootView.visibility = View.VISIBLE
        bindingBase.layoutTitle.tvTitle.text = value
        bindingBase.layoutTitle.imgLeft.show(showLeftButton)
        bindingBase.layoutTitle.imgLeft.click {
            finish()
        }
    }
}

/**
 * 根据传入的Databinding泛型 获取 Databingding
 */
fun <DB> BaseFragmentActivity<*>.getDbClass(t: Any): DB {
    // 使用反射得到ViewBinding的class
    // 当前对象的直接超类的 Type  并 参数化类型
    val type = t.javaClass.genericSuperclass as ParameterizedType
    // 返回表示此类型实际类型参数的 Type 对象的数组  目前泛型只有一个，所以拿第0 个
    val bClass = type.actualTypeArguments[0] as Class<*>
    // 获得私有方法
    val method = bClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
    return method?.invoke(null, layoutInflater) as DB
}

/**
 * 根据传入的Databinding泛型 获取 Databingding
 */
fun <DB> BaseFragment<*>.getDbClass(t: Any): DB {
    // 使用反射得到ViewBinding的class
    // 当前对象的直接超类的 Type  并 参数化类型
    val type = t.javaClass.genericSuperclass as ParameterizedType
    // 返回表示此类型实际类型参数的 Type 对象的数组  目前泛型只有一个，所以拿第0 个
    val bClass = type.actualTypeArguments[0] as Class<*>
    // 获得私有方法
    val method = bClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
    return method?.invoke(null, layoutInflater) as DB
}

/**
 * 根据传入的Databinding泛型 获取 Databingding
 */
fun <DB> Fragment.getDbClass(t: Any): DB {
    // 使用反射得到ViewBinding的class
    // 当前对象的直接超类的 Type  并 参数化类型
    val type = t.javaClass.genericSuperclass as ParameterizedType
    // 返回表示此类型实际类型参数的 Type 对象的数组  目前泛型只有一个，所以拿第0 个
    val bClass = type.actualTypeArguments[0] as Class<*>
    // 获得私有方法
    val method = bClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
    return method?.invoke(null, layoutInflater) as DB
}
