package com.andef.myworkout.design.topbar.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myworkout.design.Black
import com.andef.myworkout.design.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UiTopBar(
    title: String,
    actions: @Composable RowScope.() -> Unit = {},
    navigationIcon: @Composable () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title, color = Black, fontSize = 24.sp) },
        colors = topAppBarColors(),
        navigationIcon = navigationIcon,
        modifier = Modifier
            .shadow(
                elevation = 8.dp,
                ambientColor = Black,
                spotColor = Black,
                shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
            )
            .border(
                width = 1.dp,
                color = Black.copy(alpha = 0.05f),
                shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
            ),
        actions = actions
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