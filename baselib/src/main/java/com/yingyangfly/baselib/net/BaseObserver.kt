package com.yingyangfly.baselib.net

import android.util.Log
import com.tencent.bugly.crashreport.CrashReport
import com.yingyangfly.baselib.router.RouterUrlCommon
import com.yingyangfly.baselib.utils.JumpUtil
import com.yingyangfly.baselib.utils.User
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import java.net.SocketTimeoutException

/**
 * Author: Austin
 * Date: 2018/10/9
 * Description:
 */
abstract class BaseObserver<T> : Observer<XBaseEntity<T>> {

    val TAG = "BaseObserver"
    private val SUCCESS_CODE = 200
    private val TOKENLOSEEFFICACY = 403

    override fun onSubscribe(@NonNull d: Disposable) {
        logd("onSubscribe")
    }

    override fun onNext(@NonNull tBaseEntity: XBaseEntity<T>) {
        logd("onNext")
        if (SUCCESS_CODE == tBaseEntity.code) {
            val t = tBaseEntity.data
            try {
                onSuccess(t)
            } catch (e: Exception) {
//              异常上报
                CrashReport.postCatchedException(e)
            }
        } else if (TOKENLOSEEFFICACY == tBaseEntity.code) {
            User.saveMobile("")
            User.saveIdCard("")
            User.saveUserSex("")
            User.saveUserAge("")
            User.saveDoctorAvatar("")
            User.saveAvatar("")
            User.saveName("")
            User.saveUserId("")
            User.savePadNo("")
            User.saveOrgCode("")
            JumpUtil.jumpActivity(RouterUrlCommon.login)
        } else {
            loge("onNext--Failure--code:" + tBaseEntity.code + "--Message:" + tBaseEntity.message)
            onFail(tBaseEntity.message)
            onFail(tBaseEntity)
        }
    }

    override fun onError(@NonNull e: Throwable) {
        loge("TAG--onError--" + e.toString() + "\n" + e.message)
        if (e is SocketTimeoutException || e is java.net.UnknownHostException) {
            loge("错误类型!!是超时")
            onFail("网络超时请重试或查看网络")
        } else {
            onFail(e.toString())
        }
    }

    override fun onComplete() {
        logd("TAG--onComplete")
    }

    abstract fun onSuccess(t: T?)

    open fun onFail(msg: String) {
//        ToastUtil.show(msg)
    }

    open fun onFail(data: XBaseEntity<T>) {
    }

    fun logd(str: String) {
        Log.d(TAG, str)
    }

    fun loge(str: String) {
        Log.e(TAG, str)
    }
}
