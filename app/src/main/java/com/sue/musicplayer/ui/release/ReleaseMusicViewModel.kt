package com.sue.musicplayer.ui.release

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sue.musicplayer.data.mapper.toPlayingMusicModel
import com.sue.musicplayer.domain.model.MusicModel
import com.sue.musicplayer.domain.usecase.UpdatePlayingListUseCase
import com.sue.musicplayer.domain.usecase.home.GetReleaseMusicUseCase
import com.sue.musicplayer.domain.usecase.release.GetAllReleaseMusicUseCase
import com.sue.musicplayer.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ReleaseMusicViewModel(
    private val getAllReleaseMusicUseCase: GetAllReleaseMusicUseCase,
    private val updatePlayingListUseCase: UpdatePlayingListUseCase
): BaseViewModel() {
    val stateLiveData = MutableLiveData<ReleaseMusicState>()

    override fun fetchData() = viewModelScope.launch {
        setState(
            ReleaseMusicState.Success(getAllReleaseMusicUseCase())
        )
    }

    fun addPlayingList(musicModel: MusicModel) = viewModelScope.launch{
        updatePlayingListUseCase(
            musicModel.toPlayingMusicModel(
                addedDateTime = System.currentTimeMillis(),
                lastPlayingDateTime = System.currentTimeMillis()
            )
        )
    }

    private fun setState(state: ReleaseMusicState) {
        stateLiveData.postValue(state)
    }
}