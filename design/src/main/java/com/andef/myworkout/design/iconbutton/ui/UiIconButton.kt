package com.andef.myworkout.design.iconbutton.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.andef.myworkout.design.Black
import com.andef.myworkout.design.White

@Composable
fun UiIconButton(painter: Painter, contentDescription: String, onClick: () -> Unit) {
    IconButton(colors = iconButtonColors(), onClick = onClick) {
        Icon(
            tint = Black,
            painter = painter,
            contentDescription = contentDescription
        )
    }
}

@Composable
private fun iconButtonColors() = IconButtonDefaults.iconButtonColors(
    containerColor = White,
    contentColor = Black
)