package com.sue.musicplayer.ui.topsongs

import com.sue.musicplayer.domain.model.MusicModel

sealed class TopSongsState {
    data class Success(
        val topSongsList: List<MusicModel>
    ): TopSongsState()
}