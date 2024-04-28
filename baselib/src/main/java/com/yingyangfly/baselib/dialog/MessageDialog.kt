package com.yingyangfly.baselib.dialog

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.yingyangfly.baselib.R
import com.yingyangfly.baselib.ext.setOnSingleClickListener
import com.yingyangfly.baselib.utils.ViewTool

/**
 * 通知内容弹窗
 */
class MessageDialog : DialogFragment() {

    private var tvTitle: AppCompatTextView? = null
    private var tvContent: AppCompatTextView? = null
    private var btnConform: AppCompatButton? = null
    private var id = ""
    private var content: String = ""
    private var title: String = ""

    var onDialogClickListener: ((id: String) -> Unit)? = null

    fun setContent(id: String, content: String, title: String) {
        this.id = id
        this.content = content
        this.title = title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
        dialog?.setCanceledOnTouchOutside(true)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        immersionBar {
            hideBar(BarHide.FLAG_HIDE_BAR)
            navigationBarColor(R.color.transparent)
        }
        val rootView = ViewTool.inflateFragmentPixels(
            activity, R.layout.dialog_message, container, 1194, 834
        )
        findId(rootView)
        init()
        return rootView
    }

    private fun findId(rootView: View) {
        tvTitle = rootView.findViewById(R.id.tvTitle)
        tvContent = rootView.findViewById(R.id.tvContent)
        tvTitle?.text = if (TextUtils.isEmpty(content).not()) {
            if (TextUtils.isEmpty(title).not()) {
                title
            } else {
                "提示"
            }
        } else {
            "提示"
        }
        tvContent?.text = if (TextUtils.isEmpty(content).not()) {
            content
        } else {
            if (TextUtils.isEmpty(title).not()) {
                title
            } else {
                ""
            }
        }
        btnConform = rootView.findViewById(R.id.btnConform)
    }

    private fun init() {
        btnConform?.setOnSingleClickListener {
            onDialogClickListener?.invoke(id)
            dismiss()
        }
    }
}