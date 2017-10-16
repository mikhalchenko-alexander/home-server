package com.anahoret.home_server.models

class Folder(val name: String, val folders: Array<Folder>, val tracks: Array<Track>)
