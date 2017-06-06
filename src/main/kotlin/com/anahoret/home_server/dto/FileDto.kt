package com.anahoret.home_server.dto

import java.io.File
import java.net.URLEncoder

open class FileDto(file: File) {
  val url = URLEncoder.encode(file.absolutePath, "UTF-8")
  open val name = file.name
  val isDirectory = file.isDirectory
}

class FileRootDto(file: File): FileDto(file) {
  override val name = file.absolutePath
}

class FileParentDto(file: File): FileDto(file) {
  override val name = ""
}
