package com.anahoret.home_server.controller

import com.anahoret.home_server.dto.FileDto
import com.anahoret.home_server.dto.FileParentDto
import com.anahoret.home_server.dto.FileRootDto
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import java.io.File
import java.net.URLDecoder
import java.nio.file.Files
import javax.servlet.http.HttpServletResponse

@Controller
@Secured("USER")
@RequestMapping("files")
class FileSystemController {

  @GetMapping
  fun index(model: Model): String {
    val files = File.listRoots().map(::FileRootDto)
    model.addAttribute("files", files)
    return "files"
  }

  @GetMapping("/directory")
  fun directory(@RequestParam("url") url: String, model: Model): String {
    val directory = File(URLDecoder.decode(url, "UTF-8"))
    if (!directory.exists()) {
      throw FileNotFoundException()
    } else if (directory.isDirectory) {
      val files = mutableListOf<FileDto>()
      if (directory.parentFile != null) {
        files.add(FileParentDto(directory.parentFile))
        model.addAttribute("hasParent", true)
      }
      files.addAll(directory.listFiles().map(::FileDto).toMutableList())
      model.addAttribute("files", files.toList())
      return "files"
    } else {
      throw FileNotFoundException()
    }
  }

  @GetMapping("/file")
  fun file(@RequestParam("url") url: String): ResponseEntity<ByteArray> {
    val file = File(URLDecoder.decode(url, "UTF-8"))
    if (!file.exists()) {
      return ResponseEntity(HttpStatus.NOT_FOUND)
    } else if (file.isDirectory) {
      return ResponseEntity(HttpStatus.NOT_FOUND)
    } else {
      val mimeType = Files.probeContentType(file.toPath())
      val bytes = file.readBytes()
      val headers = HttpHeaders()
      headers.contentType = MediaType.parseMediaType(mimeType)
      return ResponseEntity(bytes, headers, HttpStatus.OK)
    }
  }

  @ExceptionHandler
  private fun handleFileNotFoundException(e: FileNotFoundException, response: HttpServletResponse) {
    response.sendError(HttpStatus.NOT_FOUND.value(), e.message)
  }

  private class FileNotFoundException: RuntimeException()

}
