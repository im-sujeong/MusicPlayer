package com.sue.musicplayer.ui.main

import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.sue.musicplayer.R
import com.sue.musicplayer.databinding.ActivityMainBinding
import com.sue.musicplayer.domain.model.PlayingMusicModel
import com.sue.musicplayer.ui.base.BaseActivity
import com.sue.musicplayer.ui.player.PlayerFragment
import org.koin.android.ext.android.inject

class MainActivity: BaseActivity<MainViewModel, ActivityMainBinding>() {
    override val viewModel by inject<MainViewModel>()

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    private val navigationController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, PlayerFragment())
            .commit()
    }

    override fun initViews() = with(binding) {
        navigationController.addOnDestinationChangedListener { _, destination, arguments ->
            when(destination.id) {

            }
        }
    }

    override fun observeData() = viewModel.stateLiveData.observe(this){ state ->
        when(state) {
            is MainState.Success -> {
                //handleSuccess(state)
            }
        }
    }

    /*private fun handleSuccess(state: MainState.Success) = with(binding){
        if( state.playingMusicList.isNotEmpty() ) {
            titleTextView.text = state.lastPlayingMusic?.title
            artistNameTextView.text = state.lastPlayingMusic?.artistName
            artistNameTextView.isVisible = true

            seekBar.isEnabled = true
            controlButton.isEnabled = true
            previousButton.isEnabled = true
            nextButton.isEnabled = true
            goToListButton.isEnabled = true

            setMusicList(state.playingMusicList)
        }else {
            titleTextView.setText(R.string.no_playing_music)
            artistNameTextView.isGone = true

            seekBar.isEnabled = false
            controlButton.isEnabled = false
            previousButton.isEnabled = false
            nextButton.isEnabled = false
            goToListButton.isEnabled = false
        }
    }

    private fun initPlayerView() = with(binding){
        playerView.player = player

        player.addListener(object : Player.Listener{
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)

                controlButton.setImageResource(
                    if(isPlaying) {
                        R.drawable.ic_play24
                    } else {
                        R.drawable.ic_pause24
                    }
                )
            }

            override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                super.onMediaItemTransition(mediaItem, reason)
            }

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
            }
        })
    }

    private fun setMusicList(playingMusicList: List<PlayingMusicModel>) {
        player.addMediaItems(playingMusicList.map { music ->
            MediaItem
                .Builder()
                .setMediaId(music.id.toString())
                .setUri(music.streamUrl)
                .build()
        })

        player.prepare()

        Log.i("sujeong", "media count : ${player.mediaItemCount}")
    }

    fun playingMusic() {

    }*/
}