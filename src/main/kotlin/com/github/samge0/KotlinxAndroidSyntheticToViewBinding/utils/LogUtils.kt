package com.github.samge0.KotlinxAndroidSyntheticToViewBinding.utils

import com.github.samge0.KotlinxAndroidSyntheticToViewBinding.Settings

object LogUtils {

    private const val DEFAULT_TAG = "LogUtils"

    private const val TAG = "{{TAG}}"
    private const val TAG_MSG = "{{MSG}}"
    private const val TEMPLATE = """
======================== {{TAG}} =========================
{{MSG}}
======================== {{TAG}} ========================="""

    /**
     * 拼接日志消息
     *
     * @param tag
     * @param msg
     * @return
     */
    private fun getMsg(tag: String?, msg: String?): String {
        return TEMPLATE.replace(TAG, tag ?: "").replace(TAG_MSG, msg ?: "")
    }

    /**
     * 以级别为 d 的形式输出LOG信息和Throwable
     */
    fun d(msg: String?, tag: String? = DEFAULT_TAG) {
        print(getMsg(tag, msg))
    }

    /**
     * 以级别为 i 的形式输出LOG信息和Throwable
     */
    fun i(msg: String?, tag: String? = DEFAULT_TAG) {
        print(getMsg(tag, msg))
    }

    /**
     * 以级别为 e 的形式输出LOG信息和Throwable
     */
    fun e(msg: String?, tag: String? = DEFAULT_TAG) {
        print(getMsg(tag, msg))
    }

    /**
     * 打印log，判断输出环境
     *
     * @param msg
     */
    private fun print(msg: String?) {
        if (!Settings.DEBUG || msg.isNullOrEmpty()) return
        println(msg)
    }
}