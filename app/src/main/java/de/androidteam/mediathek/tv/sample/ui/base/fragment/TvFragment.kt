package de.androidteam.mediathek.tv.sample.ui.base.fragment

import android.view.KeyEvent


interface TvFragment {

    /**
     * Called when this fragment has gained focus
     *
     * This happens when the Drawer is collapsed
     */
    fun onFocused()

    /**
     * Called when a fragment has lost focus
     *
     * This happens when the Drawer is expanded
     */
    fun onUnfocused()

    /**
     * Dispatch focus to this fragment.
     *
     * If [onViewCreated] has been called, [onFocused] and [onUnfocused] will be called.
     *
     */
    fun dispatchFocus(hasFocus: Boolean)

    /**
     * @return true if this fragment (or any of its views) is currently focused
     */
    fun isFocused(): Boolean

    /**
     * Dispatches a KeyEvent to a child of this Fragment that might be interested
     *
     *
     * @return true if event was consumed, false otherwise to let the system handle the key
     */
    fun dispatchKeyEvent(event: KeyEvent): Boolean

    /**
     * @return true if back press was handled, false to let the system handle it
     */
    fun onBackPressed(): Boolean

}
