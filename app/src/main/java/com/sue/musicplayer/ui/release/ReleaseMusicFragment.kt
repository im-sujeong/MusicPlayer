package com.sue.musicplayer.ui.release

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import com.sue.musicplayer.databinding.FragmentReleaseMusicBinding
import com.sue.musicplayer.domain.model.MusicModel
import com.sue.musicplayer.ui.adapter.ReleaseMusicLinearAdapter
import com.sue.musicplayer.ui.base.BaseFragment
import org.koin.android.ext.android.inject

internal class ReleaseMusicFragment: BaseFragment<ReleaseMusicViewModel, FragmentReleaseMusicBinding>(){
    override val viewModel by inject<ReleaseMusicViewModel>()

    override fun getViewBinding(): FragmentReleaseMusicBinding = FragmentReleaseMusicBinding.inflate(layoutInflater)

    private lateinit var releaseMusicAdapter: ReleaseMusicLinearAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() = with(binding) {
        releaseMusicAdapter = ReleaseMusicLinearAdapter {
            viewModel.addPlayingList(it)
        }

        releaseMusicRecyclerView.adapter = releaseMusicAdapter
    }

    override fun observeData() = viewModel.stateLiveData.observe(viewLifecycleOwner) { state ->
        when(state) {
            is ReleaseMusicState.Success -> handleSuccess(state.releaseMusicList)
        }
    }

    private fun handleSuccess(releaseMusicList: List<MusicModel>) = with(binding) {
        progressBar.isGone = true

        releaseMusicAdapter.submitList(releaseMusicList)
    }
}