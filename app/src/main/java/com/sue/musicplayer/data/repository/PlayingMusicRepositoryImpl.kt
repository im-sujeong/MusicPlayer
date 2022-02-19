package com.sue.musicplayer.data.repository

import com.sue.musicplayer.data.db.dao.PlayingMusicDao
import com.sue.musicplayer.data.entity.PlayingMusicEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class PlayingMusicRepositoryImpl(
    private val playingMusicDao: PlayingMusicDao,
    private val ioDispatcher: CoroutineDispatcher
): PlayingMusicRepositoryService {
    override suspend fun updatePlayingList(playingMusicEntity: PlayingMusicEntity) = withContext(ioDispatcher){
        val music = playingMusicDao.checkDuplicateMusic(playingMusicEntity.id)

        if( music == null ) {
            playingMusicDao.addPlayingList(playingMusicEntity)
        }else {
            playingMusicDao.updateMusic(
                music.copy(
                    lastPlayingDateTime = System.currentTimeMillis()
                )
            )
        }
    }

    override suspend fun updateFavoriteMusic(playingMusicEntity: PlayingMusicEntity) = withContext(ioDispatcher){
        playingMusicDao.updateMusic(playingMusicEntity)
    }

    override suspend fun getPlayingMusicList(): List<PlayingMusicEntity> = withContext(ioDispatcher){
        playingMusicDao.getPlayingMusicList()
    }

    override val lastPlayingMusic: Flow<PlayingMusicEntity?> =
        playingMusicDao.getLastPlayingMusic()
            .flowOn(ioDispatcher)
}