package com.yingyangfly.baselib.bean.def

import com.hjq.permissions.Permission

object PermissionList {

    var liveDetails = arrayOf(
        Permission.WRITE_EXTERNAL_STORAGE,
        Permission.CAMERA,
        Permission.RECORD_AUDIO,
        Permission.READ_EXTERNAL_STORAGE
    )

    var healthConsultation = arrayOf(
        Permission.WRITE_EXTERNAL_STORAGE,
        Permission.CAMERA,
        Permission.RECORD_AUDIO,
        Permission.READ_EXTERNAL_STORAGE
    )

    val cameraPermission = arrayOf(
        Permission.WRITE_EXTERNAL_STORAGE,
        Permission.READ_EXTERNAL_STORAGE,
        Permission.CAMERA
    )
}