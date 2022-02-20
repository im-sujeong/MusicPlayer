package com.sue.musicplayer.domain.usecase.topsongs

import com.sue.musicplayer.data.mapper.toMusicModel
import com.sue.musicplayer.domain.repository.MusicRepositoryService
import com.sue.musicplayer.domain.model.MusicModel

class GetAllTopSongsUseCase(
    private val musicRepositoryService: MusicRepositoryService
) {
    suspend operator fun invoke(): List<MusicModel> {
        return musicRepositoryService.getTopSongsAll().map {
            it.toMusicModel()
        }
    }
}