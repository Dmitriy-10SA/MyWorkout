package com.andef.myworkout.design.snackbar.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.andef.myworkout.design.snackbar.state.UiSnackBarState

@Composable
fun UiSnackBar(snackBarHostState: SnackbarHostState, state: UiSnackBarState) {
    val containerColor = when (state) {
        UiSnackBarState.Error -> Color(0xFFD2082B)
        UiSnackBarState.Usual -> Color(0xFF1565C0)
    }

    SnackbarHost(hostState = snackBarHostState) { data ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues()),
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