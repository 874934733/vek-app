package com.yingyangfly.baselib.utils

import android.content.Context
import android.text.TextUtils
import com.tencent.bugly.crashreport.CrashReport
import com.yingyangfly.baselib.BaseApplication
import java.text.SimpleDateFormat
import java.util.*

object User {

    val context: Context
        get() = BaseApplication.appContext

    fun saveMobile(mobile: String) {
        Preferences.put(CommonParam.MOBILE, mobile)
    }

    fun getMobile(): String {
        return if (Preferences.getString(CommonParam.MOBILE).isNullOrEmpty()) {
            ""
        } else {
            Preferences.getString(CommonParam.MOBILE)!!
        }
    }

    fun saveIdCard(idCard: String) {
        Preferences.put(CommonParam.IDCARD, idCard)
    }

    fun getIdCard(): String {
        return if (Preferences.getString(CommonParam.IDCARD).isNullOrEmpty()) {
            ""
        } else {
            Preferences.getString(CommonParam.IDCARD)!!
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
     * 保存医生头像
     */
    fun saveDoctorAvatar(avatar: String) {
        Preferences.put(CommonParam.DOCTORAVATAR, avatar)
    }

    fun getDoctorAvatar(): String {
        return if (TextUtils.isEmpty(Preferences.getString(CommonParam.DOCTORAVATAR))) {
            ""
        } else {
            Preferences.getString(CommonParam.DOCTORAVATAR)!!
        }
    }

    /**
     * 保存患者头像
     */
    fun saveAvatar(avatar: String) {
        Preferences.put(CommonParam.AVATAR, avatar)
    }

    fun getAvatar(): String {
        return if (TextUtils.isEmpty(Preferences.getString(CommonParam.AVATAR))) {
            ""
        } else {
            Preferences.getString(CommonParam.AVATAR)!!
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

    /**
     * 是否展示过任务弹窗
     */
    fun saveTaskDialogStatus(time: String) {
        Preferences.put(CommonParam.TASKDIALOG, time)
    }

    fun getTaskDialogStatus(): String {
        return if (TextUtils.isEmpty(Preferences.getString(CommonParam.TASKDIALOG))) {
            ""
        } else {
            Preferences.getString(CommonParam.TASKDIALOG)!!
        }
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
     * 是否第一次登陆 0第一次登陆
     */
    fun saveFirstLogin(firstLogin: String) {
        Preferences.put(CommonParam.FIRSTLOGIN, firstLogin)
        CrashReport.setUserId(firstLogin)
    }

    fun getFirstLogin(): String {
        return if (TextUtils.isEmpty(Preferences.getString(CommonParam.FIRSTLOGIN))) {
            ""
        } else {
            Preferences.getString(CommonParam.FIRSTLOGIN)!!
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

    /**
     * 音效保存日期
     */
    fun saveSaveGameSoundDate(saveGameSoundDate: String) {
        Preferences.put(CommonParam.SAVEGAMESOUNDDATE, saveGameSoundDate)
    }

    fun getSaveGameSoundDate(): String {
        return if (TextUtils.isEmpty(Preferences.getString(CommonParam.SAVEGAMESOUNDDATE))) {
            ""
        } else {
            Preferences.getString(CommonParam.SAVEGAMESOUNDDATE)!!
        }
    }

    fun isUpdateSaveGameSound(): Boolean {
        return if (TextUtils.isEmpty(getSaveGameSoundDate())) {
            true
        } else {
            TextUtils.equals(getSaveGameSoundDate(), getNowDay()).not()
        }
    }

    /**
     * 保存理疗报告地址
     */
    fun saveTrainReportUrl(trainReportUrl: String) {
        if (TextUtils.isEmpty(trainReportUrl).not()) {
            Preferences.put(CommonParam.TRAINREPORT_URL, trainReportUrl)
        }
    }

    fun getTrainReportUrl(): String {
        return if (TextUtils.isEmpty(Preferences.getString(CommonParam.TRAINREPORT_URL))) {
            ""
        } else {
            Preferences.getString(CommonParam.TRAINREPORT_URL)!!
        }
    }

    /**
     * 保存理疗报告IM消息内容
     */
    fun saveMessageBean(messageBean: String) {
        if (TextUtils.isEmpty(messageBean).not()) {
            Preferences.put(CommonParam.MESSAGEBEAN, messageBean)
        } else {
            Preferences.put(CommonParam.MESSAGEBEAN, "")
        }
    }

    fun getMessageBean(): String {
        return if (TextUtils.isEmpty(Preferences.getString(CommonParam.MESSAGEBEAN))) {
            ""
        } else {
            Preferences.getString(CommonParam.MESSAGEBEAN)!!
        }
    }
}