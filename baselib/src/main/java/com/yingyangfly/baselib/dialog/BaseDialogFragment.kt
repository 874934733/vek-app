package com.yingyangfly.baselib.dialog

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import com.yingyangfly.baselib.databinding.FragmentDialogBaseBinding
import com.yingyangfly.baselib.ext.getDbClass
import com.yingyangfly.baselib.utils.ScreenUtil

/**
 * Author: YongChao
 * Date: 19-8-14 上午11:45
 * Description: 网络加载框
 * dialogFragment 内存泄露 参考： https://blog.csdn.net/qq_37492806/article/details/105999003
 */
abstract class BaseDialogFragment<DB : ViewDataBinding> : DialogFragment(), View.OnTouchListener {
    // 基类布局
    lateinit var baseView: FragmentDialogBaseBinding

    // 子布局
    val subViewBinding: DB by lazy {
        getDbClass(this)
    }

    override fun onStart() {
        super.onStart()
        val dm = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(dm)
        dialog?.window?.setLayout(
            (dm.widthPixels - ScreenUtil.dp2px(80f)), ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //如果setCancelable()中参数为true，若点击dialog覆盖不到的activity的空白或者按返回键，
        //则进行cancel，状态检测依次onCancel()和onDismiss()。如参数为false，则按空白处或返回键无反应。缺省为true
        isCancelable = true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        baseView = FragmentDialogBaseBinding.inflate(inflater)
        baseView.llytContentDialog.addView(subViewBinding.root)
        initViews()
        initListener()
        return baseView.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val isShow = showsDialog
        this.showsDialog = false
        super.onActivityCreated(savedInstanceState)
        showsDialog = isShow

        view?.let {
            if (view?.parent != null) {
                throw IllegalAccessException("DialogFragment can not be attached to a container view")
            }
            dialog?.setContentView(view!!)
        }

        activity?.let {
            dialog?.setOwnerActivity(it)
        }

        dialog?.let {
            it.setCancelable(false)
            it.window?.decorView?.setOnTouchListener(this)
            it.setOnKeyListener { dialog, keyCode, event ->
                if ((keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_ESCAPE) && isCancelable) {
                    dismiss()
                    true
                }
                false
            }
        }

        savedInstanceState?.let {
            val dialogState = it.getBundle("android:savedDialogState")
            if (dialogState != null) {
                dialog?.onRestoreInstanceState(dialogState)
            }
        }
    }


    fun clickBackCancel(bool: Boolean) {
        if (bool) {
            dialog?.setOnKeyListener { dialog, keyCode, event ->
                keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN
            }
        }
    }


//    abstract fun layout():Int

    abstract fun initViews()
    abstract fun initListener()

    open fun setContentViewBackground(@DrawableRes resid: Int) {
        baseView.llytContentDialog.setBackgroundResource(resid)
    }

    fun setDialogBackgroundColor(@DrawableRes resid: Int) {
        baseView.llytContentDialog.setBackgroundResource(resid)
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (isCancelable && dialog?.isShowing == true) {
            dismiss()
            return true
        }
        return false
    }

}