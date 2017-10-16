package com.anahoret.home_server.controller

import com.anahoret.home_server.MusicService
import com.anahoret.home_server.models.Folder
import org.apache.tika.config.TikaConfig
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@Secured("USER")
@RequestMapping("music")
class MusicController(
  private val musicService: MusicService,
  private val tika: TikaConfig
) {

  @GetMapping
  fun music(): String = "music"

  @GetMapping("medialibrary")
  @ResponseBody
  fun mediaLibrary(): Folder {
    return musicService.getMediaLibrary()
  }

  @GetMapping("track")
  fun file(@RequestParam("url") url: String): ResponseEntity<ByteArray> {
    return FileResponse.create(url, tika)
  }

}
