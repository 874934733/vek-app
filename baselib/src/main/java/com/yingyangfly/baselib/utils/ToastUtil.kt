package com.yingyangfly.baselib.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import com.yingyangfly.baselib.R

/**
 * Author: Austin
 * Date: 2018/10/9
 * Description:
 */
class ToastUtil {
    companion object {
        @SuppressLint("StaticFieldLeak")
        fun show(mContext: Context, msg: String) {
            val toast = ToastUtils(mContext, R.layout.layout_center_toast, msg)
            toast.show()
        }

        fun showLong(mContext: Context, msg: String) {
            Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show()
        }
    }
}