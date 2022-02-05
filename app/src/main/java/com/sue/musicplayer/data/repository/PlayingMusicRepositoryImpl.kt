package com.sue.musicplayer.data.repository

import android.util.Log
import com.sue.musicplayer.data.db.dao.PlayingMusicDao
import com.sue.musicplayer.data.entity.PlayingMusicEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.lang.Exception

class PlayingMusicRepositoryImpl(
    private val playingMusicDao: PlayingMusicDao,
    private val ioDispatcher: CoroutineDispatcher
): PlayingMusicRepositoryService {
    override suspend fun addPlayingList(playingMusicEntity: PlayingMusicEntity) = withContext(ioDispatcher){
        val music = playingMusicDao.checkDuplicateMusic(playingMusicEntity.id)

        if( music == null ) {
            Log.i("sujeong", "addPlayingList !!!")
            playingMusicDao.addPlayingList(playingMusicEntity)
        }else {
            Log.i("sujeong", "updateMusic !!!")
            playingMusicDao.updateMusic(
                music.copy(
                    lastPlayingDateTime = System.currentTimeMillis()
                )
            )
        }
    }

    override val playingMusicList: Flow<List<PlayingMusicEntity>> =
        playingMusicDao.getPlayingMusicListAll()
            .distinctUntilChanged()
            .flowOn(ioDispatcher)
}