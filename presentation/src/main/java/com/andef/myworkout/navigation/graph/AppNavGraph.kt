package com.andef.myworkout.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.andef.myworkout.di.viewmodel.ViewModelFactory
import com.andef.myworkout.navigation.Screen
import com.andef.myworkout.presentation.auth.main.AuthScreen

@Composable
fun AppNavGraph(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.AuthScreen.route
    ) {
        composable(route = Screen.AuthScreen.route) {
            AuthScreen(
                viewModelFactory = viewModelFactory,
                paddingValues = paddingValues,
                navHostController = navHostController
            )
        }
        calendarNavGraph(paddingValues = paddingValues, navHostController = navHostController)
        exerciseNavGraph(paddingValues = paddingValues, navHostController = navHostController)
        composable(route = Screen.AccountScreen.route) {

        }
    }
}