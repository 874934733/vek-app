package com.yingyangfly.baselib.bean.def

import android.Manifest
import com.hjq.permissions.Permission
import java.util.Arrays

object PermissionList {

    val storagePermission = arrayOf(
        Permission.WRITE_EXTERNAL_STORAGE,
        Permission.READ_EXTERNAL_STORAGE,
    )

    // 获取录音权限
    val readPhoneStatePermission = Arrays.asList(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_PHONE_STATE
    ).toTypedArray()
}