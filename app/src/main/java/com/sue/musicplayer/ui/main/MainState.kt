package com.sue.musicplayer.ui.main

import com.sue.musicplayer.domain.model.PlayingMusicModel

sealed class MainState {
    data class Success(
        val lastPlayingMusic: PlayingMusicModel?,
        val playingMusicList: List<PlayingMusicModel>
    ): MainState()
}