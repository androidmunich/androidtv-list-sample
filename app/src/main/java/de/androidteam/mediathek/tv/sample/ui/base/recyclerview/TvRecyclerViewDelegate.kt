package de.androidteam.mediathek.tv.sample.ui.base.recyclerview

import android.util.Log
import android.view.KeyEvent
import android.view.SoundEffectConstants
import android.view.View
import androidx.leanback.widget.BaseGridView
import androidx.leanback.widget.OnChildViewHolderSelectedListener
import androidx.recyclerview.widget.RecyclerView
import de.androidteam.mediathek.tv.sample.ui.base.*

class TvRecyclerViewDelegate(
    private val recyclerView: BaseGridView
) {

    companion object {
        const val TAG = "TvRecyclerView"
    }

    private var currentViewHolder: RecyclerView.ViewHolder? = null
    private var layoutManager = recyclerView.layoutManager!!
    private var isScrolling = false
    private var scrollLeftAllowed = false
    private var scrollUpAllowed = false
    private var scrollRightAllowed = false
    private var scrollDownAllowed = false
    private var pendingSelection = false

    init {
        recyclerView.addOnChildViewHolderSelectedListener(object : OnChildViewHolderSelectedListener() {
            override fun onChildViewHolderSelected(
                parent: RecyclerView?,
                child: RecyclerView.ViewHolder?,
                position: Int,
                subposition: Int
            ) {
                super.onChildViewHolderSelected(parent, child, position, subposition)
                currentViewHolder = child
                // Dispatch focus for this ViewHolder if needed
                if (child is TvViewHolder && ((recyclerView.hasFocus() && !child.hasFocus()) || pendingSelection)) {
                    child.itemView.requestFocus()
                    pendingSelection = false
                }
            }
        })
    }

    /**
     * Set scroll directions allowed.
     *
     * Will ignore KeyEvents for scroll directions not allowed.
     */
    fun setScrollDirections(
        scrollLeft: Boolean,
        scrollUp: Boolean,
        scrollRight: Boolean,
        scrollDown: Boolean
    ) {
        scrollLeftAllowed = scrollLeft
        scrollUpAllowed = scrollUp
        scrollRightAllowed = scrollRight
        scrollDownAllowed = scrollDown
    }

    /**
     * Dispatch a KeyEvent to scroll the RecyclerView
     *
     * If we didn't scroll after this KeyEvent,
     * a check is done to consume the event according to the consume edges passed
     *
     * @return true if KeyEvent was handled and RecyclerView scrolled, false otherwise
     */
    fun dispatchKeyEvent(
        event: KeyEvent,
        consumeEdgeLeft: Boolean,
        consumeEdgeTop: Boolean,
        consumeEdgeRight: Boolean,
        consumeEdgeBottom: Boolean
    ): Boolean {
        val vh = currentViewHolder

        // Check if the focused ViewHolder can handle this key event
        if (vh is TvViewHolder && vh.dispatchKeyEvent(event)) {
            return true
        }

        // Check if we can scroll in the direction of this key event
        val isScrollAllowed = (scrollLeftAllowed && event.isLeft())
                || (scrollUpAllowed && event.isUp())
                || (scrollRightAllowed && event.isRight())
                || (scrollDownAllowed && event.isDown())

        if (!isScrollAllowed) {
            return false
        }

        // Handle key release
        if (isScrolling && event.isBeingReleased()) {
            isScrolling = false
            return true
        }

        // Ignore non-scroll events
        if (!event.isBeingPressed() || !isScrollEvent(event)) {
            return false
        }

        // Keep track of the ViewHolder that's still selected
        // because it'll change during scroll
        val previousViewHolder = currentViewHolder
        val previousView = previousViewHolder?.itemView

        // Find the next view that can be focused in this KeyEvent direction
        val nextView = findNextView(event)

        if (nextView == null) {
            isScrolling = false
            Log.d(TAG, "Next focusable view not found")
            return false
        }

        // Get the ViewHolder for the next view
        val nextViewHolder = recyclerView.findContainingViewHolder(nextView)

        // Dispatch focus if needed
        if (nextViewHolder is TvViewHolder && !nextView.hasFocus()) {
            nextView.requestFocus()
            Log.d(TAG, "Dispatching focus to: ${nextViewHolder.adapterPosition}")
            nextView.playSoundEffect(SoundEffectConstants.CLICK)
        }

        // Don't unselect if we're still in the same ViewHolder
        if (previousViewHolder is TvViewHolder
            && nextViewHolder != previousViewHolder
            && previousViewHolder.hasFocus()
        ) {
            Log.d(TAG, "Clearing focus of: ${previousViewHolder.adapterPosition}")
            previousViewHolder.onUnfocused()
        }

        // Check if we started a scroll to the next view
        // If there's a pending internal scroll, we should still handle this event
        isScrolling = checkIsScrolling(nextView, previousView)

        currentViewHolder = nextViewHolder

        // Check if we didn't scroll and consume events if we want to
        if (!isScrolling) {
            val consumeEdge = (consumeEdgeLeft && event.isLeft())
                    || (consumeEdgeTop && event.isUp())
                    || (consumeEdgeRight && event.isRight())
                    || (consumeEdgeBottom && event.isDown())
            if (consumeEdge) {
                return true
            }
            Log.d(TAG, "Didn't scroll")
            return false
        }
        return isScrolling
    }

    fun selectCurrentPosition() {
        val viewHolder = getCurrentViewHolder()
        if (viewHolder is TvViewHolder) {
            viewHolder.itemView.requestFocus()
        } else {
            pendingSelection = true
        }
    }

    fun unselectCurrentPosition() {
        val viewHolder = getCurrentViewHolder()
        if (viewHolder is TvViewHolder) {
            viewHolder.onUnfocused()
        }
    }

    /**
     * Check if we're currently scrolling to a new View
     *
     * @return true if a scroll is in progress, false otherwise
     */
    private fun checkIsScrolling(nextView: View, currentView: View?): Boolean {
        if (nextView != currentView) {
            return true
        }
        return recyclerView.scrollState != RecyclerView.SCROLL_STATE_IDLE
                || layoutManager.isSmoothScrolling
    }


    fun getCurrentViewHolder(): RecyclerView.ViewHolder? {
        if (currentViewHolder == null
            || currentViewHolder?.adapterPosition == RecyclerView.NO_POSITION
        ) {
            currentViewHolder = recyclerView.findViewHolderForAdapterPosition(
                recyclerView.selectedPosition
            )
        }
        return currentViewHolder
    }

    private fun findNextView(event: KeyEvent): View? {
        val nextView = recyclerView.focusSearch(
            layoutManager.findViewByPosition(recyclerView.selectedPosition),
            getFocusDirection(event)
        )
        if (nextView.parent != recyclerView) {
            return null
        }
        return nextView
    }

    private fun isScrollEvent(event: KeyEvent): Boolean {
        return event.isLeft() || event.isUp() || event.isRight() || event.isDown()
    }

    private fun getFocusDirection(event: KeyEvent): Int {
        return when (event.keyCode) {
            KeyEvent.KEYCODE_DPAD_LEFT -> View.FOCUS_LEFT
            KeyEvent.KEYCODE_DPAD_UP -> View.FOCUS_UP
            KeyEvent.KEYCODE_DPAD_RIGHT -> View.FOCUS_RIGHT
            KeyEvent.KEYCODE_DPAD_DOWN -> View.FOCUS_DOWN
            else -> 0
        }
    }
}
