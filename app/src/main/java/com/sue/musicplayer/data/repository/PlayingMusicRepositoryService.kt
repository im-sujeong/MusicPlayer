package com.sue.musicplayer.data.repository

import com.sue.musicplayer.data.entity.PlayingMusicEntity
import kotlinx.coroutines.flow.Flow

interface PlayingMusicRepositoryService {
    suspend fun addPlayingList(playingMusicEntity: PlayingMusicEntity)

    val playingMusicList: Flow<List<PlayingMusicEntity>>
}