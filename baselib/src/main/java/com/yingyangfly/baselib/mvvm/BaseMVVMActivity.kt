package com.yingyangfly.baselib.mvvm

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.yingyangfly.baselib.base.BaseActivity
import com.yingyangfly.baselib.bean.StatusViewType
import com.yingyangfly.baselib.ext.logi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author: gold
 * @time: 2021/12/8 下午5:31
 * @description: MVVM基类封装
 */
private const val TAG = "BaseMVVMActivity"

abstract class BaseMVVMActivity<DB : ViewDataBinding, VM : BaseViewModel> : BaseActivity<DB>() {
    val viewModel: VM by lazy {
        ViewModelProvider(this).get(getVmClass(this))
    }

    fun launch(block: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch {
            block()
        }
    }

    override fun initMVVM() {
        super.initMVVM()
        launch {
            viewModel.loadingStateFlow.collect {
                when (it) {
                    StatusViewType.DEFAULT -> {
                        "$TAG -->MVVM:DEFAULT".logi()
                    }
                    StatusViewType.LOADING -> {
                        "$TAG -->MVVM:LOADING".logi()
                        showLoading()
                    }
                    StatusViewType.DISMISS -> {
                        "$TAG -->MVVM:DISMISS".logi()
                        dismissLoading()
                        finishLoadMore()
                        finishRefresh()
                    }
                    StatusViewType.EMPTY -> {
                        "$TAG -->MVVM:EMPTY".logi()
                    }
                    StatusViewType.ERROR -> {
                        "$TAG -->MVVM:ERROR".logi()
                        onErrorView()
                    }
                    else -> {
                    }
                }
            }
        }
    }

    /**
     * 加载错误失败要显示的布局
     */
    open fun onErrorView() {
        dismissLoading()
        finishLoadMore()
        finishRefresh()
    }
}
