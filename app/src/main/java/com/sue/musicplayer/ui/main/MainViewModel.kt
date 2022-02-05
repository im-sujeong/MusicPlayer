package com.sue.musicplayer.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sue.musicplayer.domain.model.PlayingMusicModel
import com.sue.musicplayer.domain.usecase.player.GetPlayingMusicListUseCase
import com.sue.musicplayer.ui.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPlayingMusicListUseCase: GetPlayingMusicListUseCase
): BaseViewModel() {
    val stateLiveData = MutableLiveData<MainState>()
    private var playingMusicList: MutableStateFlow<List<PlayingMusicModel>> = MutableStateFlow(listOf())

    override fun fetchData(): Job = viewModelScope.launch {
        getPlayingMusicListUseCase()
            .onStart {  }
            .onEach { list ->
                playingMusicList.value = list

                setState(
                    MainState.Success(
                        lastPlayingMusic = list.maxByOrNull {
                            it.lastPlayingDateTime
                        },
                        playingMusicList = list
                    )
                )
            }
            .launchIn(this)
    }

    private fun setState(state: MainState) {
        stateLiveData.postValue(state)
    }
}