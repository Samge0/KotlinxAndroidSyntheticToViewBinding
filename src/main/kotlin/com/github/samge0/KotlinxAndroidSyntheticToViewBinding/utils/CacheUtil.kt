package com.github.samge0.KotlinxAndroidSyntheticToViewBinding.utils

import com.intellij.ide.util.PropertiesComponent

object CacheUtil {

    /**
     * 持久化的实例-全局级别存储
     */
    private val instance = PropertiesComponent.getInstance()

    /**
     * 缓存类型
     *
     */
    enum class Type {
        ReplacePrefix,         // id替换viewBinding的自定义前缀
        PackageName,           // 包名
    }

    // ======================================= ReplacePrefix start========================================
    fun putCacheReplacePrefix(v: String) = put(Type.ReplacePrefix.name, v)
    fun getCacheReplacePrefix(v: String = "") = get(Type.ReplacePrefix.name, v)
    // ======================================= ReplacePrefix end==========================================

    // ======================================= PackageName start========================================
    fun putCachePackageName(v: String) = put(Type.PackageName.name, v)
    fun getCachePackageName(v: String = "") = get(Type.PackageName.name, v)
    // ======================================= PackageName end==========================================

    /**
     * 存储
     *
     * @param key
     * @param value
     */
    private fun put(key: String, value: String) {
        instance.setValue(key, value)
    }

    /**
     * 读取
     *
     * @param T
     * @param key
     * @param defaultValue
     * @return
     */
    private inline fun <reified T> get(key: String, defaultValue: T): T {
        var result = instance.getValue(key) ?: defaultValue
        result = when(T::class.java.simpleName) {
            "String" -> result.toString()
            "Boolean" -> result.toString().toBoolean()
            "Double" -> result.toString().toDouble()
            "Float" -> result.toString().toFloat()
            "Long" -> result.toString().toLong()
            "Int" -> result.toString().toInt()
            else -> defaultValue
        }
        return result as T
    }
}