package com.anahoret.home_server.controller

import org.apache.tika.config.TikaConfig
import org.apache.tika.io.TikaInputStream
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.io.File
import java.net.URLDecoder

object FileResponse {
  fun create(url: String, tika: TikaConfig): ResponseEntity<ByteArray> {
    val file = File(URLDecoder.decode(url, "UTF-8"))
    if (!file.exists()) {
      return ResponseEntity(HttpStatus.NOT_FOUND)
    } else if (file.isDirectory) {
      return ResponseEntity(HttpStatus.NOT_FOUND)
    } else {
      val metadata = org.apache.tika.metadata.Metadata()
      metadata.set(org.apache.tika.metadata.Metadata.RESOURCE_NAME_KEY, file.toString())
      val mimeType = tika.detector.detect(TikaInputStream.get(file.toPath()), metadata).toString()
      val bytes = file.readBytes()
      val headers = HttpHeaders()
      headers.contentType = MediaType.parseMediaType(mimeType)
      return ResponseEntity(bytes, headers, HttpStatus.OK)
    }
  }
}
