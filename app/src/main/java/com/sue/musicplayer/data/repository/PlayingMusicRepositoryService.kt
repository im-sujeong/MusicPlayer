package com.sue.musicplayer.data.repository

import com.sue.musicplayer.data.entity.PlayingMusicEntity
import kotlinx.coroutines.flow.Flow

interface PlayingMusicRepositoryService {
    suspend fun updatePlayingList(playingMusicEntity: PlayingMusicEntity)

    suspend fun updateFavoriteMusic(playingMusicEntity: PlayingMusicEntity)

    suspend fun getPlayingMusicList(): List<PlayingMusicEntity>

    val lastPlayingMusic: Flow<PlayingMusicEntity?>
}