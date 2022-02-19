package com.sue.musicplayer.domain.usecase.release

import com.sue.musicplayer.data.mapper.toMusicModel
import com.sue.musicplayer.data.repository.MusicRepositoryService
import com.sue.musicplayer.domain.model.MusicModel

class GetAllReleaseMusicUseCase(
    private val musicRepositoryService: MusicRepositoryService
) {
    suspend operator fun invoke(): List<MusicModel> {
        return musicRepositoryService.getReleaseMusicAll().map {
            it.toMusicModel()
        }
    }
}