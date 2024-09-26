package com.what.colorpicker

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

class State{
    var currentColor by mutableStateOf(Color.Black)
}

val uiState = compositionLocalOf{ State() }