package com.sue.musicplayer.ui.player

import com.sue.musicplayer.domain.model.PlayingMusicModel

sealed class PlayerState {
    data class Success(
        val lastPlayingMusic: PlayingMusicModel?,
        val isChangeFavorite: Boolean
    ): PlayerState()
}