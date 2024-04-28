package com.yingyangfly.baselib.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.inputmethod.InputMethodManager
import com.blankj.utilcode.util.StringUtils
import java.io.File

/**
 * 防止双击
 * @author xiaoliu
 */
object CommonUtils {

    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private const val SHORT_MIN_CLICK_DELAY_TIME = 1000
    private const val MIN_CLICK_DELAY_TIME = 2000
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


    fun string2Bitmap(string: String?): Bitmap? {
        //将字符串转换成Bitmap类型
        var bitmap: Bitmap? = null
        try {
            val bitmapArray: ByteArray
            bitmapArray = Base64.decode(string, Base64.DEFAULT)
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.size)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return bitmap
    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    fun hideSoftKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val inputMethodManager =
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

    /**
     * 抽取URL
     *
     * @param rawInfo rawInfo
     * @return url
     */
    fun extractUrl(rawInfo: String): String {
        if (StringUtils.isEmpty(rawInfo)) {
            return ""
        }
        for (string in rawInfo.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
            if (string.startsWith("http")) {
                return string
            }
        }
        return ""
    }
}