package com.sue.musicplayer.data.repository

import com.sue.musicplayer.data.network.MusicService
import com.sue.musicplayer.data.response.RecommendMusicResponse
import com.sue.musicplayer.data.response.MusicResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MusicRepositoryImpl(
    private val musicService: MusicService,
    private val ioDispatcher: CoroutineDispatcher
): MusicRepositoryService {
    override suspend fun getRecommendMusic(): List<RecommendMusicResponse> = withContext(ioDispatcher){
        val response = musicService.getRecommendMusic()

        if( response.isSuccessful ){
            return@withContext response.body() ?: listOf()
        }else {
            return@withContext listOf()
        }
    }

    override suspend fun getReleaseMusicAll(): List<MusicResponse> = withContext(ioDispatcher){
        val response = musicService.getReleaseMusicAll()

        if( response.isSuccessful ){
            return@withContext response.body() ?: listOf()
        }else {
            return@withContext listOf()
        }
    }

    override suspend fun getTopSongsAll(): List<MusicResponse> = withContext(ioDispatcher){
        val response = musicService.getTopSongsAll()

        if( response.isSuccessful ) {
            return@withContext response.body() ?: listOf()
        }else {
            return@withContext listOf()
        }
    }
}