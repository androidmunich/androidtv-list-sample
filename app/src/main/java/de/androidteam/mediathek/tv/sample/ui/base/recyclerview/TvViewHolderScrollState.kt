package de.androidteam.mediathek.tv.sample.ui.base.recyclerview

import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView

/**
 * Persists scroll state for nested RecyclerViews.
 *
 * 1. Call [save] in [TvListVH.onPreBound]
 * and when the view is detached from the window to save the scroll position.
 *
 * 2. Call [restore] in [TvListVH.onBound]
 * after changing the adapter contents to restore the scroll position
 */
class TvViewHolderScrollState {

    private val scrollState = linkedMapOf<String, Parcelable?>()

    fun clear() {
        scrollState.clear()
    }

    /**
     * Saves this RecyclerView layout state for a given key
     */
    fun save(key: String?, recyclerView: RecyclerView) {
        if (key == null) {
            return
        }
        val layoutManager = recyclerView.layoutManager
        if (layoutManager == null) {
            scrollState.remove(key)
            return
        }
        scrollState[key] = layoutManager.onSaveInstanceState()
    }

    /**
     * Restores this RecyclerView layout state for a given key
     */
    fun restore(key: String?, recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager
        if (key == null) {
            layoutManager?.scrollToPosition(0)
            return
        }
        if (layoutManager == null) {
            scrollState.remove(key)
            return
        }
        val savedState = scrollState[key]
        if (savedState != null) {
            layoutManager.onRestoreInstanceState(savedState)
        } else {
            layoutManager.scrollToPosition(0)
        }
    }

}
