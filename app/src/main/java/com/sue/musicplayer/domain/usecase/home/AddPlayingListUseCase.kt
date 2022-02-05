package com.sue.musicplayer.domain.usecase.home

import com.sue.musicplayer.data.mapper.toPlayingMusicEntity
import com.sue.musicplayer.data.repository.PlayingMusicRepositoryService
import com.sue.musicplayer.domain.model.PlayingMusicModel

class AddPlayingListUseCase(
    private val playingMusicRepositoryService: PlayingMusicRepositoryService
) {
    suspend operator fun invoke(playingMusicModel: PlayingMusicModel) {
        return playingMusicRepositoryService.addPlayingList(
            playingMusicModel.toPlayingMusicEntity()
        )
    }
}