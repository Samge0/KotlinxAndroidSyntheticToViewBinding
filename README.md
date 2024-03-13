# KotlinxAndroidSyntheticToViewBinding

![Build](https://github.com/Samge0/KotlinxAndroidSyntheticToViewBinding/workflows/Build/badge.svg)

[![Version](https://img.shields.io/jetbrains/plugin/v/23921-kotlinxandroidsynthetictoviewbinding.svg)](https://plugins.jetbrains.com/plugin/23921-kotlinxandroidsynthetictoviewbinding)

[![Downloads](https://img.shields.io/jetbrains/plugin/d/23921-kotlinxandroidsynthetictoviewbinding.svg)](https://plugins.jetbrains.com/plugin/23921-kotlinxandroidsynthetictoviewbinding)

<!-- Plugin description -->
KotlinxAndroidSyntheticToViewBinding（Kotlinx Android Synthetic To ViewBinding）

Too much use in an old Android project`kotlinx.android.synthetic.main.`，However, this plug-in has long been abandoned and needs to be migrated`viewBinding`。

Because too many layout ids are `aa_bb_cc` format，And`viewBinding` the default id is `aaBbCc`，Too many conversion operations required for manual migration。

To this end, write a small plug-in, one-click to convert the selected target id to the format supported by view Binding, and related shortcut keys：
- `shift alt a`：parse Fragment view，从选中的布局代码块中提取fragment布局id，自动复制为：`import $baseFragmentPath
import ${CacheUtil.getCachePackageName()}.databinding.$layoutNameFull
$baseFragmentName<$layoutNameFull>(
inflate = $layoutNameFull::inflate
)`
- `ctrl + alt + a`：`setContentView(R.layout.activity_xxx)` => `binding = ActivityXxxBinding.inflate(layoutInflater)
  setContentView(binding.root)`，Simultaneous automatic replication`private lateinit var binding: ActivityXxxBinding`.if provided`CustomPackageName`，will automatically copy`import {CustomPackageName}.databinding.ActivityXxxBinding`
- `ctrl + alt + 1`：aa_bb_cc => binding.aaBbCc
- `ctrl + alt + 3`：aa_bb_cc => {customPrefix}aaBbCc
- `ctrl + alt + 4`：configure a custom prefix. e.g: binding.includeXxx.
- `ctrl + alt + 5`：configure a custom PackageName. e.g: com.xxx.xxx
- `ctrl + alt + 6`：configure a custom BaseFragmentPath. e.g: com.xxx.xxx.ui.fragment.BaseFragment
- `ctrl + alt + 7`：configure a oldBaseFragmentName. e.g. BaseFragment
<!-- Plugin description end -->

## Screenshot
![](/.github/screenshots/1.png)

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "KotlinxAndroidSyntheticToViewBinding"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://gps.qianzhan.com/shaochengbao/KotlinxAndroidSyntheticToViewBinding/-/blob/main/releases/KotlinxAndroidSyntheticToViewBinding-latest.zip) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
