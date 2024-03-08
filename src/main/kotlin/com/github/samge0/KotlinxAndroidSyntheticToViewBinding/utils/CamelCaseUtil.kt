package com.github.samge0.KotlinxAndroidSyntheticToViewBinding.utils

import com.github.samge0.KotlinxAndroidSyntheticToViewBinding.ext.*

object CamelCaseUtil {

    enum class CamelCaseEnum {
        // CamelCase: 单词首字母小写，后续每个单词首字母大写，例如：myExampleString
        CamelCase,

        // CapitalCase: 每个单词首字母大写，单词间用空格分隔，例如：My Example String
        CapitalCase,

        // ConstantCase: 全部大写，单词间用下划线分隔，常用于常量命名，例如：MY_EXAMPLE_STRING
        ConstantCase,

        // DotCase: 全部小写，单词间用点分隔，常用于域名和包名，例如：my.example.string
        DotCase,

        // HeaderCase: 每个单词首字母大写，单词间用连字符分隔，常用于HTTP头部，例如：My-Example-String
        HeaderCase,

        // NoCase: 全部小写，单词间用空格分隔，例如：my example string
        NoCase,

        // ParamCase: 全部小写，单词间用连字符分隔，常用于URL参数和CSS类名，例如：my-example-string
        ParamCase,

        // PascalCase: 每个单词首字母大写，直接连接，例如：MyExampleString，常用于类名
        PascalCase,

        // PathCase: 全部小写，单词间用斜杠分隔，常用于URL路径，例如：my/example/string
        PathCase,

        // SnakeCase: 全部小写，单词间用下划线分隔，例如：my_example_string，常用于变量名和函数名
        SnakeCase;
    }

    private val camelCaseStrategies = mapOf(
        "camelcase" to String::toCamelCase,
        "capitalcase" to String::toCapitalCase,
        "constantcase" to String::toConstantCase,
        "dotcase" to String::toDotCase,
        "headercase" to String::toHeaderCase,
        "nocase" to String::toNoCase,
        "paramcase" to String::toParamCase,
        "pascalcase" to String::toPascalCase,
        "pathcase" to String::toPathCase,
        "snakecase" to String::toSnakeCase
    )

    fun camelCaseWithType(camelCaseType: CamelCaseEnum, value: String): String? {
        return camelCaseStrategies[camelCaseType.name.lowercase()]?.invoke(value)
    }

    fun camelCaseWithPreSuffix(
        text: String,
        camelCaseType: CamelCaseEnum=CamelCaseEnum.CamelCase,
        prefix: String = "",
        suffix: String = ""
    ): String {
        return "$prefix${camelCaseWithType(camelCaseType, text)}$suffix"
    }

}