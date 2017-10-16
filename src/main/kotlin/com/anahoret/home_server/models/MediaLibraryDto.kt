package com.anahoret.home_server.models

class FolderDto(
  val id: Long,
  val name: String,
  val folders: List<FolderDto>,
  val tracks: List<TrackDto>
)

class TrackDto(
  val id: Long,
  val title: String,
  val artist: String,
  val album: String,
  val duration: Int
)
