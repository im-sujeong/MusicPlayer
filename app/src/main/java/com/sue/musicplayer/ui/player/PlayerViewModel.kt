package com.sue.musicplayer.ui.player

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sue.musicplayer.data.preference.PreferenceManager
import com.sue.musicplayer.domain.model.PlayingMusicModel
import com.sue.musicplayer.domain.usecase.player.UpdateFavoriteMusicUseCase
import com.sue.musicplayer.domain.usecase.UpdatePlayingListUseCase
import com.sue.musicplayer.domain.usecase.player.GetLastPlayingMusicUseCase
import com.sue.musicplayer.domain.usecase.playinglist.GetPlayingMusicListUseCase
import com.sue.musicplayer.ui.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.*

internal class PlayerViewModel(
    private val preferenceManager: PreferenceManager,
    private val getPlayingMusicListUseCase: GetPlayingMusicListUseCase,
    private val getLastPlayingMusicUseCase: GetLastPlayingMusicUseCase,
    private val updatePlayingListUseCase: UpdatePlayingListUseCase,
    private val updateFavoriteMusicUseCase: UpdateFavoriteMusicUseCase
): BaseViewModel() {
    val stateLiveData = MutableLiveData<PlayerState>()
    val shuffleModeLiveData = MutableLiveData<Boolean>()

    private var lastPlayingMusic: MutableStateFlow<PlayingMusicModel?> = MutableStateFlow(null)
    private lateinit var playingMusicList: MutableList<PlayingMusicModel>

    private var currentPlayingPosition = -1

    private var isChangeFavorite = false

    override fun fetchData(): Job = viewModelScope.launch {
        playingMusicList = if(preferenceManager.isShuffleMode()) {
            (getPlayingMusicListUseCase().shuffled()) as MutableList<PlayingMusicModel>
        }else {
            getPlayingMusicListUseCase() as MutableList<PlayingMusicModel>
        }

        getLastPlayingMusicUseCase()
            .onStart {  }
            .onEach { music ->
                music?.let {
                    lastPlayingMusic.value = music

                    playingMusicList.find {
                        it.id == music.id
                    }?.let {
                        playingMusicList.set(playingMusicList.indexOf(it), music)
                    }?: kotlin.run {
                        if( preferenceManager.isShuffleMode() ) {
                            playingMusicList.add(Random().nextInt(playingMusicList.size), music)
                        }else {
                            playingMusicList.add(music)
                        }
                    }
                }

                setState(
                    PlayerState.Success(
                        lastPlayingMusic = music,
                        isChangeFavorite = isChangeFavorite
                    )
                )

                if( isChangeFavorite ) {
                    isChangeFavorite = false
                }

                setCurrentPlayingMusicPosition(music)
            }
            .launchIn(this)

        shuffleModeLiveData.value = preferenceManager.isShuffleMode()
    }

    private fun setCurrentPlayingMusicPosition(lastPlayingMusic: PlayingMusicModel?) {
        lastPlayingMusic?.let { music ->
            currentPlayingPosition = playingMusicList.indexOf(
                playingMusicList.find {
                    it.id == music.id
                }
            )
        }
    }

    fun setNextPlayingMusic() = viewModelScope.launch{
        val nextPosition = if( currentPlayingPosition == playingMusicList.size - 1 ) {
            0
        }else {
            currentPlayingPosition + 1
        }

        updatePlayingListUseCase(
            playingMusicList[nextPosition].copy(
                lastPlayingDateTime = System.currentTimeMillis()
            )
        )
    }

    fun setPreviousPlayingMusic() = viewModelScope.launch{
        val previousPosition = if( currentPlayingPosition == 0 ) {
            playingMusicList.size - 1
        }else {
            currentPlayingPosition - 1
        }

        updatePlayingListUseCase(
            playingMusicList[previousPosition].copy(
                lastPlayingDateTime = System.currentTimeMillis()
            )
        )
    }

    fun changeFavorite() = viewModelScope.launch{
        isChangeFavorite = true

        updateFavoriteMusicUseCase(
            playingMusicList[currentPlayingPosition].copy(
                isFavorite = !playingMusicList[currentPlayingPosition].isFavorite
            )
        )
    }

    fun changeShuffleMode() {
        val isShuffle = !(shuffleModeLiveData.value as Boolean)

        shuffleModeLiveData.value = isShuffle
        preferenceManager.putShuffleMode(isShuffle)

        if( isShuffle ) {
            playingMusicList.shuffle()
        }else {
            playingMusicList.sortBy {
                it.addedDateTime
            }
        }

        setCurrentPlayingMusicPosition(lastPlayingMusic.value)
    }

    fun getCurrentPlayingMusic(isPlaying: Boolean) : PlayingMusicModel? {
        return if(playingMusicList.isEmpty()) {
            null
        }else {
            playingMusicList[currentPlayingPosition].copy(
                isSelected = true,
                isPlaying = isPlaying
            )
        }
    }

    private fun setState(state: PlayerState) {
        stateLiveData.postValue(state)
    }
}
