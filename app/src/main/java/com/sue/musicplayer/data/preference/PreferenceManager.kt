package com.sue.musicplayer.data.preference

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(
    private val context: Context
) {
    companion object {
        const val PREFERENCE_NAME = "MusicPlayerSP"

        const val KEY_LAST_PLAYING_MUSIC_ID = "KEY_LAST_PLAYING_MUSIC_ID"
    }

    private fun getPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    private val prefs by lazy { getPreferences(context) }

    private val editor by lazy { prefs.edit() }

    private fun putLong(key: String, value: Long) {
        editor.putLong(key, value)
        editor.apply()
    }

    private fun getLong(key: String): Long{
        return prefs.getLong(key, -1)
    }
}