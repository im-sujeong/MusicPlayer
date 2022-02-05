package com.sue.musicplayer.di

import com.sue.musicplayer.data.preference.PreferenceManager
import com.sue.musicplayer.data.repository.MusicRepositoryImpl
import com.sue.musicplayer.data.repository.MusicRepositoryService
import com.sue.musicplayer.data.repository.PlayingMusicRepositoryImpl
import com.sue.musicplayer.data.repository.PlayingMusicRepositoryService
import com.sue.musicplayer.domain.usecase.GetRecommendMusicUseCase
import com.sue.musicplayer.domain.usecase.home.AddPlayingListUseCase
import com.sue.musicplayer.domain.usecase.home.GetReleaseMusicUseCase
import com.sue.musicplayer.domain.usecase.home.GetTopSongsUseCase
import com.sue.musicplayer.domain.usecase.player.GetPlayingMusicListUseCase
import com.sue.musicplayer.ui.home.HomeViewModel
import com.sue.musicplayer.ui.intro.IntroViewModel
import com.sue.musicplayer.ui.main.MainViewModel
import com.sue.musicplayer.ui.player.PlayerViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {
    //Coroutine Dispatcher
    single { Dispatchers.IO }

    //ViewModel
    viewModel { MainViewModel(get()) }
    viewModel { IntroViewModel() }
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel { PlayerViewModel() }

    //Retrofit
    single { buildOkHttpClient() }
    single { provideRetrofitMusic(get()) }
    single { provideMusicApi(get()) }

    //UseCase
    factory { GetRecommendMusicUseCase(get()) }
    factory { GetReleaseMusicUseCase(get()) }
    factory { GetTopSongsUseCase(get()) }
    factory { AddPlayingListUseCase(get()) }
    factory { GetPlayingMusicListUseCase(get()) }

    //Repositories
    single<MusicRepositoryService> { MusicRepositoryImpl(get(), get()) }
    single<PlayingMusicRepositoryService> { PlayingMusicRepositoryImpl(get(), get()) }

    //Database
    single { provideDB(androidApplication()) }
    single { providePlayingMusicDao(get()) }

    //Preference
    single{ PreferenceManager(androidContext()) }
}