package com.yingyangfly.baselib.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.yingyangfly.baselib.R
import com.yingyangfly.baselib.utils.AppUtil
import com.yingyangfly.baselib.utils.ViewTool

/**
 * Loading
 */
class LoadingDialog constructor(context: Context) : AlertDialog(context, R.style.loading_dialog) {


    var mView: View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    open fun initViews() {
        val inflater = LayoutInflater.from(context)
        mView = inflater.inflate(R.layout.dialog_loading, null)
        setContentView(mView!!)
        setCanceledOnTouchOutside(false)
        setDialogLayout(context,window!!)
    }

    private fun setDialogLayout(context: Context, dialogWindow: Window) {
        val lp = dialogWindow.attributes
        val d = context.resources.displayMetrics // 获取屏幕宽、高用
        lp.width = (d.widthPixels * 0.8).toInt() // 高度设置为屏幕的0.6
        dialogWindow.attributes = lp
    }

    fun show(msg:String){
        mView?.findViewById<TextView>(R.id.tv_loading)?.text = msg
        show()
    }

}