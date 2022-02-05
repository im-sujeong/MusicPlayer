package com.sue.musicplayer.data.network

import com.sue.musicplayer.data.response.RecommendMusicResponse
import com.sue.musicplayer.data.response.MusicResponse
import retrofit2.Response
import retrofit2.http.GET

interface MusicService {
    @GET("/recommend")
    suspend fun getRecommendMusic(): Response<List<RecommendMusicResponse>>

    @GET("/release")
    suspend fun getReleaseMusicAll(): Response<List<MusicResponse>>

    @GET("/topSongs")
    suspend fun getTopSongsAll(): Response<List<MusicResponse>>
}