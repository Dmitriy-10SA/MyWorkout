package com.andef.myworkout.design.fab.ui

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.andef.myworkout.design.Blue
import com.andef.myworkout.design.White
import com.andef.myworkout.design.fab.state.UiFABState

@Composable
fun UiFAB(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String,
    state: UiFABState,
    onClick: () -> Unit
) {
    when (state) {
        UiFABState.Base -> {
            FloatingActionButton(
                modifier = modifier,
                containerColor = Blue,
                contentColor = White,
                onClick = onClick,
                shape = CircleShape
            ) {
                Icon(
                    painter = painter,
                    contentDescription = contentDescription,
                    tint = White
                )
            }
        }
    }
}