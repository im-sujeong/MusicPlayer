package com.sue.musicplayer.domain.usecase.home

import com.sue.musicplayer.data.mapper.toRecommendMusicModel
import com.sue.musicplayer.domain.repository.MusicRepositoryService
import com.sue.musicplayer.domain.model.RecommendMusicModel

internal class GetRecommendMusicUseCase(
    private val musicRepositoryService: MusicRepositoryService
) {
    suspend operator fun invoke(): List<RecommendMusicModel> {
        return musicRepositoryService.getRecommendMusic().map {
            it.toRecommendMusicModel()
        }
    }
}