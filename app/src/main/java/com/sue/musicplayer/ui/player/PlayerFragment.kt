package com.sue.musicplayer.ui.player

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.Player
import com.sue.musicplayer.R
import com.sue.musicplayer.data.mapper.toMediaItem
import com.sue.musicplayer.databinding.FragmentPlayerBinding
import com.sue.musicplayer.domain.model.PlayingMusicModel
import com.sue.musicplayer.extensions.loadImage
import com.sue.musicplayer.ui.base.BaseFragment
import com.sue.musicplayer.ui.main.MainActivity
import com.sue.musicplayer.util.event.PlayingMusicEventBus
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

internal class PlayerFragment: BaseFragment<PlayerViewModel, FragmentPlayerBinding>() {
    companion object {
        const val TAG = "PlayerFragment"
    }

    override val viewModel by inject<PlayerViewModel>()

    override fun getViewBinding(): FragmentPlayerBinding = FragmentPlayerBinding.inflate(layoutInflater)

    private var player: ExoPlayer? = null
    private val updateSeekRunnable = Runnable {
        updateSeek()
    }

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    private val playingMusicEventBus by inject<PlayingMusicEventBus>()

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
                        onBackPressedCallback.isEnabled = false
                    }
                    R.id.end -> {
                        onBackPressedCallback.isEnabled = true
                    }
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) { }
        })

        playerLayout.setOnClickListener {  }

        closeButton.setOnClickListener {
            closePlayer()
        }

        initPlayerView()
        initSeekBar()
        initPlayControlButton()
        initFavoriteButton()
        initShuffleButton()
        initGoToListButton()

        setOnBackPressedDispatcher()
    }

    private fun setOnBackPressedDispatcher() {
        onBackPressedCallback = object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() {
                closePlayer()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    private fun initPlayerView() = with(binding){
        context?.let {
            player = ExoPlayer.Builder(it).build()

            playerView.player = player

            player?.addListener(object : Player.Listener{
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    super.onIsPlayingChanged(isPlaying)

                    changePlaying(isPlaying)
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)

                    if( playbackState == Player.STATE_ENDED ) {
                        viewModel.setNextPlayingMusic()
                    }else {
                        updateSeek()
                    }
                }
            })
        }
    }

    private fun initSeekBar() = with(binding){
        playerSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) { }

            override fun onStartTrackingTouch(seekBar: SeekBar?) { }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                player?.seekTo((seekBar.progress * 1000).toLong())
            }
        })
    }

    private fun initPlayControlButton() = with(binding){
        bottomControlButton.setOnClickListener {
            player?.let { player ->
                if( player.isPlaying ) {
                    player.pause()
                }else {
                    player.play()
                }
            }
        }

        controlButton.setOnClickListener {
            player?.let { player ->
                if( player.isPlaying ) {
                    player.pause()
                }else {
                    player.play()
                }
            }
        }

        bottomNextButton.setOnClickListener {
            viewModel.setNextPlayingMusic()
        }

        nextButton.setOnClickListener {
            viewModel.setNextPlayingMusic()
        }

        previousButton.setOnClickListener {
            viewModel.setPreviousPlayingMusic()
        }

        bottomPreviousButton.setOnClickListener {
            viewModel.setPreviousPlayingMusic()
        }
    }

    private fun initFavoriteButton() = with(binding){
        favoriteButton.setOnClickListener {
            viewModel.changeFavorite()
        }
    }

    private fun initShuffleButton() = with(binding){
        shuffleButton.setOnClickListener {
            viewModel.changeShuffleMode()
        }
    }

    private fun initGoToListButton() = with(binding){
        goToListButton.setOnClickListener {
            playerMotionLayout.transitionToStart()

            goToPlayingList()
        }

        bottomGoToListButton.setOnClickListener {
            goToPlayingList()
        }
    }

    private fun closePlayer() = with(binding){
        playerMotionLayout.transitionToStart()
    }

    private fun goToPlayingList() = with(binding){
        bottomGoToListButton.isGone = true

        (requireActivity() as MainActivity).goToPlayingList(
            viewModel.getCurrentPlayingMusic(
                player?.isPlaying ?: kotlin.run { false }
            )
        )
    }

    fun showGoToListButton() {
        binding.bottomGoToListButton.isVisible = true
    }

    override fun observeData() {
        viewModel.stateLiveData.observe(viewLifecycleOwner){ state ->
            when(state) {
                is PlayerState.Success -> {
                    handleSuccess(state.lastPlayingMusic, state.isChangeFavorite)
                }
            }
        }

        viewModel.shuffleModeLiveData.observe(viewLifecycleOwner){ isShuffleMode ->
            handleShuffleMode(isShuffleMode)
        }
    }

    private fun handleSuccess(lastPlayingMusic: PlayingMusicModel?, isChangeFavorite: Boolean) = with(binding){
        lastPlayingMusic?.let { lastPlayingMusic ->
            if( !isChangeFavorite ) {
                player?.let { player ->
                    player.setMediaItem(lastPlayingMusic.toMediaItem())
                    player.prepare()
                }

                if( System.currentTimeMillis() - lastPlayingMusic.lastPlayingDateTime < 100 ) {
                    playMusic()
                }
            }

            titleTextView.isVisible = true
            bottomArtistNameTextView.isVisible = true

            bottomPreviousButton.isEnabled = true
            bottomNextButton.isEnabled = true
            bottomControlButton.isEnabled = true

            previousButton.isEnabled = true
            nextButton.isEnabled = true
            controlButton.isEnabled = true

            favoriteButton.isEnabled = true
            shuffleButton.isEnabled = true

            playerSeekBar.isEnabled = true

            bottomTitleTextView.text = lastPlayingMusic.title
            bottomArtistNameTextView.text = lastPlayingMusic.artistName

            titleTextView.text = lastPlayingMusic.title
            artistNameTextView.text = lastPlayingMusic.artistName

            coverImageView.loadImage(lastPlayingMusic.coverImageUrl, 8f)

            favoriteButton.setImageResource(
                if(lastPlayingMusic.isFavorite) {
                    R.drawable.ic_favorite_on
                }else {
                    R.drawable.ic_favorite_off
                }
            )
        } ?: kotlin.run {
            coverImageView.setImageResource(R.drawable.ic_no_music)
            coverImageView.scaleType = ImageView.ScaleType.CENTER

            artistNameTextView.setText(R.string.no_playing_music)
            bottomTitleTextView.setText(R.string.no_playing_music)

            titleTextView.isVisible = false
            bottomArtistNameTextView.isVisible = false

            bottomPreviousButton.isEnabled = false
            bottomNextButton.isEnabled = false
            bottomControlButton.isEnabled = false

            previousButton.isEnabled = false
            nextButton.isEnabled = false
            controlButton.isEnabled = false

            favoriteButton.isEnabled = false
            shuffleButton.isEnabled = false

            playerSeekBar.isEnabled = false
        }
    }

    private fun handleShuffleMode(isShuffle: Boolean) = with(binding){
        shuffleButton.setImageResource(
            if( isShuffle ) {
                R.drawable.ic_shuffle_on
            }else {
                R.drawable.ic_shuffle_off
            }
        )
    }

    private fun playMusic() {
        player?.let {
            it.seekTo(0, 0)
            it.play()
        }
    }

    private fun updateSeek() {
        val player = this.player ?: return
        val duration = if(player.duration >= 0) player.duration else 0
        val position = player.currentPosition

        updateSeekUi(duration, position)

        val state = player.playbackState

        view?.let {
            it.removeCallbacks(updateSeekRunnable)

            if (state != Player.STATE_IDLE && state != Player.STATE_ENDED) {
                it.postDelayed(
                    updateSeekRunnable, 1000
                )
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateSeekUi(duration: Long, position: Long) = with(binding){
        playerSeekBar.max = (duration / 1000).toInt()
        playerSeekBar.progress = (position / 1000).toInt()
        bottomSeekBar.max = (duration / 1000).toInt()
        bottomSeekBar.progress = (position / 1000).toInt()

        currentTimeTextView.text = "%02d:%02d".format(
            TimeUnit.MINUTES.convert(position, TimeUnit.MILLISECONDS),
            (position / 1000) % 60
        )

        musicTimeTextView.text = "%02d:%02d".format(
            TimeUnit.MINUTES.convert(duration, TimeUnit.MILLISECONDS),
            (duration / 1000) % 60
        )
    }

    private fun changePlaying(isPlaying: Boolean) = with(binding) {
        if( isPlaying ) {
            bottomControlButton.setImageResource(R.drawable.ic_pause)
            controlButton.setImageResource(R.drawable.ic_pause)
        }else {
            bottomControlButton.setImageResource(R.drawable.ic_play)
            controlButton.setImageResource(R.drawable.ic_play)
        }

        lifecycleScope.launch {
            playingMusicEventBus.changePlayingMusic(
                viewModel.getCurrentPlayingMusic(isPlaying)!!
            )
        }
    }

    fun isClosedPlayer(): Boolean = with(binding){
        return playerMotionLayout.progress == 0.0f
    }

    override fun onDestroy() {
        super.onDestroy()

        player?.release()
        view?.removeCallbacks(updateSeekRunnable)
    }
}