package com.sue.musicplayer.ui.intro

import androidx.lifecycle.viewModelScope
import com.sue.musicplayer.ui.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

internal class IntroViewModel: BaseViewModel() {
    override fun fetchData(): Job = viewModelScope.launch {

    }
}