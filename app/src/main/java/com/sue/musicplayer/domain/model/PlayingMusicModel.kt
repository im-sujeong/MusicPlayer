package com.sue.musicplayer.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayingMusicModel(
    val id: Long,
    val title: String,
    val artistName: String,
    val coverImageUrl: String,
    val streamUrl: String,
    val isFavorite: Boolean,
    val addedDateTime: Long,
    val lastPlayingDateTime: Long,
    val isSelected: Boolean,
    val isPlaying: Boolean
): Parcelable