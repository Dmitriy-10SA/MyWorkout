package com.andef.myworkout.design.dialog.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.andef.myworkout.design.Black
import com.andef.myworkout.design.White
import com.andef.myworkout.design.button.state.UiButtonState
import com.andef.myworkout.design.button.ui.UiButton

@Composable
fun UiDialog(
    title: (@Composable () -> Unit)? = null,
    text: (@Composable () -> Unit)? = null,
    confirmText: String = "",
    dismissText: String = "",
    confirm: () -> Unit = {},
    dismiss: () -> Unit = {},
    showButtons: Boolean = false,
    onDismissRequest: () -> Unit = dismiss
) {
    AlertDialog(
        containerColor = White,
        textContentColor = Black,
        iconContentColor = Black,
        titleContentColor = Black,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            if (showButtons) {
                UiButton(
                    state = UiButtonState.Base(),
                    text = confirmText,
                    onClick = confirm
                )
            }
        },
        dismissButton = {
            if (showButtons) {
                UiButton(
                    state = UiButtonState.Base(),
                    text = dismissText,
                    onClick = dismiss
                )
            }
        },
        shape = RoundedCornerShape(24.dp),
        title = title,
        text = text
    )
}