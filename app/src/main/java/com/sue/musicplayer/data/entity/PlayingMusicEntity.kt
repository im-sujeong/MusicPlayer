package com.sue.musicplayer.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class PlayingMusicEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val artistName: String,
    val coverImageUrl: String,
    val streamUrl: String,
    val isFavorite: Boolean,
    val addedDateTime: Long,
    val lastPlayingDateTime: Long
): Parcelable