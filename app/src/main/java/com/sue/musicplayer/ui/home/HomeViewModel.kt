package com.sue.musicplayer.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sue.musicplayer.data.mapper.toPlayingMusicModel
import com.sue.musicplayer.domain.model.MusicModel
import com.sue.musicplayer.domain.usecase.GetRecommendMusicUseCase
import com.sue.musicplayer.domain.usecase.home.AddPlayingListUseCase
import com.sue.musicplayer.domain.usecase.home.GetReleaseMusicUseCase
import com.sue.musicplayer.domain.usecase.home.GetTopSongsUseCase
import com.sue.musicplayer.ui.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val getRecommendMusicUseCase: GetRecommendMusicUseCase,
    private val getReleaseMusicUseCase: GetReleaseMusicUseCase,
    private val getTopSongsUseCase: GetTopSongsUseCase,
    private val addPlayingListUseCase: AddPlayingListUseCase
): BaseViewModel() {
    val stateLiveData = MutableLiveData<HomeState>()

    override fun fetchData(): Job = viewModelScope.launch{
        setState(
            HomeState.Success(
                getRecommendMusicUseCase(),
                getReleaseMusicUseCase(),
                getTopSongsUseCase()
            )
        )
    }

    fun addPlayingList(musicModel: MusicModel) = viewModelScope.launch{
        addPlayingListUseCase(
            musicModel.toPlayingMusicModel(
                addedDateTime = System.currentTimeMillis(),
                lastPlayingDateTime = System.currentTimeMillis(),
                false
            )
        )
    }

    private fun setState(state: HomeState) {
        stateLiveData.postValue(state)
    }
}