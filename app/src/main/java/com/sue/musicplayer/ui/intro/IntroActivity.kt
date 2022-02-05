package com.sue.musicplayer.ui.intro

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.sue.musicplayer.databinding.ActivityIntroBinding
import com.sue.musicplayer.ui.base.BaseActivity
import com.sue.musicplayer.ui.main.MainActivity
import org.koin.android.ext.android.inject

internal class IntroActivity : BaseActivity<IntroViewModel, ActivityIntroBinding>() {
    override val viewModel by inject<IntroViewModel>()

    override fun getViewBinding(): ActivityIntroBinding = ActivityIntroBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).apply {
            postDelayed(
                {
                    goToHome()
                }, 1000
            )
        }
    }

    private fun goToHome() {
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }

    override fun observeData() { }
}