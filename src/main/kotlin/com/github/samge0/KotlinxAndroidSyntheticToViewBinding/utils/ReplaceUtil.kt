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


object ReplaceUtil {


    // 替换ContentView
    fun parseActionReplaceContentView(event: AnActionEvent) {
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
            val newText = CamelCaseUtil.camelCaseWithPreSuffix(selectedText, prefix=prefix)
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
                val newText = CamelCaseUtil.camelCaseWithPreSuffix(it, prefix=prefix)
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
}