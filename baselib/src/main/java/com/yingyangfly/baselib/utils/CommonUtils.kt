package com.yingyangfly.baselib.utils

import java.io.File

/**
 * 防止双击
 * @author xiaoliu
 */
object CommonUtils {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private const val SHORT_MIN_CLICK_DELAY_TIME = 1000
    private const val MIN_CLICK_DELAY_TIME = 1500
    private var lastClickTime: Long = 0

    val isFastClick: Boolean
        get() {
            var flag = false
            val curClickTime = System.currentTimeMillis()
            if (curClickTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                flag = true
            }
            lastClickTime = curClickTime
            return flag
        }
    val isShortFastClick: Boolean
        get() {
            var flag = false
            val curClickTime = System.currentTimeMillis()
            if (curClickTime - lastClickTime > SHORT_MIN_CLICK_DELAY_TIME) {
                flag = true
            }
            lastClickTime = curClickTime
            return flag
        }

    /**
     * 删除目录及目录下的文件
     */
    fun deleteDirectory(dir: String): Boolean {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        var dir = dir
        if (!dir.endsWith(File.separator)) dir += File.separator
        val dirFile = File(dir)
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory) {
            return false
        }
        var flag = true
        // 删除文件夹中的所有文件包括子目录
        val files: Array<File> = dirFile.listFiles()
        for (i in files.indices) {
            // 删除子文件
            if (files[i].isFile) {
                flag = deleteFile(files[i].absolutePath)
                if (!flag) break
            } else if (files[i].isDirectory) {
                flag = deleteDirectory(
                    files[i].absolutePath
                )
                if (!flag) break
            }
        }
        if (!flag) {
            return false
        }
        // 删除当前目录
        return dirFile.delete()
    }

    /**
     * 删除单个文件
     */
    private fun deleteFile(fileName: String): Boolean {
        val file = File(fileName)
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        return if (file.exists() && file.isFile) {
            file.delete()
        } else {
            false
        }
    }
}