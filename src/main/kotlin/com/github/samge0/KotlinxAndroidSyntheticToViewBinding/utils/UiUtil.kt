package com.github.samge0.KotlinxAndroidSyntheticToViewBinding.utils

import com.intellij.openapi.ui.ComboBox
import java.awt.Insets
import java.awt.event.ActionEvent
import java.awt.event.ItemEvent
import javax.swing.*
import javax.swing.event.DocumentListener

object UiUtil {

    /**
     * 获取一个容器
     *
     * @param horizontal 是否横向
     * @return
     */
    fun getBox(horizontal: Boolean = false): Box {
        return if(horizontal) Box.createHorizontalBox() else Box.createVerticalBox()
    }

    /**
     * 获取切换按钮
     *
     * @param selected
     * @return
     */
    fun getJToggleButton(selected: Boolean, l: ((Boolean) -> Unit)?=null): JToggleButton {
        return JToggleButton().apply {
            isSelected = selected
            // 除去边框
            border = null
            // 首先设置不绘制按钮边框
            isBorderPainted = false
            // 设置按钮背景透明
            isContentAreaFilled = false
            // 将边框外的上下左右空间设置为0
            margin = Insets(3, -35, 0, 0)
            // 将标签中显示的文本和图标之间的间隔量设置为0
            iconTextGap = 0
            // 设置 选中(开) 和 未选中(关) 时显示的图片
            selectedIcon = FileUtil.getImageIcon("images/toggle-on.png", 32, 32)
            icon = FileUtil.getImageIcon("images/toggle-off.png", 32, 32)
            // 添加 toggleBtn 的状态被改变的监听
            addChangeListener { e ->
                val isSelected = (e.source as JToggleButton).isSelected
                LogUtils.d("是否选中: $isSelected")
                l?.let { it(isSelected) }
            }
        }
    }

    /**
     * 往box中添加一个切换按钮
     *
     * @param box 容器
     * @param label 描述文本
     * @param selected 默认填充文本
     * @param l 监听对象
     * @return
     */
    fun addJToggleButton(box: Box, label: String, selected: Boolean, l: ((Boolean) -> Unit)?=null): JToggleButton {
        box.add(JLabel(label))
        val view = getJToggleButton(selected, l)
        box.add(view)
        return view
    }

    /**
     * 获取 输入框
     *
     * @param text 默认填充文本
     * @param rows 行数
     * @param columns 列数
     * @param l 监听对象
     * @return
     */
    fun getJTextArea(text: String?, rows: Int, columns: Int, l: DocumentListener?=null): JTextArea {
        return JTextArea(text, rows, columns).apply {
            // 设置自动换行，默认为 false。
            lineWrap = true
            // 设置自动换行方式。如果为 true，则将在单词边界（空白）处换行; 如果为 false，则将在字符边界处换行。默认为 false。
            wrapStyleWord = true
            isEditable = true
            document.addDocumentListener(l)
            margin = Insets(5, 5, 5, 5)
        }
    }

    /**
     * 往box中添加一个输入框
     *
     * @param box 容器
     * @param label 描述文本
     * @param text 默认填充文本
     * @param rows 行数
     * @param columns 列数
     * @param l 监听对象
     * @return
     */
    fun addJTextArea(box: Box, label: String, text: String?, rows: Int, columns: Int, l: DocumentListener?=null): JTextArea {
        box.add(JLabel(label))
        val et = getJTextArea(text, rows, columns, l)
        box.add(et)
        return et
    }


    /**
     * 获取下拉选择view
     *
     * @param lst   列表
     * @param v     当前选中项
     * @param l     监听
     * @return
     */
    fun getJComboBox(lst: Array<String>, v: String, l: ((String) -> Unit)?=null): ComboBox<String> {
        return ComboBox(lst).apply {
            addItemListener { e -> // 只处理选中的状态
                if (ItemEvent.SELECTED == e.stateChange) {
                    LogUtils.d("选中: $selectedIndex = $selectedItem")
                    l?.let { it((selectedItem?.toString()?:"")) }
                }
            }
            // 设置默认选中的条目
            selectedIndex = if (v.isEmpty()) 0 else lst.indexOfFirst { it == v }
        }
    }

    /**
     * 往box中添加一个下拉选择view
     *
     * @param box 容器
     * @param label 描述文本
     * @param lst   列表
     * @param v     当前选中项
     * @return
     */
    fun addJComboBox(box: Box, label: String, lst: Array<String>, v: String, l: ((String) -> Unit)?=null): ComboBox<String> {
        box.add(JLabel(label))
        val comboBox = getJComboBox(lst, v, l)
        box.add(comboBox)
        return comboBox
    }

    /**
     * 获取一个按钮
     *
     * @param label   按钮文本
     * @param l     点击监听
     * @return
     */
    fun getJButton(label: String, l: ((ActionEvent) -> Unit)?=null): JButton {
        return JButton(label).apply {
            addActionListener(l)
        }
    }
}