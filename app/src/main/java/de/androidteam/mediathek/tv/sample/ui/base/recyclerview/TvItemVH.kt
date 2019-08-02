package de.androidteam.mediathek.tv.sample.ui.base.recyclerview

import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import de.androidteam.mediathek.tv.sample.R

/**
 * A ViewHolder that can be focused.
 *
 * Don't extend from this class if your ViewHolder shouldn't be focused
 */
abstract class TvItemVH(private val view: View) : RecyclerView.ViewHolder(view), TvViewHolder {

    companion object {
        const val TAG = "TvItemVH"
    }

    private val focusAnimationTranslationZ = itemView.resources
        .getDimensionPixelSize(R.dimen.video_item_spacing).toFloat()
    private val focusAnimationInterpolator = DecelerateInterpolator()
    private val unfocusAnimationInterpolator = DecelerateInterpolator()
    private val unfocusAnimationDuration = 500L
    private val focusAnimationDuration = 200L

    override fun onCreated() {
        view.isFocusable = true
        view.isFocusableInTouchMode = true
        view.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                onUnfocused()
            } else {
                onFocused()
            }
        }
    }

    override fun onPreBound() {
        // No-op
    }

    override fun onBound() {
        itemView.scaleY = 1.0f
        itemView.scaleX = 1.0f
    }

    override fun onRecycled() {
        // No-op
        // Clear resources if needed
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        return false
    }

    override fun onFocused() {
        Log.d(TAG, "Item at $adapterPosition gained focus")
        itemView.animate()
            .scaleX(1.1f)
            .scaleY(1.1f)
            .translationZ(focusAnimationTranslationZ)
            .setInterpolator(focusAnimationInterpolator)
            .duration = focusAnimationDuration
    }

    override fun onUnfocused() {
        Log.d(TAG, "Item at $adapterPosition lost focus")
        itemView.animate()
            .scaleX(1.0f)
            .scaleY(1.0f)
            .translationZ(0f)
            .setInterpolator(unfocusAnimationInterpolator)
            .duration = unfocusAnimationDuration
    }

    override fun hasFocus(): Boolean {
        return view.hasFocus()
    }
}
