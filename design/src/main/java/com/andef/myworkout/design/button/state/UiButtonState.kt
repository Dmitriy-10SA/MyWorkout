package com.andef.myworkout.design.button.state

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

sealed class UiButtonState {
    data class Base(
        val modifier: Modifier = Modifier,
        val textModifier: Modifier = Modifier,
        val enabled: Boolean = true
    ) : UiButtonState()

    data class Chooser(
        val modifier: Modifier = Modifier,
        val textModifier: Modifier = Modifier,
        val enabled: Boolean = true,
        val icon: Painter,
        val contentDescription: String
    ) : UiButtonState()
    data object ForgotPassword : UiButtonState()
}