package com.andef.myworkout.design.scaffold.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.andef.myworkout.design.Black
import com.andef.myworkout.design.White

@Composable
fun UiScaffold(
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackBarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        containerColor = White,
        contentColor = Black,
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = snackBarHost
    ) {
        content(it)
    }
}