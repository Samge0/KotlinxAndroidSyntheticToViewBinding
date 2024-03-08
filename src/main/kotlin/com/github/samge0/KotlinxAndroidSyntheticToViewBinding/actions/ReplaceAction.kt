package com.github.samge0.KotlinxAndroidSyntheticToViewBinding.actions

import com.github.samge0.KotlinxAndroidSyntheticToViewBinding.utils.CacheUtil
import com.github.samge0.KotlinxAndroidSyntheticToViewBinding.utils.ReplaceUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


// 替换ContentView
class ActionReplaceContentView : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        ReplaceUtil.parseActionReplaceContentView(event)
    }
}


// 替换单个id的action
class ActionReplaceBinding : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        ReplaceUtil.parseActionReplaceBinding(event, "binding.")
    }
}


// 替换所有include的action
class ActionReplaceInclude : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        ReplaceUtil.parseActionReplaceInclude(event)
    }
}


// 替换单个id的action - 自定义前缀
class ActionReplaceCustomPrefix : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        ReplaceUtil.parseActionReplaceBinding(event, CacheUtil.getCacheReplacePrefix())
    }
}


// 替换单个id的action - 设置自定义前缀
class ActionSetCustomPrefix : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        ReplaceUtil.parseActionSetCustomPrefix(event)
    }
}


// 设置自定义包名
class ActionSetCustomPackageName : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        ReplaceUtil.parseActionSetCustomPackageName(event)
    }
}