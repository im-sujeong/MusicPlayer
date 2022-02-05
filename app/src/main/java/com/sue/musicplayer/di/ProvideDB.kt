package com.sue.musicplayer.di

import android.content.Context
import androidx.room.Room
import com.sue.musicplayer.data.db.AppDatabase

internal fun provideDB(context: Context): AppDatabase {
    return Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        AppDatabase.DB_NAME
    ).build()
}

internal fun providePlayingMusicDao(database: AppDatabase) = database.playingMusicDao()