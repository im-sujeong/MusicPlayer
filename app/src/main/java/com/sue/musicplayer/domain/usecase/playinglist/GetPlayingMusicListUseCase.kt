package com.sue.musicplayer.domain.usecase.playinglist

import com.sue.musicplayer.data.mapper.toPlayingMusicModel
import com.sue.musicplayer.domain.repository.PlayingMusicRepositoryService
import com.sue.musicplayer.domain.model.PlayingMusicModel

class GetPlayingMusicListUseCase(
    private val playingMusicRepositoryService: PlayingMusicRepositoryService
) {
    suspend operator fun invoke(): List<PlayingMusicModel> =
        playingMusicRepositoryService.getPlayingMusicList().map {
            it.toPlayingMusicModel()
        }
}