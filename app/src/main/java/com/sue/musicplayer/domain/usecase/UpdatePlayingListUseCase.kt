package com.sue.musicplayer.domain.usecase

import com.sue.musicplayer.data.mapper.toPlayingMusicEntity
import com.sue.musicplayer.data.repository.PlayingMusicRepositoryService
import com.sue.musicplayer.domain.model.PlayingMusicModel

class UpdatePlayingListUseCase(
    private val playingMusicRepositoryService: PlayingMusicRepositoryService
) {
    suspend operator fun invoke(playingMusicModel: PlayingMusicModel) {
        return playingMusicRepositoryService.updatePlayingList(
            playingMusicModel.toPlayingMusicEntity()
        )
    }
}