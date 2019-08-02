package de.androidteam.mediathek.tv.sample.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import de.androidteam.mediathek.tv.sample.R
import de.androidteam.mediathek.tv.sample.ui.base.fragment.TvFragment
import de.androidteam.mediathek.tv.sample.ui.base.isBeingPressed
import de.androidteam.mediathek.tv.sample.ui.base.isLeft
import de.androidteam.mediathek.tv.sample.ui.base.isRight
import de.androidteam.mediathek.tv.sample.ui.video.VideoListFragment

/**
 * Handles navigation inside the app.
 *
 * Shows the navigation drawer when needed and sends key events to the current active fragment
 */
class NavigationManager(
    private val fragmentManager: FragmentManager,
    private val navigationDrawer: NavigationDrawer,
    private val fragmentContainer: ViewGroup,
    private val openDrawerOnBackPress: Boolean = false
) {

    companion object {
        const val STATE_CURRENT_FRAGMENT_TAG = "current_fragment_tag"
    }

    private lateinit var currentFragmentTag: String
    private var currentFragment: Fragment? = null

    fun openDefaultScreen() {
        currentFragment = VideoListFragment()
        fragmentManager.beginTransaction()
            .add(fragmentContainer.id, currentFragment!!, VideoListFragment.TAG)
            .commitNow()
        navigationDrawer.selectItemAt(0, requestFocus = false)
        currentFragmentTag = VideoListFragment.TAG
        if (currentFragment is TvFragment) {
            (currentFragment as TvFragment).dispatchFocus(true)
        }
    }

    fun saveState(savedState: Bundle) {
        savedState.putString(STATE_CURRENT_FRAGMENT_TAG, currentFragmentTag)
    }

    fun restoreState(savedState: Bundle) {
        currentFragmentTag = savedState.getString(STATE_CURRENT_FRAGMENT_TAG) ?: ""
        currentFragment = fragmentManager.findFragmentByTag(currentFragmentTag)
    }

    fun onBackPressed(): Boolean {

        // Check if the current fragment is interested in the back press event
        val tvFragment = currentFragment
        if (tvFragment is TvFragment && tvFragment.onBackPressed()) {
            return true
        }

        // Some apps open the navigation drawer on back press
        if (openDrawerOnBackPress && !navigationDrawer.isExpanded && navigationDrawer.isShowing) {
            expandDrawer(true)
            return true
        }

        return false
    }

    fun dispatchKeyEvent(event: KeyEvent): Boolean {

        // Check if the current fragment can handle this key event
        val tvFragment = currentFragment
        if (tvFragment is TvFragment && tvFragment.isFocused() && tvFragment.dispatchKeyEvent(event)) {
            return true
        }

        // The fragment didn't handle this event.
        // Check if we're interested in opening the drawer.
        if (event.isLeft()) {
            // Don't call expand twice due to ACTION_UP
            if (event.isBeingPressed()) {
                expandDrawer(true)
            }
            return true
        }

        // Check if we're navigating inside the drawer
        if (navigationDrawer.isExpanded && navigationDrawer.dispatchKeyEvent(event)) {
            return true
        }

        // Check if we're closing the drawer
        if (event.isRight()) {
            // Don't call expand twice due to ACTION_UP
            if (event.isBeingPressed()) {
                expandDrawer(false)
            }
            return true
        }

        // Let the system handle this key
        return false
    }

    private fun expandDrawer(expand: Boolean) {
        navigationDrawer.expand(expand)
        val translation = navigationDrawer.navigationView.resources
            .getDimensionPixelOffset(R.dimen.navigation_view_expand_translation)
        fragmentContainer.animate().translationX(if (expand) -translation.toFloat() else 0f)
        focusCurrentFragment(!expand)
    }

    private fun focusCurrentFragment(focus: Boolean) {
        val tvFragment = currentFragment
        if (tvFragment is TvFragment && tvFragment.isFocused() != focus) {
            tvFragment.dispatchFocus(focus)
        }
    }

}
