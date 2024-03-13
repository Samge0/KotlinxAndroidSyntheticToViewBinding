package com.github.samge0.KotlinxAndroidSyntheticToViewBinding.utils

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Document
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import java.awt.datatransfer.StringSelection
import java.util.*


object ReplaceUtil {


    // 替换 FragmentContentView
    fun parseActionReplaceFragmentContentView(event: AnActionEvent) {
        val baseFragmentPath = CacheUtil.getCacheBaseFragmentPath()
        if (baseFragmentPath.isEmpty()) return

        val baseFragmentName = baseFragmentPath.split(".").last()

        val editor: Editor? = event.getData(CommonDataKeys.EDITOR)
        val project: Project? = event.project
        if (editor != null && project != null) {
            val document = editor.document
            val selectionModel = editor.selectionModel
            val selectedText = selectionModel.selectedText
            selectedText?.let {
                val layoutName = extractLayoutName(it) ?: it
                val layoutNameFull = CamelCaseUtil.camelCaseWithPreSuffix(layoutName, camelCaseType = CamelCaseUtil.CamelCaseEnum.PascalCase, suffix = "Binding")

                // 需要复制到剪贴板的文本
                val packageName = CacheUtil.getCachePackageName()
                val newBaseFragmentInfo = """$baseFragmentName<$layoutNameFull>(
    inflate = $layoutNameFull::inflate
)"""
                val textToCopy = if (packageName.isEmpty()) {
                    """import ${CacheUtil.getCachePackageName()}.databinding.$layoutNameFull
$newBaseFragmentInfo"""
                } else {
                    """import $baseFragmentPath
import ${CacheUtil.getCachePackageName()}.databinding.$layoutNameFull
$newBaseFragmentInfo"""
                }
                CopyPasteManager.getInstance().setContents(StringSelection(textToCopy))

                // 判断是否自动替换OldBaseFragmentName
                val oldBaseFragmentName = CacheUtil.getCacheOldBaseFragmentName()
                if (oldBaseFragmentName.isNotEmpty()) {
                    WriteCommandAction.runWriteCommandAction(project) {
                        var text = document.text
                        val oldBaseFragmentInfo = if ("$oldBaseFragmentName()" in text) {
                            "$oldBaseFragmentName()"
                        } else {
                            oldBaseFragmentName
                        }
                        text = text.replace(oldBaseFragmentInfo, newBaseFragmentInfo)
                        document.setText(text)
                    }
                }

                // 置空选中内容
                WriteCommandAction.runWriteCommandAction(project) {
                    document.replaceString(selectionModel.selectionStart, selectionModel.selectionEnd, "")
                }
                selectionModel.removeSelection()
            }
        }
    }


    // 替换 ActivityContentView
    fun parseActionReplaceActivityContentView(event: AnActionEvent) {
        val editor: Editor? = event.getData(CommonDataKeys.EDITOR)
        val project: Project? = event.project
        if (editor != null && project != null) {
            val document = editor.document
            val selectionModel = editor.selectionModel
            val selectedText = selectionModel.selectedText
            selectedText?.let {
                val layoutName = extractLayoutName(it)
                if (layoutName.isNullOrEmpty()) return@let

                val layoutNameFull = CamelCaseUtil.camelCaseWithPreSuffix(layoutName, camelCaseType = CamelCaseUtil.CamelCaseEnum.PascalCase, suffix = "Binding")

                // 需要复制到剪贴板的文本
                val packageName = CacheUtil.getCachePackageName()
                val textToCopy = if (packageName.isEmpty()) {
                    "private lateinit var binding: $layoutNameFull"
                } else {
                    """import ${CacheUtil.getCachePackageName()}.databinding.$layoutNameFull
        private lateinit var binding: $layoutNameFull"""
                }
                CopyPasteManager.getInstance().setContents(StringSelection(textToCopy))

                val replaceValue = """binding = $layoutNameFull.inflate(layoutInflater)
        setContentView(binding.root)"""
                WriteCommandAction.runWriteCommandAction(project) {
                    document.replaceString(selectionModel.selectionStart, selectionModel.selectionEnd, replaceValue)
                }
                selectionModel.removeSelection()
            }
        }
    }


    // 替换单个id的action - 设置自定义前缀
    fun parseActionSetCustomPrefix(event: AnActionEvent) {

        // 弹出输入框
        val userInput = Messages.showInputDialog(
                event.project,
                "Please enter a custom prefix:",
                "Set Custom Prefix",
                Messages.getQuestionIcon(),
                CacheUtil.getCacheReplacePrefix(),
                null
        )

        CacheUtil.putCacheReplacePrefix(userInput?:"")
    }


    // 设置自定义包名
    fun parseActionSetCustomPackageName(event: AnActionEvent) {

        // 弹出输入框
        val userInput = Messages.showInputDialog(
                event.project,
                "Please enter a custom PackageName:",
                "Set Custom PackageName",
                Messages.getQuestionIcon(),
                CacheUtil.getCachePackageName(),
                null
        )

        CacheUtil.putCachePackageName(userInput?:"")
    }


    // 设置自定义BaseFragment路径
    fun parseActionSetCustomBaseFragmentPath(event: AnActionEvent) {

        // 弹出输入框
        val userInput = Messages.showInputDialog(
                event.project,
                "Please enter a custom BaseFragment path:",
                "Set Custom BaseFragment Path",
                Messages.getQuestionIcon(),
                CacheUtil.getCacheBaseFragmentPath(),
                null
        )

        CacheUtil.putCacheBaseFragmentPath(userInput?:"")
    }


    // 设置旧的BaseFragment名称（用于自动替换）
    fun parseActionSetOldBaseFragmentName(event: AnActionEvent) {

        // 弹出输入框
        val userInput = Messages.showInputDialog(
                event.project,
                "Please enter oldBaseFragmentName:",
                "Set OldBaseFragmentName",
                Messages.getQuestionIcon(),
                CacheUtil.getCacheOldBaseFragmentName(),
                null
        )

        CacheUtil.putCacheOldBaseFragmentName(userInput?:"")
    }


    // 替换所有include的action - 如有有选中的，则只替换选中的
    fun parseActionReplaceInclude(event: AnActionEvent) {
        val project: Project? = event.project
        val editor = event.getData(CommonDataKeys.EDITOR)
        val document = editor?.document ?: return

        val selectionModel = editor.selectionModel
        val selectedText = selectionModel.selectedText

        val prefix = "binding.include."
        if (selectedText.isNullOrEmpty()) {
            // 需要替换的字符串及其替换值
            val idList = listOf("group_title", "group_top", "left_img", "imgbtn_left_back", "imgbtn_left_back_text", "left_title", "left_img_group", "left_headimg", "left_vipimg", "txt_title", "imgbtn_right_txt", "imgbtn_right_txt2", "imgbtn_right_menu", "item_right_top_logo", "bottom_line")
            replaceDocument(project, document, idListToMap(prefix, idList))
        } else {
            // 替换当个选中的
            val newText = CamelCaseUtil.camelCaseWithPreSuffix(convertStartsWithMLowercaseVariableName(selectedText), prefix=prefix)
            WriteCommandAction.runWriteCommandAction(project) {
                document.replaceString(selectionModel.selectionStart, selectionModel.selectionEnd, newText)
            }
            selectionModel.removeSelection()
        }
    }


    // 替换单个id的action
    fun parseActionReplaceBinding(event: AnActionEvent, prefix: String) {
        val editor: Editor? = event.getData(CommonDataKeys.EDITOR)
        val project: Project? = event.project
        if (editor != null && project != null) {
            val document = editor.document
            val selectionModel = editor.selectionModel
            val selectedText = selectionModel.selectedText
            selectedText?.let {
                val newText = CamelCaseUtil.camelCaseWithPreSuffix(convertStartsWithMLowercaseVariableName(it), prefix=prefix)
                WriteCommandAction.runWriteCommandAction(project) {
                    document.replaceString(selectionModel.selectionStart, selectionModel.selectionEnd, newText)
                }
                selectionModel.removeSelection()
            }
        }
    }


    // 提取布局名
    private fun extractLayoutName(codeLine: String): String? {
        // 正则表达式匹配 "R.layout." 后面跟随的任意单词字符 (\w+)
        val pattern = "R\\.layout\\.(\\w+)".toRegex()
        // 在给定的字符串中查找匹配项
        val matchResult = pattern.find(codeLine)
        // 如果找到匹配项，返回第一个组（圆括号内的部分）
        return matchResult?.groupValues?.get(1) // groupValues[0] 是整个匹配的字符串，[1] 是第一个圆括号内匹配的部分
    }


    // 传入指定的字典，替换文档中的内容
    private fun replaceDocument(project: Project?, document: Document, replacements: Map<String, String>) {
        WriteCommandAction.runWriteCommandAction(project) {
            var text = document.text
            for ((oldString, newString) in replacements) {
                println("$oldString => $newString")
                text = text.replace(oldString, newString)
            }
            document.setText(text)
        }
    }


    // 将id字符串列表转为指定的替换字典
    private fun idListToMap(prefix: String, idsList: List<String>): Map<String, String> {
        return idsList.associateWith { idStr ->
            CamelCaseUtil.camelCaseWithPreSuffix(idStr, prefix=prefix)
        }
    }

    // 判断是否 变量名称以m小写字母开头，后跟大写字母 的类型
    private fun isVariableNameStartsWithMLowercaseFollowedByUppercase(variableName: String): Boolean {
        // 定义正则表达式
        val regex = "^m[A-Z]".toRegex()

        // 返回变量名是否匹配正则表达式的结果
        return regex.containsMatchIn(variableName)
    }

    // 如果是 变量名称以m小写字母开头，后跟大写字母 的类型，则移除m字母，并将首字母小写
    private fun convertStartsWithMLowercaseVariableName(variableName: String): String {
        // 检查变量名是否符合条件
        if (isVariableNameStartsWithMLowercaseFollowedByUppercase(variableName)) {
            // 移除左侧的'm'并将首字母转换为小写
            return variableName.removePrefix("m").replaceFirstChar { it.lowercase(Locale.getDefault()) }
        }
        // 如果不符合条件，返回原始变量名
        return variableName
    }
}