package com.sue.musicplayer.ui.player

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.sue.musicplayer.R
import com.sue.musicplayer.databinding.FragmentPlayerBinding
import com.sue.musicplayer.ui.base.BaseFragment
import org.koin.android.ext.android.inject

internal class PlayerFragment: BaseFragment<PlayerViewModel, FragmentPlayerBinding>() {
    override val viewModel by inject<PlayerViewModel>()

    override fun getViewBinding(): FragmentPlayerBinding = FragmentPlayerBinding.inflate(layoutInflater)

    private var player: ExoPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() = with(binding){
        playerMotionLayout.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) { }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) { }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                when(currentId) {
                    R.id.start -> {
                        bottomSeekBar.isEnabled = true
                    }
                    R.id.end -> { }
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) { }
        })

        closeButton.setOnClickListener {
            playerMotionLayout.transitionToStart()
        }

        initPlayerView()
    }

    private fun initPlayerView() = with(binding){
        context?.let {
            player = ExoPlayer.Builder(it).build()

            playerView.player = player

            player?.addListener(object : Player.Listener{
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)
                }

                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    super.onMediaItemTransition(mediaItem, reason)
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                }
            })
        }
    }

    override fun observeData() {

    }
}