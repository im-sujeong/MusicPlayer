package com.sue.musicplayer.data.response

data class MusicResponse(
    val id: Long,
    val title: String,
    val artistName: String,
    val coverImageUrl: String,
    val streamUrl: String,
    val number: Int?,
    val rank: Int?
)