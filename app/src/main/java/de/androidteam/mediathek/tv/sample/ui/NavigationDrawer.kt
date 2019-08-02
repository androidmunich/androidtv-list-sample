package de.androidteam.mediathek.tv.sample.ui

import android.view.KeyEvent
import android.view.MenuItem
import androidx.core.view.forEachIndexed
import com.google.android.material.navigation.NavigationView
import de.androidteam.mediathek.tv.sample.R
import de.androidteam.mediathek.tv.sample.ui.base.isBeingPressed
import de.androidteam.mediathek.tv.sample.ui.base.isDown
import de.androidteam.mediathek.tv.sample.ui.base.isUp
import kotlin.collections.set
import kotlin.math.max
import kotlin.math.min

/**
 * Wrapper around [com.google.android.material.navigation.NavigationView]
 * that supports selecting items using KeyEvents
 */
class NavigationDrawer(
    val navigationView: NavigationView
) : NavigationView.OnNavigationItemSelectedListener {

    var isExpanded = false
        private set
    var isShowing = false
        private set
    var currentItemId = -1
        private set
    private var currentPosition = 0
    private val items = arrayListOf<MenuItem>()
    private val itemMap = linkedMapOf<Int, Int>() // Maps menu item ids to their position
    private var clicked = false

    init {
        navigationView.setNavigationItemSelectedListener(this)
        navigationView.menu.forEachIndexed { index, item ->
            itemMap[item.itemId] = index
            items.add(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        currentItemId = item.itemId
        item.isChecked = true
        return true
    }

    fun dispatchKeyEvent(event: KeyEvent): Boolean {
        // We can only navigate up or down
        if (!event.isUp() && !event.isDown()) {
            return false
        }

        if (event.isBeingPressed() && (event.isUp() || event.isDown())) {
            val newPosition = if (event.isUp()) {
                max(currentPosition - 1, 0)
            } else {
                min(currentPosition + 1, navigationView.menu.size() - 1)
            }
            selectItemAt(newPosition, requestFocus = true)
            clicked = true
            return true
        } else return clicked
    }

    fun selectItemAt(position: Int, requestFocus: Boolean = false) {
        navigationView.setCheckedItem(items[position])
        currentPosition = position
        if (requestFocus) {
            navigationView.requestFocus()
        }

    }

    fun selectItemId(itemId: Int, requestFocus: Boolean = false) {
        navigationView.setCheckedItem(itemId)
        currentPosition = itemMap[itemId] ?: 0
        if (requestFocus) {
            navigationView.requestFocus()
        }
    }

    fun expand(expand: Boolean) {
        if (expand == isExpanded) {
            return
        }
        if (expand) {
            navigationView.animate().translationX(0f)
            navigationView.requestFocus()
        } else {
            navigationView.animate().translationX(
                navigationView.resources.getDimensionPixelSize(R.dimen.navigation_view_expand_translation)
                    .toFloat()
            )
        }
        isExpanded = expand
    }
}
