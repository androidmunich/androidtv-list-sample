package de.androidteam.mediathek.tv.sample.ui.base.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


abstract class TvBaseFragment : Fragment(), TvFragment {

    private var hasFocus: Boolean = false

    abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutId = getLayoutId()
        return if (layoutId != 0) {
            inflater.inflate(layoutId, container, false)
        } else {
            null
        }
    }

    override fun isFocused(): Boolean = hasFocus

    override fun onFocused() {
        hasFocus = true
    }

    override fun onUnfocused() {
        hasFocus = false
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return false
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    /**
     * Dispatch focus to this fragment.
     *
     * If [onViewCreated] has been called, [onFocused] and [onUnfocused] will be called.
     *
     * Otherwise, [hasFocus] is just set.
     */
    override fun dispatchFocus(hasFocus: Boolean) {
        if (view == null) {
            this.hasFocus = hasFocus
            return
        } else if (hasFocus) {
            onFocused()
        } else {
            onUnfocused()
        }
    }

}
