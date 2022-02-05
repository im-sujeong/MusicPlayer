package com.sue.musicplayer.di

import android.util.Log
import com.sue.musicplayer.BuildConfig
import com.sue.musicplayer.data.network.MusicService
import com.sue.musicplayer.data.url.Url
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal fun provideMusicApi(retrofit: Retrofit): MusicService {
    return retrofit.create(MusicService::class.java)
}

internal fun provideRetrofitMusic(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(Url.MUSIC_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

internal fun buildOkHttpClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor { message -> Log.e("sujeong", message) }.apply {
        level = if(BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        }else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    return OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()
}