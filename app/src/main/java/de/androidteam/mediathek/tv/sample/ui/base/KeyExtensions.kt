package de.androidteam.mediathek.tv.sample.ui.base

import android.view.KeyEvent

fun KeyEvent.isLeft() = keyCode == KeyEvent.KEYCODE_DPAD_LEFT

fun KeyEvent.isUp() = keyCode == KeyEvent.KEYCODE_DPAD_UP

fun KeyEvent.isRight() = keyCode == KeyEvent.KEYCODE_DPAD_RIGHT

fun KeyEvent.isDown() = keyCode == KeyEvent.KEYCODE_DPAD_DOWN

fun KeyEvent.isBeingPressed() = action == KeyEvent.ACTION_DOWN

fun KeyEvent.isBeingReleased() = action == KeyEvent.ACTION_UP
