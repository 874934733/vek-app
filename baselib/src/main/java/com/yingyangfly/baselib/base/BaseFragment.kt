package com.yingyangfly.baselib.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.yingyangfly.baselib.R
import com.yingyangfly.baselib.databinding.ActivityBaseBinding
import com.yingyangfly.baselib.dialog.LoadingDialog
import com.yingyangfly.baselib.ext.getDbClass
import com.yingyangfly.baselib.ext.initBar
import com.yingyangfly.baselib.utils.ResUtil
import com.yingyangfly.baselib.utils.ViewTool
import gorden.rxbus2.RxBus

/**
 * @author: gold
 * @time: 2021/11/19 上午10:46
 * @description: 封装DataBinding基类，减少样板代码 re = reflect  反射  该类用到了反射
 */
abstract class BaseFragment<DB : ViewDataBinding> : Fragment(), OnRefreshLoadMoreListener {
    /**
     * 布局
     */
    val binding: DB by lazy(LazyThreadSafetyMode.NONE) {
        getDbClass(this)
    }


    val bindingBase: ActivityBaseBinding by lazy {
        ActivityBaseBinding.inflate(layoutInflater)
    }

    lateinit var mContext: Context

    /**
     * Loading
     */
    val netLoading: LoadingDialog by lazy {
        LoadingDialog(mContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mContext = requireActivity()
        RxBus.get().register(this)
        ARouter.getInstance().inject(this)
        initBar(false)
        initSmartRefresh()
        val params: ViewGroup.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT, 1.0f
        )
        binding.root.layoutParams = params
        bindingBase.llytContent.addView(ViewTool.inflateFragmentPixels(activity, binding.root,1194, 834))
        initMVVM()
        initViews()
        initListener()
        initData()
        return bindingBase.root
    }

    protected inline fun binding(block: DB.() -> Unit): DB {
        return binding.apply(block)
    }

    /**
     * 初始化View
     */
    abstract fun initViews()

    /**
     * 初始化监听
     */
    abstract fun initListener()

    /**
     * 初始化数据源 or 请求
     */
    abstract fun initData()

    /**
     * 初始化数据源 or 请求
     */
    open fun initMVVM() {}

    /**
     * 初始化下拉刷新布局
     */
    fun initSmartRefresh() {
        // 是否启用下拉刷新功能
        bindingBase.smartRefreshLayout.setEnableRefresh(false)
        // 是否启用上拉加载功能
        bindingBase.smartRefreshLayout.setEnableLoadMore(false)
        // 关闭越界回弹功能
        bindingBase.smartRefreshLayout.setEnableOverScrollBounce(false)
        // 禁止越界拖动
        bindingBase.smartRefreshLayout.setEnableOverScrollDrag(false)
        bindingBase.smartRefreshLayout.setOnRefreshLoadMoreListener(this)
        //设置刷新头和加载头的背景色
        bindingBase.smartRefreshLayout.refreshHeader?.view?.setBackgroundColor(ResUtil.getColor(R.color.color_F5F5F5))
        bindingBase.smartRefreshLayout.refreshFooter?.view?.setBackgroundColor(ResUtil.getColor(R.color.color_F5F5F5))
    }

    /**
     * 是否需要下拉刷新加载更多布局 默认不需要
     */
    open fun enableRefreshLoadMore() = false

    /**
     * 下拉刷新
     */
    override fun onRefresh(refreshLayout: RefreshLayout) {}

    /**
     * 加载更多
     */
    override fun onLoadMore(refreshLayout: RefreshLayout) {}

    /**
     * 是否自动刷新
     */
    fun autoRefresh() {
        bindingBase.smartRefreshLayout.autoRefresh()
    }

    /**
     * 是否下拉刷新，默认不能下拉刷新
     */
    fun enableRefresh(refresh: Boolean) {
        bindingBase.smartRefreshLayout.setEnableRefresh(refresh)
    }

    /**
     * 是否加载更多，默认不加载更多
     */
    fun enableLoadMore(loadMore: Boolean) {
        bindingBase.smartRefreshLayout.setEnableLoadMore(loadMore)
    }

    /**
     * 结束下拉刷新
     */
    fun finishRefresh() {
        bindingBase.smartRefreshLayout.finishRefresh()
    }

    /**
     * 结束加载更多
     */
    fun finishLoadMore() {
        bindingBase.smartRefreshLayout.finishLoadMore()
    }

    /**
     * 结束加载更多， 且没有更多数据
     */
    fun finishLoadMoreWithNoMoreData() {
        bindingBase.smartRefreshLayout.finishLoadMoreWithNoMoreData()
    }

    /**
     * 显示Loading
     */
    fun showLoading() {
        netLoading.show()
    }

    /**
     * 关闭Loading
     */
    fun dimissLoading() {
        netLoading.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unRegister(this)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }
}
