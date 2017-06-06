package com.anahoret.home_server.models

class TrackList (val name: String, val folders: Array<TrackList>, val tracks: Array<Track>) {

  companion object {
    fun empty(): TrackList {
      return TrackList("<EMPTY>", arrayOf(), arrayOf())
    }
  }

}
