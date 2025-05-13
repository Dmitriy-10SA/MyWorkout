package com.andef.myworkout.design.iconbutton.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.andef.myworkout.design.Black
import com.andef.myworkout.design.White

@Composable
fun UiIconButton(
    painter: Painter,
    contentDescription: String,
    tint: Color = Black,
    onClick: () -> Unit
) {
    IconButton(colors = iconButtonColors(tint), onClick = onClick) {
        Icon(
            tint = tint,
            painter = painter,
            contentDescription = contentDescription
        )
    }
}

@Composable
private fun iconButtonColors(tint: Color) = IconButtonDefaults.iconButtonColors(
    containerColor = White,
    contentColor = tint
)