package com.sue.musicplayer.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.sue.musicplayer.MainNavDirections
import com.sue.musicplayer.R
import com.sue.musicplayer.databinding.ActivityMainBinding
import com.sue.musicplayer.domain.model.PlayingMusicModel
import com.sue.musicplayer.ui.base.BaseActivity
import com.sue.musicplayer.ui.player.PlayerFragment
import org.koin.android.ext.android.inject

class MainActivity: BaseActivity<MainViewModel, ActivityMainBinding>() {
    override val viewModel by inject<MainViewModel>()

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    private val navigationController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    private var backPressedTime: Long = 0

    override fun onSupportNavigateUp(): Boolean {
        return navigationController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, PlayerFragment(), PlayerFragment.TAG)
            .commit()
    }

    override fun initViews() = with(binding) {
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setupActionBarWithNavController(navigationController)

        navigationController.addOnDestinationChangedListener { _, destination, arguments ->
            when(destination.id) {
                R.id.homeFragment -> {
                    toolBar.isGone = true

                    supportFragmentManager.findFragmentByTag(PlayerFragment.TAG)?.let {
                        (it as PlayerFragment).showGoToListButton()
                    }
                }

                R.id.playingListFragment -> {
                    setActionBar(R.string.playing_list)
                }

                R.id.topSongsFragment -> {
                    setActionBar(R.string.top_songs)
                }

                R.id.releaseMusicFragment -> {
                    setActionBar(R.string.new_release)
                }
            }
        }
    }

    private fun setActionBar(@StringRes title: Int) = with(binding){
        toolBar.isVisible = true

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        titleTextView.setText(title)
    }

    override fun observeData() { }

    fun goToPlayingList(currentPlayingMusic: PlayingMusicModel?) {
        navigationController.currentDestination?.let { destination ->
            if(destination.id != R.id.playingListFragment) {
                navigationController.navigate(MainNavDirections.actionGoToPlayingListFragment(currentPlayingMusic))
            }
        }
    }

    override fun onBackPressed() {
        if( isPossibleFinishApp() ) {
            finishApp()
        }else {
            super.onBackPressed()
        }
    }

    private fun isPossibleFinishApp() : Boolean {
        return navigationController.currentDestination?.id == R.id.homeFragment &&
                (supportFragmentManager.findFragmentByTag(PlayerFragment.TAG) as PlayerFragment).isClosedPlayer()
    }

    private fun finishApp() {
        if( (System.currentTimeMillis() - backPressedTime) < 1000 ) {
            finish()
        }else {
            Toast.makeText(this, R.string.press_back_key_one_more, Toast.LENGTH_SHORT).show()

            backPressedTime = System.currentTimeMillis()
        }
    }
}