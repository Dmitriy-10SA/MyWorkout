package com.andef.myworkout.design.topbar.ui

import androidx.compose.foundation.border
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.andef.myworkout.design.Black
import com.andef.myworkout.design.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiTopBar(title: String) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        colors = topAppBarColors(),
        modifier = Modifier
            .shadow(elevation = 8.dp, ambientColor = Black, spotColor = Black)
            .border(width = 1.dp, color = Black.copy(alpha = 0.05f))
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun topAppBarColors() = TopAppBarDefaults.topAppBarColors(
    containerColor = White,
    navigationIconContentColor = Black,
    titleContentColor = Black,
    actionIconContentColor = Black,
    scrolledContainerColor = White
)