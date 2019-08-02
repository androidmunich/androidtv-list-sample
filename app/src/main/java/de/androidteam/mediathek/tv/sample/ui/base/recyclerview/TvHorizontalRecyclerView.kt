package de.androidteam.mediathek.tv.sample.ui.base.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import androidx.leanback.widget.HorizontalGridView

class TvHorizontalRecyclerView : HorizontalGridView, TvRecyclerView, View.OnFocusChangeListener {

    private val delegate = TvRecyclerViewDelegate(this)
    private var columns = 1
    private val focusListeners = arrayListOf<OnFocusChangeListener>()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        clipChildren = false
        clipToPadding = false
        setGravity(Gravity.CENTER)
        onFocusChangeListener = this
        setScrollDirections()
    }

    fun setScrollDirections(
        scrollLeft: Boolean = true,
        scrollTop: Boolean = false,
        scrollRight: Boolean = true,
        scrollBottom: Boolean = false
    ) {
        delegate.setScrollDirections(scrollLeft, scrollTop, scrollRight, scrollBottom)
    }

    override fun addFocusListener(listener: OnFocusChangeListener) {
        focusListeners.add(listener)
    }

    override fun removeFocusListener(listener: OnFocusChangeListener) {
        focusListeners.remove(listener)
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus) {
            selectCurrentPosition()
        }
        focusListeners.forEach {
            it.onFocusChange(v, hasFocus)
        }
    }

    /**
     * Allows passing optional arguments to [dispatchKeyEvent]
     */
    fun dispatchKeyEventAndConsumeEdges(
        event: KeyEvent,
        consumeEdgeLeft: Boolean = false,
        consumeEdgeTop: Boolean = false,
        consumeEdgeRight: Boolean = true,
        consumeEdgeBottom: Boolean = false
    ): Boolean {
        return delegate.dispatchKeyEvent(
            event,
            consumeEdgeLeft,
            consumeEdgeTop,
            consumeEdgeRight,
            consumeEdgeBottom
        )
    }

    override fun dispatchKeyEvent(
        event: KeyEvent,
        consumeEdgeLeft: Boolean,
        consumeEdgeTop: Boolean,
        consumeEdgeRight: Boolean,
        consumeEdgeBottom: Boolean
    ): Boolean {
        return delegate.dispatchKeyEvent(
            event,
            consumeEdgeLeft,
            consumeEdgeTop,
            consumeEdgeRight,
            consumeEdgeBottom
        )
    }

    override fun getCurrentViewHolder(): ViewHolder? {
        return delegate.getCurrentViewHolder()
    }

    override fun setNumRows(numRows: Int) {
        super.setNumRows(numRows)
        columns = numRows
        if (columns > 1) {
            setScrollDirections(scrollLeft = true, scrollTop = true, scrollRight = true, scrollBottom = true)
        }
    }

    fun getNumberOfColumns(): Int = columns

    fun selectCurrentPosition() {
        delegate.selectCurrentPosition()
    }

    fun unselectCurrentPosition() {
        delegate.unselectCurrentPosition()
    }
}
