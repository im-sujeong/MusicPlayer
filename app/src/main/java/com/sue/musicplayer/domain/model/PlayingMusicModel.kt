package com.sue.musicplayer.domain.model

data class PlayingMusicModel(
    val id: Long,
    val title: String,
    val artistName: String,
    val coverImageUrl: String,
    val streamUrl: String,
    val isFavorite: Boolean,
    val addedDateTime: Long,
    val lastPlayingDateTime: Long,
    val isPlaying: Boolean
)