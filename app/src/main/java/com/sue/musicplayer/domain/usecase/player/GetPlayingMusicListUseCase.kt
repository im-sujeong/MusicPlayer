package com.sue.musicplayer.domain.usecase.player

import com.sue.musicplayer.data.mapper.toPlayingMusicModelList
import com.sue.musicplayer.data.repository.PlayingMusicRepositoryService
import com.sue.musicplayer.domain.model.PlayingMusicModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetPlayingMusicListUseCase(
    private val playingMusicRepositoryService: PlayingMusicRepositoryService
) {
    operator fun invoke(): Flow<List<PlayingMusicModel>> {
        return playingMusicRepositoryService.playingMusicList.map {
            it.toPlayingMusicModelList()
        }
    }
}