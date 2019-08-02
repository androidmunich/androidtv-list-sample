package de.androidteam.mediathek.tv.sample.ui.base.recyclerview

import android.view.KeyEvent
import android.view.View
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView

interface TvRecyclerView : ViewParent {

    fun dispatchKeyEvent(
        event: KeyEvent,
        consumeEdgeLeft: Boolean,
        consumeEdgeTop: Boolean,
        consumeEdgeRight: Boolean,
        consumeEdgeBottom: Boolean
    ): Boolean

    fun addFocusListener(listener: View.OnFocusChangeListener)

    fun removeFocusListener(listener: View.OnFocusChangeListener)

    fun getCurrentViewHolder(): RecyclerView.ViewHolder?
}
