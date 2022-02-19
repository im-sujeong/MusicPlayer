package com.sue.musicplayer.ui.playinglist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sue.musicplayer.domain.model.PlayingMusicModel
import com.sue.musicplayer.domain.usecase.UpdatePlayingListUseCase
import com.sue.musicplayer.domain.usecase.playinglist.GetPlayingMusicListUseCase
import com.sue.musicplayer.ui.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlayingListViewModel(
    private val currentPlayingMusic: PlayingMusicModel?,
    private val getPlayingMusicListUseCase: GetPlayingMusicListUseCase,
    private val updatePlayingListUseCase: UpdatePlayingListUseCase
): BaseViewModel() {
    val stateLiveData = MutableLiveData<PlayingListState>()

    private var currentPlayingMusicPosition = -1

    override fun fetchData(): Job = viewModelScope.launch {
        setState(
            PlayingListState.Success(
                getPlayingMusicListUseCase().mapIndexed { index, playingMusicModel ->
                    if( playingMusicModel.id == currentPlayingMusic?.id ) {
                        currentPlayingMusicPosition = index

                        playingMusicModel.copy(
                            isSelected = true,
                            isPlaying = currentPlayingMusic.isPlaying
                        )
                    }else {
                        playingMusicModel
                    }
                }
            )
        )
    }

    fun playMusic(music: PlayingMusicModel) = viewModelScope.launch{
        updatePlayingListUseCase(
            music.copy(
                lastPlayingDateTime = System.currentTimeMillis()
            )
        )
    }

    fun changePlayingMusic(music: PlayingMusicModel) {
        var tempCurrentPlayingMusicPosition = 0

        setState(
            PlayingListState.Success(
                (stateLiveData.value as PlayingListState.Success).playingMusicList.mapIndexed { index, playingMusicModel ->
                    if( playingMusicModel.id == music.id ) {
                        tempCurrentPlayingMusicPosition = index

                        playingMusicModel.copy(
                            isSelected = true,
                            isPlaying = music.isPlaying
                        )
                    }else {
                        if( currentPlayingMusicPosition == index ) {
                            playingMusicModel.copy(
                                isSelected = false,
                                isPlaying = false
                            )
                        }else {
                            playingMusicModel
                        }
                    }
                }
            )
        )

        currentPlayingMusicPosition = tempCurrentPlayingMusicPosition
    }

    private fun setState(state: PlayingListState) {
        stateLiveData.postValue(state)
    }
}