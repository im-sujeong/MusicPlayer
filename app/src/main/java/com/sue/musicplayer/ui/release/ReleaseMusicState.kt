package com.sue.musicplayer.ui.release

import com.sue.musicplayer.domain.model.MusicModel

sealed class ReleaseMusicState {
    data class Success(
        val releaseMusicList: List<MusicModel>
    ): ReleaseMusicState()
}