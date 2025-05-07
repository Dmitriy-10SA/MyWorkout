package com.andef.myworkout.presentation.auth.signup

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.andef.myworkout.ui.theme.MyWorkoutTheme

@Composable
fun AuthScreenSignUpContent(navHostController: NavHostController) {

}

@Preview
@Composable
private fun AuthScreenLoginContentPreview() {
    MyWorkoutTheme {
        AuthScreenSignUpContent(
            navHostController = rememberNavController()
        )
    }
}