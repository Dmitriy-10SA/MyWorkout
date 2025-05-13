package com.andef.myworkout.design.snackbar.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.andef.myworkout.design.Blue
import com.andef.myworkout.design.Red
import com.andef.myworkout.design.snackbar.state.UiSnackBarState

@Composable
fun UiSnackBarHost(
    modifier: Modifier,
    snackBarHostState: SnackbarHostState,
    state: UiSnackBarState
) {
    val containerColor = when (state) {
        UiSnackBarState.Error -> Red
        UiSnackBarState.Usual -> Blue
    }

    SnackbarHost(hostState = snackBarHostState) { data ->
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Snackbar(
                snackbarData = data,
                containerColor = containerColor,
                contentColor = Color(0xFFFFFFFF),
                dismissActionContentColor = Color(0xFFFFFFFF),
                actionColor = Color(0xFFFFFFFF),
                actionContentColor = Color(0xFFFFFFFF),
                shape = RoundedCornerShape(10.dp)
            )
        }
    }
}