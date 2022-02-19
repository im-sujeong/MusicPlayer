package com.sue.musicplayer.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.sue.musicplayer.databinding.FragmentHomeBinding
import com.sue.musicplayer.domain.model.MusicModel
import com.sue.musicplayer.extensions.dpToPx
import com.sue.musicplayer.ui.base.BaseFragment
import com.sue.musicplayer.ui.adapter.RecommendMusicAdapter
import com.sue.musicplayer.ui.adapter.ReleaseMusicAdapter
import com.sue.musicplayer.ui.adapter.TopSongsAdapter
import org.koin.android.ext.android.inject

internal class HomeFragment: BaseFragment<HomeViewModel, FragmentHomeBinding>() {
    override val viewModel by inject<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    private lateinit var recommendMusicAdapter: RecommendMusicAdapter
    private lateinit var releaseMusicAdapter: ReleaseMusicAdapter
    private lateinit var topSongsAdapter: TopSongsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() = with(binding){
        recommendMusicAdapter = RecommendMusicAdapter()
        releaseMusicAdapter = ReleaseMusicAdapter {
            addPlayingList(it)
        }
        topSongsAdapter = TopSongsAdapter {
            addPlayingList(it)
        }

        CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(10f.dpToPx()))
            recommendViewPager.setPageTransformer(this)
        }

        recommendViewPager.adapter = recommendMusicAdapter
        recommendViewPager.offscreenPageLimit = 3

        releaseRecyclerView.adapter = releaseMusicAdapter

        topSongRecyclerView.adapter = topSongsAdapter

        moreReleaseButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToReleaseMusicFragment()
            )
        }

        moreTopSongButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToTopSongsFragment()
            )
        }
    }

    override fun observeData() = viewModel.stateLiveData.observe(viewLifecycleOwner){ state ->
        when(state) {
            is HomeState.Error -> { Toast.makeText(requireContext(), state.messageId, Toast.LENGTH_SHORT).show()}
            is HomeState.Success -> { handleSuccess(state) }
        }
    }

    private fun handleSuccess(state: HomeState.Success) {
        recommendMusicAdapter.submitList(state.recommendMusicList)
        releaseMusicAdapter.submitList(state.releaseMusicList)
        topSongsAdapter.submitList(state.musicList)
    }

    private fun addPlayingList(musicModel: MusicModel) {
        viewModel.addPlayingList(musicModel)
    }
}