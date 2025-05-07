package com.andef.myworkout.presentation.auth.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.andef.myworkout.ui.theme.MyWorkoutTheme

@Composable
fun AuthScreenLoginContent(navHostController: NavHostController) {

}

@Preview
@Composable
private fun AuthScreenLoginContentPreview() {
    MyWorkoutTheme {
        AuthScreenLoginContent(
            navHostController = rememberNavController()
        )
    }
}