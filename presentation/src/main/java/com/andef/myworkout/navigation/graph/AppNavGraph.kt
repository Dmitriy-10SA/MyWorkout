package com.andef.myworkout.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.andef.myworkout.navigation.Screen
import com.andef.myworkout.presentation.auth.AuthScreen

@Composable
fun AppNavGraph(paddingValues: PaddingValues, navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.AuthScreen.route
    ) {
        composable(route = Screen.AuthScreen.route) {
            AuthScreen(paddingValues = paddingValues, navHostController = navHostController)
        }
        composable(route = Screen.AccountScreen.route) {

        }
        exerciseNavGraph(paddingValues = paddingValues, navHostController = navHostController)
        calendarNavGraph(paddingValues = paddingValues, navHostController = navHostController)
    }
}