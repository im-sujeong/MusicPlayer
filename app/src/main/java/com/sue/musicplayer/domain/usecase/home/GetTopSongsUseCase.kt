package com.sue.musicplayer.domain.usecase.home

import com.sue.musicplayer.data.mapper.toMusicModel
import com.sue.musicplayer.domain.repository.MusicRepositoryService
import com.sue.musicplayer.domain.model.MusicModel

internal class GetTopSongsUseCase(
    private val musicRepositoryService: MusicRepositoryService
) {
    suspend operator fun invoke(): List<MusicModel> {
        val topSongsList = musicRepositoryService.getTopSongsAll().map {
            it.toMusicModel()
        }

        return if(topSongsList.isEmpty()) {
            topSongsList
        }else {
            topSongsList.subList(0, 5)
        }
    }
}