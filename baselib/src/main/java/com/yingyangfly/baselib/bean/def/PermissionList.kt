package com.yingyangfly.baselib.bean.def

import com.hjq.permissions.Permission

object PermissionList {

    val cameraPermission = arrayOf(
        Permission.WRITE_EXTERNAL_STORAGE,
        Permission.READ_EXTERNAL_STORAGE,
    )
}