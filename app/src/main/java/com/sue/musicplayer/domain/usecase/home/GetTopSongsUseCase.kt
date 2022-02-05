package com.sue.musicplayer.domain.usecase.home

import com.sue.musicplayer.data.mapper.toMusicModel
import com.sue.musicplayer.data.repository.MusicRepositoryService
import com.sue.musicplayer.domain.model.MusicModel

internal class GetTopSongsUseCase(
    private val musicRepositoryService: MusicRepositoryService
) {
    suspend operator fun invoke(): List<MusicModel> {
        return musicRepositoryService.getTopSongsAll().map {
            it.toMusicModel()
        }.subList(0, 5)
    }
}