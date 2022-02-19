package com.sue.musicplayer.domain.usecase.player

import com.sue.musicplayer.data.mapper.toPlayingMusicModel
import com.sue.musicplayer.data.repository.PlayingMusicRepositoryService
import com.sue.musicplayer.domain.model.PlayingMusicModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetLastPlayingMusicUseCase(
    private val playingMusicRepositoryService: PlayingMusicRepositoryService
) {
    operator fun invoke(): Flow<PlayingMusicModel?> {
        return playingMusicRepositoryService.lastPlayingMusic.map {
            it?.toPlayingMusicModel()
        }
    }
}