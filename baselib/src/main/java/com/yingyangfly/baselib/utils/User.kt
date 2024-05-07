package com.yingyangfly.baselib.utils

import android.content.Context
import android.text.TextUtils
import com.tencent.bugly.crashreport.CrashReport
import com.yingyangfly.baselib.BaseApplication
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object User {

    val context: Context
        get() = BaseApplication.appContext

    fun saveAndroidId(androidId: String) {
        Preferences.put(CommonParam.androidId, androidId)
    }

    fun getAndroidId(): String {
        return if (Preferences.getString(CommonParam.androidId).isNullOrEmpty()) {
            ""
        } else {
            Preferences.getString(CommonParam.androidId)!!
        }
    }

    /**
     * 保存用户登陆手机号
     */
    fun savePhone(phone: String) {
        Preferences.put(CommonParam.PHONE, phone)
    }

    fun getPhone(): String {
        return if (Preferences.getString(CommonParam.PHONE).isNullOrEmpty()) {
            ""
        } else {
            Preferences.getString(CommonParam.PHONE)!!
        }
    }

    /**
     * 保存用户token
     */
    fun saveToken(token: String) {
        Preferences.put(CommonParam.TOKEN, token)
        CrashReport.setUserId(token)
    }

    fun getToken(): String {
        return if (Preferences.getString(CommonParam.TOKEN).isNullOrEmpty()) {
            ""
        } else {
            Preferences.getString(CommonParam.TOKEN)!!
        }
    }

    /**
     * 保存患者性别
     */
    fun saveUserSex(sex: String) {
        Preferences.put(CommonParam.USERSEX, sex)
    }

    fun getUserSex(): String {
        return if (Preferences.getString(CommonParam.USERSEX).isNullOrEmpty()) {
            ""
        } else {
            Preferences.getString(CommonParam.USERSEX)!!
        }
    }

    /**
     * 保存患者年龄
     */
    fun saveUserAge(age: String) {
        Preferences.put(CommonParam.USERAGE, age)
    }

    fun getUserAge(): String {
        return if (Preferences.getString(CommonParam.USERAGE).isNullOrEmpty()) {
            ""
        } else {
            Preferences.getString(CommonParam.USERAGE)!!
        }
    }

    /**
     * 保存患者姓名
     */
    fun saveName(name: String) {
        Preferences.put(CommonParam.NAME, name)
    }

    fun getName(): String {
        return if (TextUtils.isEmpty(Preferences.getString(CommonParam.NAME))) {
            ""
        } else {
            Preferences.getString(CommonParam.NAME)!!
        }
    }

    /**
     * 保存患者ID
     */
    fun saveUserId(id: String) {
        Preferences.put(CommonParam.ID, id)
    }

    fun getUserId(): String {
        return if (TextUtils.isEmpty(Preferences.getString(CommonParam.ID))) {
            ""
        } else {
            Preferences.getString(CommonParam.ID)!!
        }
    }

    /**
     * 获取当前日期
     */
    fun getNowDay(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

    fun saveOrgCode(orgCode: String) {
        Preferences.put(CommonParam.ORGCODE, orgCode)
        CrashReport.setUserId(orgCode)
    }

    fun getOrgCode(): String {
        return if (TextUtils.isEmpty(Preferences.getString(CommonParam.ORGCODE))) {
            ""
        } else {
            Preferences.getString(CommonParam.ORGCODE)!!
        }
    }

    /**
     * 保存平板编号
     */
    fun savePadNo(padNo: String) {
        Preferences.put(CommonParam.PADNO, padNo)
    }

    fun getPadNo(): String {
        return if (TextUtils.isEmpty(Preferences.getString(CommonParam.PADNO))) {
            ""
        } else {
            Preferences.getString(CommonParam.PADNO)!!
        }
    }
}