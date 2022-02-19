package com.sue.musicplayer.ui.topsongs

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sue.musicplayer.data.mapper.toPlayingMusicModel
import com.sue.musicplayer.domain.model.MusicModel
import com.sue.musicplayer.domain.usecase.UpdatePlayingListUseCase
import com.sue.musicplayer.domain.usecase.topsongs.GetAllTopSongsUseCase
import com.sue.musicplayer.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class TopSongsViewModel(
    private val getAllTopSongsUseCase: GetAllTopSongsUseCase,
    private val updatePlayingListUseCase: UpdatePlayingListUseCase
): BaseViewModel() {
    val stateLiveData = MutableLiveData<TopSongsState>()

    override fun fetchData() = viewModelScope.launch {
        setState(TopSongsState.Success(getAllTopSongsUseCase()))
    }

    fun addPlayingList(musicModel: MusicModel) = viewModelScope.launch{
        updatePlayingListUseCase(
            musicModel.toPlayingMusicModel(
                addedDateTime = System.currentTimeMillis(),
                lastPlayingDateTime = System.currentTimeMillis()
            )
        )
    }

    private fun setState(state: TopSongsState) {
        stateLiveData.postValue(state)
    }
}