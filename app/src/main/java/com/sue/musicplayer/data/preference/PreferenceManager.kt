package com.sue.musicplayer.data.preference

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(
    private val context: Context
) {
    companion object {
        const val PREFERENCE_NAME = "MusicPlayerSP"

        const val KEY_SHUFFLE_MODE = "KEY_SHUFFLE_MODE"
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

    private fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    private fun getBoolean(key: String, default: Boolean): Boolean {
        return prefs.getBoolean(key, default)
    }

    fun putShuffleMode(isShuffle: Boolean) {
        putBoolean(KEY_SHUFFLE_MODE, isShuffle)
    }

    fun isShuffleMode(): Boolean {
        return getBoolean(KEY_SHUFFLE_MODE, false)
    }
}