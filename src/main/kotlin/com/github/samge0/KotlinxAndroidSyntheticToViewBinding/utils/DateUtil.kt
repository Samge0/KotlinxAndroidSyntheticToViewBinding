package com.github.samge0.KotlinxAndroidSyntheticToViewBinding.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    /**
     * 获取当前日期
     *
     * @return
     */
    fun getCurrDate(format: String="yyyy/MM/dd HH:mm:ss"): String {
        return SimpleDateFormat(format).format(Date())
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    fun getCurrYear(): String {
        val c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00")) //获取东八区时间
        return c[Calendar.YEAR].toString()
    }
}