package com.yingyangfly.baselib.utils

import com.blankj.utilcode.util.RegexUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author wangsai
 * @date 2022/9/14 20:46
 * @description 字符串相关的工具类
 */
object StringUtil {

    /**
     * 验证手机号
     */
    fun isMobileExact(input: CharSequence) = RegexUtils.isMobileExact(input)

    /**
     * 手机号脱敏
     */
    fun phoneBlur(tel: String): String {
        if (!isMobileExact(tel)) return tel
        val phoneBlurRegex = "(\\d{3})\\d{4}(\\d{4})".toRegex()

        /** * 手机号脱敏替换正则 */
        val regex = "$1****$2"
        return tel.replace(phoneBlurRegex, regex)

    }

    /**
     * 验证身份证
     */
    fun isIDCard(input: CharSequence) =
        RegexUtils.isIDCard15(input) || RegexUtils.isIDCard15(input) || RegexUtils.isIDCard18Exact(
            input
        )

    // 传入年和月得出当月的天数
    fun getMonth(m: Int, y: Int): Int {
        return when (m) {
            2 -> if (isRunYear(y)) 29 else 28
            4, 6, 9, 11 -> 30
            else -> 31
        }
    }

    // 判断是否为闰年
    fun isRunYear(y: Int): Boolean {
        return y % 4 == 0 && y % 100 != 0 || y % 400 == 0
    }

    // 格式化时间，设置时间很方便，也比较简单，学的很快
    fun getFormatTime(p: String, t: Date?): String {
        return SimpleDateFormat(p, Locale.CHINESE).format(t)
    }

}