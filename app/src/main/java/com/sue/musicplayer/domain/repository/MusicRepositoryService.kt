package com.sue.musicplayer.domain.repository

import com.sue.musicplayer.data.response.RecommendMusicResponse
import com.sue.musicplayer.data.response.MusicResponse

interface MusicRepositoryService {
    suspend fun getRecommendMusic(): List<RecommendMusicResponse>

    suspend fun getReleaseMusicAll(): List<MusicResponse>

    suspend fun getTopSongsAll(): List<MusicResponse>
}