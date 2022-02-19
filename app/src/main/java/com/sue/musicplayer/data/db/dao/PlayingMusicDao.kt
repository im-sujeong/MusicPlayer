package com.sue.musicplayer.data.db.dao

import androidx.room.*
import com.sue.musicplayer.data.entity.PlayingMusicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayingMusicDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlayingList(playingMusicEntity: PlayingMusicEntity)

    @Query("SELECT * FROM PlayingMusicEntity WHERE id=:id")
    suspend fun checkDuplicateMusic(id: Long): PlayingMusicEntity?

    @Query("SELECT * FROM PlayingMusicEntity ORDER BY lastPlayingDateTime")
    fun getPlayingMusicListForFlow(): Flow<List<PlayingMusicEntity>>

    @Query("SELECT * FROM PlayingMusicEntity ORDER BY lastPlayingDateTime DESC")
    fun getLastPlayingMusic(): Flow<PlayingMusicEntity?>

    @Query("SELECT * FROM PlayingMusicEntity ORDER BY addedDateTime")
    suspend fun getPlayingMusicList(): List<PlayingMusicEntity>

    @Update
    suspend fun updateMusic(musicEntity: PlayingMusicEntity)
}