package com.yingyangfly.baselib.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import com.yingyangfly.baselib.bean.StatusViewType
import com.yingyangfly.baselib.ext.loge
import com.yingyangfly.baselib.ext.toast
import com.yingyangfly.baselib.net.BaseResp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.net.UnknownServiceException
import javax.net.ssl.SSLException

/**
 * @author: gold
 * @time: 2022/3/23 下午7:45
 * @description: ViewModel基础类
 */
abstract class BaseViewModel : ViewModel() {
    val loadingStateFlow = MutableStateFlow(0)
    fun <T> launchFlow(
        showLoading: Boolean = false,
        block: suspend () -> BaseResp<T>
    ): Flow<BaseResp<T>> {

        return flow {
            emit(block())
        }.onStart {
            loadingStateFlow.value = StatusViewType.DEFAULT
            if (showLoading) {
                loadingStateFlow.value = StatusViewType.LOADING
            }
        }.onCompletion {
            if (showLoading) {
                loadingStateFlow.value = StatusViewType.DISMISS
            }
        }.flowOn(Dispatchers.IO).catch { e ->
            apiException(e).toast()
            loadingStateFlow.value = StatusViewType.ERROR
//            e.printStackTrace()
            "接口：catch:${e.message}".loge()
        }
    }

    /**
     * 运行在UI线程的协程
     */
    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        try {
            // 协程超时取消
            withTimeout(30 * 1000) {
                block()
            }
        } catch (e: Exception) {
            // 此处接收到BaseRepository里的request抛出的异常
            // 根据业务逻辑自行处理代码...
        }
    }

    /**
     * 运行在UI线程的协程
     */
    fun <T> Flow<BaseResp<T>>.runUI(
        success: ((success: T?) -> Unit)?,
        fail: ((msg: String) -> Unit)?
    ) = viewModelScope.launch {
        try {
            // 协程超时取消
            withTimeout(30 * 1000) {
                this@runUI.collect {
                    it.y {
                        success?.invoke(it)
                    }
                    it.n {
                        fail?.invoke(it)
                    }
                }
            }
        } catch (e: Exception) {
            // 此处接收到BaseRepository里的request抛出的异常
            // 根据业务逻辑自行处理代码...
        }
    }

    /**
     * 异常处理
     */
    fun apiException(e: Throwable): String {
        e.printStackTrace()
        var errorMsg = ""
        when (e) {
            is UnknownHostException -> errorMsg = "网络异常，请检查您的网络设置"
            is SocketTimeoutException, //okhttp全局设置超时
            is TimeoutCancellationException -> errorMsg = "网络超时,请稍后再试" // 协程超时
            is HttpException -> {
                when {
                    e.code() == 303 -> errorMsg = "请使用GET请求方式"
                    e.code() == 401 -> errorMsg = "认证失败"
                    e.code() == 403 -> errorMsg = "访问被拒绝"
                    e.code() == 404 -> errorMsg = "找不到路径"
                    e.code() == 408 -> errorMsg = "请求超时"
                    e.code() == 413 -> errorMsg = "请求体过大"
                    e.code() == 415 -> errorMsg = "类型不正确"
                    e.code() == 500 -> errorMsg = "服务器错误"
                    e.code() == 501 -> errorMsg = "请求还没有被发现"
                    e.code() == 502 -> errorMsg = "网关错误"
                    e.code() == 503 -> errorMsg = "服务暂时不可用" // 服务器正好在更新代码重启。
                    e.code().toString().startsWith("4") -> errorMsg = "客户端异常"
                    e.code().toString().startsWith("5") -> errorMsg = "服务器异常"
                }
            }
            is ConnectException -> errorMsg = "网络不给力，请稍候重试！"
            is SSLException -> errorMsg = "证书异常"
            is JsonSyntaxException -> errorMsg = "数据解析失败,请检查数据是否正确"
            is JSONException -> errorMsg = "数据解析异常，非法JSON"
            is MalformedJsonException -> errorMsg = "数据解析异常，非法JSON"
            is UnknownServiceException -> errorMsg = "未知服务器路径"
            is Exception -> errorMsg = "程序异常" + e.javaClass.name
            else -> errorMsg = e.message ?: "请求失败，请稍后再试"
        }
        return errorMsg
    }
}
