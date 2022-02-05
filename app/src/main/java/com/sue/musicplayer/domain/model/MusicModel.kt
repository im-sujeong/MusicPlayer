package com.sue.musicplayer.domain.model

data class MusicModel(
    val id: Long,
    val title: String,
    val artistName: String,
    val coverImageUrl: String,
    val streamUrl: String,
    val number: Int?,
    val rank: Int?
)