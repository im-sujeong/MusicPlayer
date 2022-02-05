package com.sue.musicplayer.extensions

import android.content.res.Resources

internal fun Float.dpToPx() : Int {
    return (this * Resources.getSystem().displayMetrics.density).toInt()
}