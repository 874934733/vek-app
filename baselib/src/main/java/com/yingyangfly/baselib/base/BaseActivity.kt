package com.yingyangfly.baselib.base

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
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
import com.yingyangfly.baselib.utils.ActivityManagers
import com.yingyangfly.baselib.utils.ResUtil
import com.yingyangfly.baselib.utils.ViewTool
import gorden.rxbus2.RxBus

/**
 * activity基类
 */
abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity(), OnRefreshLoadMoreListener {

    /**
     * 确定初始化将总是发生在单个线程，那么你可以使用 LazyThreadSafetyMode.NONE模式， 它不会有任何线程安全的保证和相关的开销。
     * 如果初始化委托的同步锁不是必需的，这样多个线程可以同时执行，那么将 LazyThreadSafetyMode.PUBLICATION 作为参数传递给 lazy() 函数
     */
    val binding: DB by lazy(LazyThreadSafetyMode.NONE) {
        getDbClass(this)
    }

    val bindingBase: ActivityBaseBinding by lazy {
        ActivityBaseBinding.inflate(layoutInflater)
    }

    lateinit var mContext: Context

    /**
     * 是否执行了下拉刷新或上拉加载
     */
    private var isRefreshOrLoadMore = false

    /**
     * 加载loading
     */
    val netLoading: LoadingDialog by lazy {
        LoadingDialog(mContext)
    }

    private var logoutDialog: AlertDialog? = null

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxBus.get().register(this)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE // 竖屏
        mContext = this
        initWindow()
        // 默认不全屏，底部导航栏透明
        initBar(false)
        ARouter.getInstance().inject(this)
        ActivityManagers.instance.addActivity(this)
        initSmartRefresh()
        val params: ViewGroup.LayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f
        )
        binding.root.layoutParams = params
        bindingBase.llytContent.addView(binding.root)
        setContentView(ViewTool.inflateLayoutPixels(this, bindingBase.root, 1194, 834))
        initMVVM()
        initViews()
        initListener()
        initData()
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
     * 需要设置window参数可重写此方法
     */
    open fun initWindow() {}

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
    override fun onRefresh(refreshLayout: RefreshLayout) {
        isRefreshOrLoadMore = true
    }

    /**
     * 加载更多
     */
    override fun onLoadMore(refreshLayout: RefreshLayout) {
        isRefreshOrLoadMore = true
    }

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
        bindingBase.smartRefreshLayout.setEnableFooterFollowWhenNoMoreData(true)
    }

    /**
     * 结束下拉刷新
     */
    fun finishRefresh() {
        isRefreshOrLoadMore = false
        bindingBase.smartRefreshLayout.finishRefresh()
    }

    /**
     * 结束加载更多
     */
    fun finishLoadMore() {
        isRefreshOrLoadMore = false
        bindingBase.smartRefreshLayout.finishLoadMore()
    }

    /**
     * 结束加载更多，且没有更多数据
     */
    fun finishLoadMoreWithNoMoreData() {
        isRefreshOrLoadMore = false
        bindingBase.smartRefreshLayout.finishLoadMoreWithNoMoreData()
    }

    /**
     * 设置没有更多数据
     */
    fun resetNoMoreData() {
        bindingBase.smartRefreshLayout.resetNoMoreData()
    }

    /**
     * 显示Loading
     */
    fun showLoading() {
        if (isRefreshOrLoadMore) return
        netLoading.show()
    }

    /**
     * 取消Loading
     */
    fun dismissLoading() {
        netLoading.dismiss()
    }

    /**
     * 显示键盘
     */
    fun showInput(et: EditText) {
        et.requestFocus()
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
    }

    /**
     * 关闭软键盘
     */
    fun closeKeyBord(mEditText: EditText, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        RxBus.get().unRegister(this)
        ActivityManagers.instance.removeActivity(this)
    }

    var showFragment: Fragment? = null
    fun showFragment(viewId: Int, f: Fragment) {
        try {
            val ft = supportFragmentManager.beginTransaction()
            if (showFragment != null) ft.hide(showFragment!!)
            if (f.isAdded) {
                ft.show(f)
            } else {
                ft.add(viewId, f)
            }
            showFragment = f
            ft.commitAllowingStateLoss()
            supportFragmentManager.executePendingTransactions()// 立即执行
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true
        }
        return false
    }
}