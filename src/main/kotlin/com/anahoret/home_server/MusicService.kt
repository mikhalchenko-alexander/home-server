package com.anahoret.home_server

import com.anahoret.home_server.models.FolderDto
import com.anahoret.home_server.models.TrackDto
import org.jaudiotagger.audio.AudioFileIO
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.id3.ID3v23Tag
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.util.*
import java.util.concurrent.atomic.AtomicLong

@Service
class MusicService {

  @Value("\${music.root_dir}")
  lateinit var rootDirPath: String

  private val trackId = AtomicLong(0)
  private val folderId = AtomicLong(0)
  private val trackMap = Collections.synchronizedMap(mutableMapOf<Long, String>())

  val ml by lazy {
    val rootDir = File(rootDirPath)
    require(rootDir.isDirectory, { "\"$rootDirPath\" is not a directory" })
    walkDir(rootDir, isRoot = true)
  }

  fun getMediaLibrary(): FolderDto = ml

  private fun walkDir(dir: File, isRoot: Boolean = false): FolderDto {
    val name = if (isRoot) "ROOT" else dir.name
    val tl = dir.listFiles()?.let { children ->
      val folders = children
        .filter(File::isDirectory)
        .sortedBy { it.name }
        .map { walkDir(it) }
      val tracks = children
        .filter { it.isFile && it.extension == "mp3" }
        .sortedBy { it.name }
        .map {
          val audioFile = AudioFileIO.read(it)
          val duration = audioFile.audioHeader.trackLength
          val tag = audioFile.tag as ID3v23Tag
          val title = tag.getFirst(FieldKey.TITLE)
          val artist = tag.getFirst(FieldKey.ARTIST)
          val album = tag.getFirst(FieldKey.ALBUM)
          val id = trackId.getAndIncrement()
          trackMap.put(id, it.absolutePath)
          TrackDto(id, title, artist, album, duration)
        }
      FolderDto(folderId.getAndIncrement(), name, folders, tracks)
    }
    return tl ?: FolderDto(folderId.getAndIncrement(), name, emptyList(), emptyList())
  }

  fun getFilePath(trackId: Long): String? = trackMap[trackId]

}
