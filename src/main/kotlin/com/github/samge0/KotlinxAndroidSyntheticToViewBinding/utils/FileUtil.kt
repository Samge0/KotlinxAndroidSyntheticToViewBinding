package com.github.samge0.KotlinxAndroidSyntheticToViewBinding.utils

import com.goterl.resourceloader.FileLoader
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiFile
import com.intellij.psi.impl.file.PsiDirectoryImpl
import java.awt.Image
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import kotlin.io.path.pathString

object FileUtil {
    /**
     * 临时数据目录，结束时需要清空
     */
    private var mTempDir: String? = null

    /**
     * 加载resource资源的目录标记
     */
    private const val TAG_RESOURCE_LOADER = "resource-loader"

    /**
     * 项目对象，在插件初始化时进行赋值
     */
    var mProject: Project? = null

    /**
     * 初始化项目对象信息
     *
     * @param project
     */
    fun init(project: Project) {
        mProject = project
    }

    /**
     * 获取resources中的资源文件
     *
     * @param path resources目录下的相对路径
     * @return
     */
    fun getResourceFile(path: String): File {
        val tempFile: File = FileLoader.get().load(path, this.javaClass)
        tempFile.absolutePath.toString().split(TAG_RESOURCE_LOADER).let {
            if (it.size > 1) mTempDir = it[0]
        }
        return tempFile
    }

    /**
     * 获取项目的文件路径
     *
     * @param path 项目层级下的相对路径
     * @return
     */
    fun getProjectFile(path: String): File {
        val tag = if (path.startsWith("/")) "" else "/"
        return File("${getProjectPath()}${tag}${path}")
    }

    /**
     * 获取 resources目录下 的ImageIcon
     *
     * @param path  resources目录下的相对路径
     * @param w     宽
     * @param h     高
     * @return
     */
    fun getImageIcon(path: String, w: Int, h: Int): ImageIcon {
        val imageOn: Image = ImageIO.read(getResourceFile(path))
        return ImageIcon(imageOn.getScaledInstance(w, h, Image.SCALE_DEFAULT))
    }
    /**
     * 读取文件内容
     *
     * @param f 文件
     * @return
     */
    fun read(f: File): String {
        return try {
//            val myFile = File(path)
//            val ins: InputStream = myFile.inputStream()
//            ins.readBytes().toString(Charset.defaultCharset())
            f.readText()
        } catch (e: Exception) {
            ""
        }
    }

    /**
     * 读取文件内容
     *
     * @param path 文件路径
     * @return
     */
    fun read(path: String): String {
        return read(File(path))
    }

    /**
     * 保存文件
     *
     * @param path  文件路径
     * @param txt       文件内容
     * @return
     */
    fun save(path: String, txt: String): Int {
        return try {
            File(path).writeText(txt)
            1
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param path
     * @return
     */
    fun exists(path: String): Boolean {
        return try {
            File(path).exists()
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 创建文件夹
     *
     * @param path
     * @return
     */
    fun createDirectory(path: String): Int {
        return try {
            val f = File(path)
            if (!f.isDirectory) f.mkdirs()
            1
        } catch (e: Exception) {
            -1
        }
    }

    /**
     * 获取项目路径
     *
     * @param event
     * @return
     */
    fun getProjectPath(event: AnActionEvent? = null): String {
        val project = event?.project ?: mProject
        LogUtils.d(project?.basePath)
        return project?.basePath ?: ""
    }

    /**
     * 获取 当前右击的 文件路径、文件名 信息
     *
     * @param event
     * @param isParseTable
     * @return
     */
    fun getClickPath(event: AnActionEvent, isParseTable: Boolean = false): Pair<String, String> {
        val psiFile: PsiFile? = event.getData(CommonDataKeys.PSI_FILE)
        if (isParseTable) return Pair(getProjectPath(event), "")
        return if (psiFile != null){
            val dirPath = psiFile.containingDirectory.virtualFile.toNioPath().pathString
            val fileName = psiFile.name
            LogUtils.d("dirPath = $dirPath")
            LogUtils.d("fileName = $fileName")
            Pair(dirPath, fileName)
        } else {
            val dirPath = (event.getData(CommonDataKeys.PSI_ELEMENT) as PsiDirectoryImpl).virtualFile.toNioPath().pathString
            LogUtils.d("dirPath = $dirPath")
            Pair(dirPath, "")
        }
    }

    /**
     * 清理临时目录
     *
     */
    fun clenTempDir() {
        mTempDir?.let {
            val f = File(it)
            if (f.exists() && f.isDirectory) {
                f.listFiles()?.forEach { cf ->
                    if (cf.isDirectory && cf.name.startsWith(TAG_RESOURCE_LOADER)){
                        LogUtils.d("清理临时目录：${cf.absolutePath}")
                        cf.deleteRecursively()
                    }
                }
            }
        }
    }
}