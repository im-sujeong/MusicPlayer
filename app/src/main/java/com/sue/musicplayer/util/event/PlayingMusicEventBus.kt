package com.sue.musicplayer.util.event

import com.sue.musicplayer.domain.model.PlayingMusicModel
import kotlinx.coroutines.flow.MutableSharedFlow

class PlayingMusicEventBus {
    val playingMusicFlow = MutableSharedFlow<PlayingMusicModel>()

    suspend fun changePlayingMusic(music: PlayingMusicModel) {
        playingMusicFlow.emit(music)
    }
}