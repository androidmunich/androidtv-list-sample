package de.androidteam.mediathek.tv.sample.ui.base.recyclerview

import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import androidx.leanback.widget.BaseGridView
import androidx.recyclerview.widget.RecyclerView

/**
 * A ViewHolder that holds a list of items.
 *
 * Scroll state is persisted using [TvViewHolderScrollState]
 */
abstract class TvListVH(
    private val view: View,
    private val scrollState: TvViewHolderScrollState
) : RecyclerView.ViewHolder(view), TvViewHolder {

    companion object {
        const val TAG = "TvListVH"
    }

    protected lateinit var tvRecyclerView: TvRecyclerView
    private var isFocused = false

    abstract fun getScrollKey(): String?

    abstract fun getRecyclerView(): BaseGridView

    override fun onCreated() {
        if (view is ViewGroup) {
            view.clipChildren = false
            view.clipToPadding = false
        }
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                getRecyclerView().requestFocus()
                onFocused()
            }
        }
        tvRecyclerView = getRecyclerView() as TvRecyclerView
        view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(v: View?) {
                scrollState.save(getScrollKey(), getRecyclerView())
            }

            override fun onViewAttachedToWindow(v: View?) {
            }
        })
        applyFocusUiState(false)
    }

    override fun onPreBound() {
        scrollState.save(getScrollKey(), getRecyclerView())
        applyFocusUiState(false)
    }

    override fun onBound() {
        scrollState.restore(getScrollKey(), getRecyclerView())
    }

    override fun onRecycled() {
        // No-op
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return tvRecyclerView.dispatchKeyEvent(
            event,
            consumeEdgeLeft = false,
            consumeEdgeTop = false,
            consumeEdgeRight = true, // Consume the event if we've reached the end of the list
            consumeEdgeBottom = false
        )
    }

    override fun onFocused() {
        Log.d(TAG, "Nested list at $adapterPosition gained focus")
        applyFocusUiState(true)
        isFocused = true
    }

    override fun onUnfocused() {
        Log.d(TAG, "Nested list at $adapterPosition lost focus")
        applyFocusUiState(false)
        isFocused = false
    }

    override fun hasFocus(): Boolean {
        return isFocused
    }

    open fun applyFocusUiState(hasFocus: Boolean) {
        getRecyclerView().alpha = if (hasFocus) 1.0f else 0.15f
    }

}
