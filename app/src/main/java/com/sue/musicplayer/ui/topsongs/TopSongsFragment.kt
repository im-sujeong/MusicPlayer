package com.sue.musicplayer.ui.topsongs

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import com.sue.musicplayer.databinding.FragmentTopSongsBinding
import com.sue.musicplayer.domain.model.MusicModel
import com.sue.musicplayer.ui.adapter.TopSongsAdapter
import com.sue.musicplayer.ui.base.BaseFragment
import org.koin.android.ext.android.inject

internal class TopSongsFragment: BaseFragment<TopSongsViewModel, FragmentTopSongsBinding>() {
    override val viewModel by inject<TopSongsViewModel>()

    override fun getViewBinding(): FragmentTopSongsBinding = FragmentTopSongsBinding.inflate(layoutInflater)

    private lateinit var topSongsAdapter: TopSongsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() = with(binding){
        topSongsAdapter = TopSongsAdapter {
            viewModel.addPlayingList(it)
        }

        topSongsRecyclerView.adapter = topSongsAdapter
    }

    override fun observeData() = viewModel.stateLiveData.observe(viewLifecycleOwner){ state ->
        when(state) {
            is TopSongsState.Success -> { handleSuccess(state.topSongsList) }
        }
    }

    private fun handleSuccess(topSongsList: List<MusicModel>) = with(binding){
        progressBar.isGone = true

        topSongsAdapter.submitList(topSongsList)
    }
}