package com.sue.musicplayer.domain.usecase

import com.sue.musicplayer.data.mapper.toRecommendMusicModel
import com.sue.musicplayer.data.repository.MusicRepositoryService
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