package com.sue.musicplayer.di

import com.sue.musicplayer.data.preference.PreferenceManager
import com.sue.musicplayer.data.repository.MusicRepositoryImpl
import com.sue.musicplayer.domain.repository.MusicRepositoryService
import com.sue.musicplayer.data.repository.PlayingMusicRepositoryImpl
import com.sue.musicplayer.domain.repository.PlayingMusicRepositoryService
import com.sue.musicplayer.domain.model.PlayingMusicModel
import com.sue.musicplayer.domain.usecase.player.UpdateFavoriteMusicUseCase
import com.sue.musicplayer.domain.usecase.UpdatePlayingListUseCase
import com.sue.musicplayer.domain.usecase.home.GetRecommendMusicUseCase
import com.sue.musicplayer.domain.usecase.home.GetReleaseMusicUseCase
import com.sue.musicplayer.domain.usecase.home.GetTopSongsUseCase
import com.sue.musicplayer.domain.usecase.player.GetLastPlayingMusicUseCase
import com.sue.musicplayer.domain.usecase.playinglist.GetPlayingMusicListUseCase
import com.sue.musicplayer.domain.usecase.release.GetAllReleaseMusicUseCase
import com.sue.musicplayer.domain.usecase.topsongs.GetAllTopSongsUseCase
import com.sue.musicplayer.ui.home.HomeViewModel
import com.sue.musicplayer.ui.intro.IntroViewModel
import com.sue.musicplayer.ui.main.MainViewModel
import com.sue.musicplayer.ui.player.PlayerViewModel
import com.sue.musicplayer.ui.playinglist.PlayingListViewModel
import com.sue.musicplayer.ui.release.ReleaseMusicViewModel
import com.sue.musicplayer.ui.topsongs.TopSongsViewModel
import com.sue.musicplayer.util.event.PlayingMusicEventBus
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {
    //Coroutine Dispatcher
    single { Dispatchers.IO }

    //ViewModel
    viewModel { MainViewModel() }
    viewModel { IntroViewModel() }
    viewModel { HomeViewModel(get(), get(), get(), get()) }
    viewModel { PlayerViewModel(get(), get(), get(), get(), get()) }
    viewModel { (currentPlayingMusic: PlayingMusicModel?) -> PlayingListViewModel(currentPlayingMusic, get(), get()) }
    viewModel { TopSongsViewModel(get(), get()) }
    viewModel { ReleaseMusicViewModel(get(), get()) }

    //Retrofit
    single { buildOkHttpClient() }
    single { provideRetrofitMusic(get()) }
    single { provideMusicApi(get()) }

    //UseCase
    factory { GetRecommendMusicUseCase(get()) }
    factory { GetReleaseMusicUseCase(get()) }
    factory { GetTopSongsUseCase(get()) }
    factory { UpdatePlayingListUseCase(get()) }
    factory { GetLastPlayingMusicUseCase(get()) }
    factory { UpdateFavoriteMusicUseCase(get()) }
    factory { GetPlayingMusicListUseCase(get()) }
    factory { GetAllTopSongsUseCase(get()) }
    factory { GetAllReleaseMusicUseCase(get()) }

    //Repositories
    single<MusicRepositoryService> { MusicRepositoryImpl(get(), get()) }
    single<PlayingMusicRepositoryService> { PlayingMusicRepositoryImpl(get(), get()) }

    //Database
    single { provideDB(androidApplication()) }
    single { providePlayingMusicDao(get()) }

    //Preference
    single{ PreferenceManager(androidContext()) }

    //Event
    single { PlayingMusicEventBus() }
}