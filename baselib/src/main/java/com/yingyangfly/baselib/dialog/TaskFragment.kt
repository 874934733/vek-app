package com.yingyangfly.baselib.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.gyf.immersionbar.BarHide
import com.gyf.immersionbar.ktx.immersionBar
import com.yingyangfly.baselib.R
import com.yingyangfly.baselib.ext.setOnSingleClickListener
import com.yingyangfly.baselib.utils.LiveEventBusUtil
import com.yingyangfly.baselib.utils.RxBusCodes
import com.yingyangfly.baselib.utils.User
import com.yingyangfly.baselib.utils.ViewTool

/**
 * 任务提醒
 */
class TaskFragment : DialogFragment(), View.OnTouchListener {

    private var taskDesn = ""
    private var fishImage: AppCompatImageView? = null
    private var tvContent: AppCompatTextView? = null
    private var confirmBtn: AppCompatButton? = null
    private var id = ""
    private var content: Context? = null

    var onDialogClickListener: ((bean: String) -> Unit)? = null

    fun setTaskDesn(taskDesn: String, id: String) {
        this.taskDesn = taskDesn
        this.id = id
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
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
            activity, R.layout.fragment_task, container, 1194, 834
        )
        content = activity
        findId(rootView)
        init()
        return rootView
    }

    private fun findId(rootView: View) {
        fishImage = rootView.findViewById(R.id.fishImage)
        tvContent = rootView.findViewById(R.id.tvContent)
        confirmBtn = rootView.findViewById(R.id.confirmBtn)
        confirmBtn?.setOnSingleClickListener {
            User.saveMessageBean("")
            onDialogClickListener?.invoke(id)
            dismiss()
        }
    }

    private fun init() {
        Glide.with(this)
            .asGif()
            .load(R.drawable.fish)
            .into(fishImage!!)
        tvContent?.text = taskDesn
        speak(taskDesn)
    }

    /**
     * 播放语音合成内容
     */
    private fun speak(taskDesn: String) {
        LiveEventBusUtil.send(RxBusCodes.SPEECHSYNTHESIS, taskDesn)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (isCancelable && dialog?.isShowing == true) {
            dismiss()
            return true
        }
        return false
    }

    override fun dismiss() {
        LiveEventBusUtil.send(RxBusCodes.STOPVOICE, "")
        super.dismiss()
    }
}