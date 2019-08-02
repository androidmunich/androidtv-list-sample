package de.androidteam.mediathek.tv.sample.ui.base.recyclerview

import android.view.KeyEvent

interface TvViewHolder {

    /**
     * Called after [androidx.recyclerview.widget.RecyclerView.Adapter.onCreateViewHolder]
     */
    fun onCreated()

    /**
     * Called after [androidx.recyclerview.widget.RecyclerView.Adapter.onBindViewHolder]
     * but before binding the new data
     */
    fun onPreBound()

    /**
     * Called after [onPreBound] and after the new data is bound
     */
    fun onBound()

    /**
     * Called after [androidx.recyclerview.widget.RecyclerView.Adapter.onViewRecycled]
     */
    fun onRecycled()

    /**
     * Called when the view of the ViewHolder has gained focus
     */
    fun onFocused()

    /**
     * Called when the view of this ViewHolder has lost focus
     */
    fun onUnfocused()

    /**
     * @return true if this ViewHolder is focused, false otherwise
     */
    fun hasFocus(): Boolean

    /**
     * @return true if KeyEvent was consumed, false otherwise
     */
    fun dispatchKeyEvent(event: KeyEvent): Boolean

}
