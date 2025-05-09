package com.andef.myworkout.design.button.state

import androidx.compose.ui.Modifier

sealed class UiButtonState {
    data class Base(
        val modifier: Modifier = Modifier,
        val textModifier: Modifier = Modifier,
        val enabled: Boolean = true
    ) : UiButtonState()

    data class Chooser(
        val modifier: Modifier = Modifier,
        val textModifier: Modifier = Modifier,
        val enabled: Boolean = true
    ) : UiButtonState()
    data object ForgotPassword : UiButtonState()
}