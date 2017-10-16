package com.anahoret.home_server

import com.anahoret.home_server.models.Folder
import com.anahoret.home_server.models.Track
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.net.URLEncoder
import org.jaudiotagger.audio.AudioFileIO
import java.time.LocalTime

@Service
class MusicService {

  @Value("\${music.root_dir}")
  lateinit var rootDirPath: String

  fun getMediaLibrary(): Folder {
    val rootDir = File(rootDirPath)
    require(rootDir.isDirectory, { "\"$rootDirPath\" is not a directory" })

    return walkDir(rootDir, isRoot = true)
  }

  private fun walkDir(dir: File, isRoot: Boolean = false): Folder {
    val name = if (isRoot) "ROOT" else dir.name
    val tl = dir.listFiles()?.let { children ->
      val folders = children.filter(File::isDirectory).map { walkDir(it) }.toTypedArray()
      val tracks = children.filter { it.isFile && it.extension == "mp3" }
        .map {
          val audioFile = AudioFileIO.read(it)
          val duration = audioFile.audioHeader.trackLength
          val localTime = LocalTime.ofSecondOfDay(duration.toLong())
          Track(it.name, localTime.toString(), encodeUrl(it.absolutePath))
        }.toTypedArray()
      Folder(name, folders, tracks)
    }
    return tl ?: Folder(name, emptyArray(), emptyArray())
  }

  private fun encodeUrl(filePath: String): String {
    return "/music/track?url=${URLEncoder.encode(filePath, "UTF-8")}"
  }

}
