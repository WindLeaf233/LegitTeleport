package owo.foliage.legitteleport.utils

import owo.foliage.legitteleport.LegitTeleport
import java.io.File

object FileUtil {
    private val path = LegitTeleport.instance.dataFolder.absolutePath + File.separator

    fun makeFile(path: String) {
        val file = File(path)
        if (!file.exists()) {
            file.parentFile.mkdirs()
            file.createNewFile()
        }
    }

    fun save(content: String, path: String) {
        val file = File(path)
        if (file.exists()) file.writeText(content)
        else {
            makeFile(path)
            save(content, path)
        }
    }

    fun read(path: String, emptyContent: String = ""): String {
        val file = File(path)
        return if (file.exists()) file.readText() else {
            save(emptyContent, path)
            emptyContent
        }
    }

    fun saveResource(name: String) {
        if (!File(getResourcePath(name)).exists()) LegitTeleport.instance.saveResource(name, false)
    }

    fun getResourcePath(name: String) = "%s%s".format(path, name)
}