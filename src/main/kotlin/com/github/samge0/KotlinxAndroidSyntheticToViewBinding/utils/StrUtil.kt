package com.github.samge0.KotlinxAndroidSyntheticToViewBinding.utils

import java.util.*

object StrUtil {

    /**
     * 裁剪字符串
     *
     * @param value
     * @param maxSize
     * @return
     */
    fun clipStr(value: String?, maxSize: Int): String{
        if (value.isNullOrEmpty()) return ""
        if (value.length > maxSize) return "${value.substring(0,maxSize)}…"
        return value
    }


    /**
     * 生成guid
     *
     * @return
     */
    fun getGuid(): String {
        return UUID.randomUUID().toString()
    }


    /**
     * 检查字段是否为空
     *
     * @param value
     * @return
     */
    fun isEmpty(value: String?): Boolean {
        return value.isNullOrEmpty()
    }

    /**
     * 获取首个不为空的字符
     *
     * @param str1
     * @param str2
     * @return
     */
    fun firstTxt(str1: String?, str2: String?): String {
        return (if (str1.isNullOrEmpty()) str2 else str1) ?: ""
    }
}