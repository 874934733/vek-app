package com.yingyangfly.baselib.dialog

import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.TypedValue
import android.view.View
import androidx.fragment.app.FragmentManager
import com.yingyangfly.baselib.base.BaseActivity
import com.yingyangfly.baselib.databinding.DialogTipBinding
import com.yingyangfly.baselib.ext.setTextColorResource
import com.yingyangfly.baselib.ext.show

/**
 * @author gold
 * @date 2022/9/9 上午9:24
 * @description 弹框封装
 */
class TipDialogFragment(val builder: TipDialogBuilder) : BaseDialogFragment<DialogTipBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = builder.outCancel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (builder.title.isNullOrEmpty()) {
            subViewBinding.tvTipDialogTitle.show(false)
        } else {
            if (builder.title.isNullOrEmpty().not()) {
                subViewBinding.tvTipDialogTitle.text = builder.title
            }
            if (builder.titleRes != 0) {
                subViewBinding.tvTipDialogTitle.setTextColorResource(builder.titleRes)
            }
        }

        if (builder.content.isNullOrEmpty().not()) {
            subViewBinding.tvTipDialogContent.text = Html.fromHtml(builder.content)
        }

        if (builder.contentSize != 0) {
            subViewBinding.tvTipDialogContent.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                builder.contentSize.toFloat()
            )
        }

        if (builder.topImg != 0) {
            subViewBinding.ivTipDialogImg.show(true)
            subViewBinding.ivTipDialogImg.setImageResource(builder.topImg)
        }

        if (builder.contentRes != 0) {
            subViewBinding.tvTipDialogContent.setText(builder.contentRes)
        }

        if (builder.contentInfo.isNullOrEmpty().not()) {
            subViewBinding.tvTipDialogContent.text = builder.contentInfo
        }

        if (builder.leftBtnText.isNullOrEmpty()) {
            subViewBinding.confirmLayout.show(false)
            subViewBinding.tvConfirm.show(true)
        } else {
            subViewBinding.confirmLayout.show(true)
            subViewBinding.tvConfirm.show(false)
            subViewBinding.tvLeft.text = builder.leftBtnText
        }
        subViewBinding.tvLeft.setOnClickListener { builder.leftClickListener.invoke() }

        if (builder.rightBtnText.isNullOrEmpty()) {
            subViewBinding.tvConfirm.show(false)
            subViewBinding.tvRight.show(false)
        } else {
            subViewBinding.tvConfirm.text = builder.rightBtnText
            subViewBinding.tvRight.text = builder.rightBtnText
        }
        subViewBinding.tvConfirm.setOnClickListener { builder.rightClickListener.invoke() }
        subViewBinding.tvRight.setOnClickListener { builder.rightClickListener.invoke() }

        if (builder.leftTextColor != 0) {
            subViewBinding.tvLeft.setTextColor(builder.leftTextColor)
        }
    }

    override fun initViews() {

    }

    class TipDialogBuilder {

        var title = ""
        var topImg = 0
        var titleRes = 0
        var content = ""
        var contentRes = 0
        var leftBtnText = ""
        var rightBtnText = ""
        var leftClickListener: () -> Unit = {}
        var rightClickListener: () -> Unit = {}
        var outCancel = true
        var contentInfo: Spanned? = null
        var leftTextColor = 0

        // 内容字体大小
        var contentSize = 0

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

        fun content(content: Spanned): TipDialogBuilder {
            this.contentInfo = content
            return this
        }

        /**
         * 顶部图标
         */
        fun topImg(img: Int): TipDialogBuilder {
            topImg = img
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

        /**
         * 设置内容字体大小
         */
        fun contentTextSize(sizeDp: Int): TipDialogBuilder {
            contentSize = sizeDp
            return this
        }

        fun rightClick(c: () -> Unit = {}, dimiss: Boolean = false): TipDialogBuilder {
            rightClickListener = {
                c.invoke()
                if (dimiss) {
                    tipDialogFragment?.dismiss()
                }
            }
            return this
        }

        fun leftClick(c: () -> Unit = {}, dimiss: Boolean = false): TipDialogBuilder {
            leftClickListener = {
                c.invoke()
                if (dimiss) {
                    tipDialogFragment?.dismiss()
                }
            }
            return this
        }

        var tipDialogFragment: TipDialogFragment? = null

        fun build(): TipDialogFragment? {
            tipDialogFragment = TipDialogFragment(this)
            return tipDialogFragment
        }

        fun show(fragmentManager: FragmentManager) {
            build()?.show(fragmentManager, title)
        }

        /**
         * 蓝色文字
         */
        fun outCancel(bool: Boolean): TipDialogBuilder {
            outCancel = bool
            return this
        }

        /**
         * left 文字颜色
         */
        fun leftTextColor(color: Int): TipDialogBuilder {
            leftTextColor = color
            return this
        }

        private var l: (() -> Unit?)? = null
        private var r: (() -> Unit?)? = null
        private var message = ""
        private var cancelText = "取消"
        private var confirmText = "确定"
        fun show(act: BaseActivity<*>) {
            TipDialogBuilder().content(message, 0)
                .leftBtnText(cancelText)
                .rightBtnText(confirmText)
                .leftClick({
                    if (l != null) {
                        l!!()
                    }
                }, dimiss = true)
                .rightClick({
                    if (r != null) {
                        r!!()
                    }
                }, true)
                .show(act.supportFragmentManager)
        }

        fun message(message: String): TipDialogBuilder {
            this.message = message
            return this
        }

        fun l(y: () -> Unit): TipDialogBuilder {
            this.l = y
            return this
        }

        fun r(n: () -> Unit): TipDialogBuilder {
            this.r = n
            return this
        }

        fun l(text: String, y: () -> Unit): TipDialogBuilder {
            this.cancelText = text
            this.l = y
            return this
        }

        fun r(text: String, n: () -> Unit): TipDialogBuilder {
            this.confirmText = text
            this.r = n
            return this
        }
    }

    override fun initListener() {

    }

}

fun Any.dialog(message: String): TipDialogFragment.TipDialogBuilder {
    val dialog = TipDialogFragment.TipDialogBuilder()
    dialog.message(message)
    return dialog
}

