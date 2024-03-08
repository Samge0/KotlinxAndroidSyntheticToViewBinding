package com.github.samge0.KotlinxAndroidSyntheticToViewBinding.ext

fun String.toCamelCase():  String {
    // 如果字符串不包含下划线，直接返回原字符串
    if (!this.contains("_")) return this.replaceFirstChar { char -> char.lowercase() }

    // 对包含下划线的字符串进行处理
    return split("_").mapIndexed { index, s ->
        if (index > 0) s.replaceFirstChar { it.uppercase() } else s.lowercase()
    }.joinToString("")
}

fun String.toCapitalCase(): String = split("_").joinToString(" ") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }

fun String.toConstantCase(): String = uppercase().replace(" ", "_")

fun String.toDotCase(): String = lowercase().replace(" ", ".")

fun String.toHeaderCase(): String = split(" ").joinToString("-") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }

fun String.toNoCase(): String = lowercase().replace("_", " ")

fun String.toParamCase(): String = lowercase().replace(" ", "-")

fun String.toPascalCase(): String = split("_").joinToString("") { it.lowercase().replaceFirstChar { char -> char.uppercase() } }

fun String.toPathCase(): String = lowercase().replace(" ", "/")

fun String.toSnakeCase(): String = lowercase().replace(" ", "_")
