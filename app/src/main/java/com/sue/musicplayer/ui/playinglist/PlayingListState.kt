package com.sue.musicplayer.ui.playinglist

import com.sue.musicplayer.domain.model.PlayingMusicModel

sealed class PlayingListState() {
    data class Success(
        val playingMusicList: List<PlayingMusicModel>
    ): PlayingListState()
}
