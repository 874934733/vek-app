package com.yingyangfly.baselib.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.yingyangfly.baselib.R
import com.yingyangfly.baselib.utils.AppUtil
import com.yingyangfly.baselib.utils.ViewTool

/**
 * Loading
 */
class LoadingDialog constructor(context: Context) : AlertDialog(context, R.style.loading_dialog) {

    var mView: View? = null
    private var fishImage: AppCompatImageView? = null
    private var ovlImage: AppCompatImageView? = null
    var animation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    fun initViews() {
        mView = ViewTool.inflateLayoutPixelsById(context, R.layout.dialog_loading, 1194, 834)
        setContentView(mView!!)
        fishImage = findViewById(R.id.fishImage)
        ovlImage = findViewById(R.id.ovlImage)
        setCanceledOnTouchOutside(false)
        setDialogLayout(window!!)
        init()
    }

    private fun init() {
        Glide.with(context)
            .asGif()
            .load(R.drawable.fish)
            .into(fishImage!!)
    }

    private fun setDialogLayout(dialogWindow: Window) {
        dialogWindow.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialogWindow.setBackgroundDrawableResource(R.color.transparent)
    }

    override fun show() {
        val window = window
        focusNotAle(window!!)
        super.show()
        if (ovlImage != null) {
            animation = AnimationUtils.loadAnimation(AppUtil.getContext(), R.anim.scale_anim)
            ovlImage?.startAnimation(animation)
        }
        hideNavigationBar(window)
        clearFocusNotAle(window)
    }

    /**
     * 隐藏虚拟栏 ，显示的时候再隐藏掉
     *
     * @param window
     */
    private fun hideNavigationBar(window: Window) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.decorView.setOnSystemUiVisibilityChangeListener { visibility: Int ->
            var uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or  //布局位于状态栏下方
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or  //全屏
                    View.SYSTEM_UI_FLAG_FULLSCREEN or  //隐藏导航栏
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            uiOptions = if (Build.VERSION.SDK_INT >= 19) {
                uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            } else {
                uiOptions or View.SYSTEM_UI_FLAG_LOW_PROFILE
            }
            window.decorView.systemUiVisibility = uiOptions
        }
    }

    /**
     * dialog 需要全屏的时候用，和clearFocusNotAle() 成对出现
     * 在show 前调用  focusNotAle   show后调用clearFocusNotAle
     *
     * @param window
     */
    private fun focusNotAle(window: Window) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )
    }

    /**
     * dialog 需要全屏的时候用，focusNotAle() 成对出现
     * 在show 前调用  focusNotAle   show后调用clearFocusNotAle
     *
     * @param window
     */
    private fun clearFocusNotAle(window: Window) {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }

    override fun dismiss() {
        if (ovlImage != null) {
            ovlImage?.clearAnimation()
        }
        super.dismiss()
    }

}