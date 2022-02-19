package com.sue.musicplayer.domain.usecase.home

import com.sue.musicplayer.data.mapper.toMusicModel
import com.sue.musicplayer.data.repository.MusicRepositoryService
import com.sue.musicplayer.domain.model.MusicModel

class GetReleaseMusicUseCase(
    private val musicRepositoryService: MusicRepositoryService
) {
    suspend operator fun invoke(): List<MusicModel> {
        val releaseMusicList = musicRepositoryService.getReleaseMusicAll().map {
            it.toMusicModel()
        }

        return if( releaseMusicList.isEmpty() ) {
            releaseMusicList
        }else {
            releaseMusicList.subList(0, 5)
        }
    }
}