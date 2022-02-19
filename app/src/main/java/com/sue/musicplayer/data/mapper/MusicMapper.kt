package com.sue.musicplayer.data.mapper

import com.google.android.exoplayer2.MediaItem
import com.sue.musicplayer.data.entity.PlayingMusicEntity
import com.sue.musicplayer.data.response.RecommendMusicResponse
import com.sue.musicplayer.data.response.MusicResponse
import com.sue.musicplayer.domain.model.PlayingMusicModel
import com.sue.musicplayer.domain.model.RecommendMusicModel
import com.sue.musicplayer.domain.model.MusicModel

fun RecommendMusicResponse.toRecommendMusicModel() =
    RecommendMusicModel(
        id = id,
        title = title,
        subTitle = subTitle,
        imageUrl = imageUrl
    )

fun MusicResponse.toMusicModel() =
    MusicModel(
        id = id,
        title = title,
        artistName = artistName,
        coverImageUrl = coverImageUrl,
        streamUrl = streamUrl,
        number = number,
        rank = rank
    )

fun PlayingMusicEntity.toPlayingMusicModel(isSelected: Boolean = false, isPlaying: Boolean = false) =
    PlayingMusicModel(
        id, title, artistName, coverImageUrl, streamUrl, isFavorite, addedDateTime, lastPlayingDateTime, isSelected, isPlaying
    )

fun PlayingMusicModel.toPlayingMusicEntity() =
    PlayingMusicEntity(
        id, title, artistName, coverImageUrl, streamUrl, isFavorite, addedDateTime, lastPlayingDateTime
    )

fun PlayingMusicModel.toMediaItem() =
    MediaItem
        .Builder()
        .setMediaId(id.toString())
        .setUri(streamUrl)
        .build()

fun MusicModel.toPlayingMusicModel(addedDateTime: Long, lastPlayingDateTime: Long) =
    PlayingMusicModel(
        id, title, artistName, coverImageUrl, streamUrl, false, addedDateTime, lastPlayingDateTime, false, false
    )

fun List<PlayingMusicEntity>.toPlayingMusicModelList() =
    map {
        it.toPlayingMusicModel()
    }
