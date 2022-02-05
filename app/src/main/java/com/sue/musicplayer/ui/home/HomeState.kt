package com.sue.musicplayer.ui.home

import androidx.annotation.StringRes
import com.sue.musicplayer.domain.model.RecommendMusicModel
import com.sue.musicplayer.domain.model.MusicModel

sealed class HomeState {
    data class Success(
        val recommendMusicList: List<RecommendMusicModel>,
        val releaseMusicList: List<MusicModel>,
        val musicList: List<MusicModel>
    ): HomeState()

    data class Error(
        @StringRes val messageId: Int
    ): HomeState()
}
