package com.andef.myworkout.ui.utils

import androidx.navigation.NavHostController
import com.andef.myworkout.navigation.Screen

fun navigateToMainScreen(navHostController: NavHostController) {
    navHostController.navigate(Screen.CalendarScreen.route) {
        popUpTo(0)
    }
}