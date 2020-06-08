package com.xiaxiayige.flutter.help

import java.io.*

fun main(args: Array<String>) {

    val fileArray =
        arrayOf(
            ResourceBean("assets", "aaa.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "bbb.png"),
            ResourceBean("assets", "ccc.png")


        )

    val fileDir = File("D:\\IdeaProjects\\DartResourceGenerate\\src\\com\\generate\\")
    if (!fileDir.exists()) {
        fileDir.mkdir()
    }
    val file = File(fileDir, "r.dart")
    if(!file.exists()){
        file.createNewFile()
    }

    val startTime = System.currentTimeMillis()
    val ous = FileOutputStream(file)

    val bos = BufferedOutputStream(ous)

    bos.write("class R {".toByteArray())
    fileArray.forEach {
        bos.write("\n\tstatic const String ${it.fileName.split(".")[0]} = \"${it.dir}/${it.fileName}\";".toByteArray())
    }
    bos.write("\n}".toByteArray())
    bos.flush()
    bos.close()
    println("结束时间" + (System.currentTimeMillis() - startTime))

}