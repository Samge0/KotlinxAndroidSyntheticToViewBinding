package com.github.samge0.KotlinxAndroidSyntheticToViewBinding.actions

import com.github.samge0.KotlinxAndroidSyntheticToViewBinding.utils.CacheUtil
import com.github.samge0.KotlinxAndroidSyntheticToViewBinding.utils.ReplaceUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent


// 替换 FragmentContentView
class ActionReplaceFragmentContentView : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        ReplaceUtil.parseActionReplaceFragmentContentView(event)
    }
}


// 替换 ActivityContentView
class ActionReplaceActivityContentView : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        ReplaceUtil.parseActionReplaceActivityContentView(event)
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


// 设置自定义BaseFragment路径
class ActionSetCustomBaseFragmentPath : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        ReplaceUtil.parseActionSetCustomBaseFragmentPath(event)
    }
}


// 设置旧的BaseFragment名称（用于自动替换）
class ActionSetOldBaseFragmentName : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        ReplaceUtil.parseActionSetOldBaseFragmentName(event)
    }
}