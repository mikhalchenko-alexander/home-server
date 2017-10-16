package com.anahoret.home_server.controller

import com.anahoret.home_server.MusicService
import com.anahoret.home_server.models.FolderDto
import org.apache.tika.config.TikaConfig
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
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
  fun mediaLibrary(): FolderDto = musicService.getMediaLibrary()

  @GetMapping("track/{id}")
  fun file(@PathVariable("id") trackId: Long): ResponseEntity<ByteArray> {
    return musicService.getFilePath(trackId)?.let { filePath ->
      FileResponse.createFromPath(filePath, tika)
    } ?: ResponseEntity(HttpStatus.NOT_FOUND)
  }

}
