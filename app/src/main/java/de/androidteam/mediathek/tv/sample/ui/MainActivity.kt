package de.androidteam.mediathek.tv.sample.ui

import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import de.androidteam.mediathek.tv.sample.R


class MainActivity : AppCompatActivity() {

    private lateinit var navigationManager: NavigationManager
    private lateinit var navigationDrawer: NavigationDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigationDrawer = NavigationDrawer(findViewById(R.id.navigationView))
        navigationManager = NavigationManager(
            supportFragmentManager,
            navigationDrawer,
            findViewById(R.id.fragmentContainer)
        )
        if (savedInstanceState == null) {
            navigationManager.openDefaultScreen()
        } else {
            navigationManager.restoreState(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        navigationManager.saveState(outState)
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        val handled = navigationManager.dispatchKeyEvent(event)
        if (!handled) {
            return super.dispatchKeyEvent(event)
        } else {
            return true
        }
    }

    override fun onBackPressed() {
        if (!navigationManager.onBackPressed()) {
            super.onBackPressed()
        }
    }
}
