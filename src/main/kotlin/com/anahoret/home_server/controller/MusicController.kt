package com.anahoret.home_server.controller

import com.anahoret.home_server.MusicService
import com.anahoret.home_server.models.TrackList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.io.File
import java.net.URLDecoder
import java.nio.file.Files

@Controller
@Secured("USER")
@RequestMapping("music")
class MusicController {

  @Autowired
  private lateinit var musicService: MusicService

  @GetMapping
  fun music(): String = "music"

  @GetMapping("playlist")
  @ResponseBody
  fun playlist(): TrackList {
    return musicService.getTrackList()
  }

  @GetMapping("track")
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

}
