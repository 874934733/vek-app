package com.yingyangfly.baselib.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.yingyangfly.baselib.R
import com.yingyangfly.baselib.ext.setOnSingleClickListener
import com.yingyangfly.baselib.ext.setTextColorResource
import com.yingyangfly.baselib.ext.show
import com.yingyangfly.baselib.utils.ViewTool
import gorden.rxbus2.RxBus

/**
 * 提示框dialog
 */
class TipsDialog(val builder: TipDialogBuilder) : DialogFragment() {

    private var tvTitle: TextView? = null
    private var tvContent: TextView? = null
    private var tvLeft: TextView? = null
    private var tvRight: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.get().register(this)
        isCancelable = false
        dialog?.setCanceledOnTouchOutside(true)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        immersionBar {
            hideBar(BarHide.FLAG_HIDE_BAR)
            navigationBarColor(R.color.transparent)
        }
        val rootView = ViewTool.inflateFragmentPixels(
            activity, R.layout.dialog_tips, container, 1194, 834
        )
        findId(rootView)
        init()
        return rootView
    }

    private fun findId(rootView: View) {
        tvTitle = rootView.findViewById(R.id.tvTitle)
        tvContent = rootView.findViewById(R.id.tvContent)
        tvLeft = rootView.findViewById(R.id.tvLeft)
        tvRight = rootView.findViewById(R.id.tvRight)
        if (builder.title.isNullOrEmpty()) {
            tvTitle?.show(false)
        } else {
            tvTitle?.show(true)
            if (builder.title.isNullOrEmpty().not()) {
                tvTitle?.text = builder.title
            }
            if (builder.titleRes != 0) {
                tvTitle?.setTextColorResource(builder.titleRes)
            }
        }
        if (builder.content.isNullOrEmpty()) {
            tvContent?.show(false)
        } else {
            tvContent?.show(true)
            tvContent?.text = builder.content
        }
        if (builder.contentRes != 0) {
            tvContent?.show(true)
            tvContent?.setText(builder.contentRes)
        }
        if (builder.leftBtnText.isNullOrEmpty().not()) {
            tvLeft?.text = builder.leftBtnText
        }
        if (builder.rightBtnText.isNullOrEmpty().not()) {
            tvRight?.text = builder.rightBtnText
        }
        tvLeft?.setOnSingleClickListener {
            builder.leftClickListener.invoke()
        }
        tvRight?.setOnSingleClickListener {
            builder.rightClickListener.invoke()
        }

    }

    private fun init() {

    }


    class TipDialogBuilder {

        var title = ""
        var titleRes = 0
        var content = ""
        var contentRes = 0
        var leftBtnText = ""
        var rightBtnText = ""
        var leftClickListener: () -> Unit = {}
        var rightClickListener: () -> Unit = {}
        var outCancel = true

        fun title(title: String = "", titleRes: Int = 0): TipDialogBuilder {
            this.title = title
            this.titleRes = titleRes
            return this
        }

        fun content(content: String = "", contentRes: Int = 0): TipDialogBuilder {
            this.content = content
            this.contentRes = contentRes
            return this
        }

        /**
         * 黑色文字
         */
        fun leftBtnText(text: String): TipDialogBuilder {
            leftBtnText = text
            return this
        }

        /**
         * 蓝色文字
         */
        fun rightBtnText(text: String): TipDialogBuilder {
            rightBtnText = text
            return this
        }

        fun rightClick(c: () -> Unit = {}, dimiss: Boolean = false): TipDialogBuilder {
            rightClickListener = {
                c.invoke()
                if (dimiss) {
                    tipsDialog?.dismiss()
                }
            }
            return this
        }

        fun leftClick(c: () -> Unit = {}, dimiss: Boolean = false): TipDialogBuilder {
            leftClickListener = {
                c.invoke()
                if (dimiss) {
                    tipsDialog?.dismiss()
                }
            }
            return this
        }

        var tipsDialog: TipsDialog? = null

        fun build(): TipsDialog? {
            tipsDialog = TipsDialog(this)
            return tipsDialog
        }

        fun show(fragmentManager: FragmentManager) {
            build()?.show(fragmentManager, title)
        }
    }
}