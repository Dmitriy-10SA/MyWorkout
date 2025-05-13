package com.andef.myworkout.ui.utils

import androidx.navigation.NavHostController
import com.andef.myworkout.navigation.Screen

fun navigateToMainScreen(navHostController: NavHostController) {
    navHostController.navigate(Screen.CalendarScreen.MainScreen.route) {
        popUpTo(0)
    }
}

fun onUnauthorizedNavigate(navHostController: NavHostController) {
    navHostController.navigate(Screen.AuthScreen.MainScreen.route) {
        popUpTo(0)
    }
}