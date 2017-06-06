package com.anahoret.home_server

import com.anahoret.home_server.models.Track
import com.anahoret.home_server.models.TrackList
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

  fun getTrackList(): TrackList {
    val rootDir = File(rootDirPath)
    require(rootDir.isDirectory, { "$rootDirPath is not a directory" })

    return walkDir(rootDir)
  }

  private fun walkDir(dir: File): TrackList {
    val tl = dir.listFiles()?.let { children ->
      val trackLists = children.filter(File::isDirectory).map(this::walkDir).toTypedArray()
      val tracks = children.filter { it.isFile && it.extension == "mp3" }
        .map {
          val audioFile = AudioFileIO.read(it)
          val duration = audioFile.audioHeader.trackLength
          val localTime = LocalTime.ofSecondOfDay(duration.toLong())
          Track(it.name, localTime.toString(), encodeUrl(it.absolutePath))
        }.toTypedArray()

      TrackList(dir.name, trackLists, tracks)
    }

    return tl ?: TrackList.empty()
  }

  private fun encodeUrl(filePath: String): String {
    return "/music/track?url=${URLEncoder.encode(filePath, "UTF-8")}"
  }

}
