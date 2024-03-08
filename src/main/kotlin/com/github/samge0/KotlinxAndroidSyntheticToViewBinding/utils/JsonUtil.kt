package com.github.samge0.KotlinxAndroidSyntheticToViewBinding.utils

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONException
import com.alibaba.fastjson.JSONObject

object JsonUtil {

    private const val TAG = "JsonUtil"

    /**
     * 判断是否正确的json格式，如果不是的话，在fastjson中解析会报错
     * @param v String
     * @return Boolean
     */
    fun isJson(v: String?): Boolean {
        if (v.isNullOrEmpty() || v == "{}") return false
        val jsonNew = v.trim()
        if (jsonNew.startsWith("{") && jsonNew.endsWith("}")) return true
//        if (jsonNew.startsWith("[") && jsonNew.endsWith("]")) return true
        return false
    }

    /**
     * 将json解析为bean对象，并在解析前校验了json格式
     * @param text String?
     * @param clazz Class<T>?
     * @return T?
     */
    fun <T> parseObject(text: String?, clazz: Class<T>?): T? {
        if(!isJson(text)) return null
        return try {
            JSON.parseObject(text, clazz)
        } catch (e: JSONException) {
            LogUtils.e(TAG,"parseObject->${clazz?.name} JSONException $e")
            null
        } catch (e: Exception) {
            LogUtils.e(TAG,"parseObject->${clazz?.name} Exception $e")
            null
        }
    }

    /**
     * 将json解析为bean列表，并在解析前校验了json格式
     * @param text String?
     * @param clazz Class<T>?
     * @return List<T>
     */
    fun <T> parseArray(text: String?, clazz: Class<T>?): MutableList<T> {
        if(!isJson(text)) return ArrayList()
        return try {
            JSON.parseArray(text, clazz)
        } catch (e: JSONException) {
            LogUtils.e(TAG,"parseArray JSONException $e")
            ArrayList()
        } catch (e: Exception) {
            LogUtils.e(TAG,"parseArray Exception $e")
            ArrayList()
        }
    }

    /**
     * 将对象转为json字符串
     * @param v Any?
     * @return String
     */
    fun toJSONString(v: Any?): String {
        if (v == null) return ""
        return try {
            JSON.toJSONString(v)
        } catch (e: JSONException) {
            LogUtils.e(TAG,"toJSONString JSONException $e")
            ""
        } catch (e: Exception) {
            LogUtils.e(TAG,"toJSONString Exception $e")
            ""
        }
    }

    fun parseObject(text: String?): JSONObject? {
        if(!isJson(text)) return null
        return try {
            JSON.parseObject(text)
        } catch (e: JSONException) {
            LogUtils.e(TAG,"parseObject->JSONObject JSONException $e")
            null
        } catch (e: Exception) {
            LogUtils.e(TAG,"parseObject->JSONObject Exception $e")
            null
        }
    }

    /**
     * 美化json字符串
     * @param v Any?
     * @param beautify: Boolean 是否美化
     * @return String
     */
    fun beautifyJson(v: Any?, beautify: Boolean): String {
        if (v == null) return ""
        return try {
            when (v) {
                is String -> JSON.toJSONString(parseObject(v), beautify)
                else -> JSON.toJSONString((JSON.toJSON(v) as JSONObject), beautify)
            }
        } catch (e: JSONException) {
            LogUtils.e(TAG,"toJSONString JSONException $e")
            ""
        } catch (e: Exception) {
            LogUtils.e(TAG,"toJSONString Exception $e")
            ""
        }
    }
}