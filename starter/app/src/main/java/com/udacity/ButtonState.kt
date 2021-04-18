package com.udacity


sealed class ButtonState {
    object Still : ButtonState()
    object Clicked : ButtonState()
    object Loading : ButtonState()
    object Completed : ButtonState()
}