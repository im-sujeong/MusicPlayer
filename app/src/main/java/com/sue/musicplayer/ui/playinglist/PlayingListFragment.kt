package com.sue.musicplayer.ui.playinglist

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.sue.musicplayer.databinding.FragmentPlayingListBinding
import com.sue.musicplayer.domain.model.PlayingMusicModel
import com.sue.musicplayer.ui.adapter.PlayingListAdapter
import com.sue.musicplayer.ui.base.BaseFragment
import com.sue.musicplayer.util.event.PlayingMusicEventBus
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

internal class PlayingListFragment: BaseFragment<PlayingListViewModel, FragmentPlayingListBinding>() {
    private val argument: PlayingListFragmentArgs by navArgs()

    override val viewModel by inject<PlayingListViewModel>{
        parametersOf(argument.currentPlayingMusic)
    }

    override fun getViewBinding(): FragmentPlayingListBinding = FragmentPlayingListBinding.inflate(layoutInflater)

    private val playingMusicEventBus by inject<PlayingMusicEventBus>()

    private lateinit var playingListAdapter: PlayingListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() = with(binding){
        playingListAdapter = PlayingListAdapter {
            viewModel.playMusic(it)
        }

        playingListRecyclerView.itemAnimator = null
        playingListRecyclerView.adapter = playingListAdapter
    }

    override fun observeData() {
        viewModel.stateLiveData.observe(viewLifecycleOwner){ state ->
            when(state) {
                is PlayingListState.Success -> { handleSuccess(state.playingMusicList) }
            }
        }

        lifecycleScope.launch {
            playingMusicEventBus.playingMusicFlow.collect {
                viewModel.changePlayingMusic(it)
            }
        }
    }

    private fun handleSuccess(playingMusicList: List<PlayingMusicModel>) = with(binding){
        if( playingMusicList.isEmpty() ) {
            emptyTextView.isVisible = true
        }else {
            emptyTextView.isGone = true

            playingListAdapter.submitList(playingMusicList)
        }
    }
}