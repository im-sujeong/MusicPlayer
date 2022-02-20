package com.sue.musicplayer.domain.usecase.player

import com.sue.musicplayer.data.mapper.toPlayingMusicEntity
import com.sue.musicplayer.domain.repository.PlayingMusicRepositoryService
import com.sue.musicplayer.domain.model.PlayingMusicModel

class UpdateFavoriteMusicUseCase(
    private val playingMusicRepositoryService: PlayingMusicRepositoryService
) {
    suspend operator fun invoke(playingMusicModel: PlayingMusicModel) {
        playingMusicRepositoryService.updateFavoriteMusic(playingMusicModel.toPlayingMusicEntity())
    }
}