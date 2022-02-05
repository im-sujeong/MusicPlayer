package com.sue.musicplayer.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sue.musicplayer.data.db.dao.PlayingMusicDao
import com.sue.musicplayer.data.entity.PlayingMusicEntity

@Database(entities = [PlayingMusicEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun playingMusicDao(): PlayingMusicDao

    companion object {
        const val DB_NAME = "musicPlayerDB.db"
    }
}