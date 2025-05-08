package com.andef.myworkout.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
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
    navHostController: NavHostController,
    snackBarHostState: SnackbarHostState
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.AuthScreen.route
    ) {
        composable(route = Screen.AuthScreen.route) {
            AuthScreen(
                viewModelFactory = viewModelFactory,
                paddingValues = paddingValues,
                navHostController = navHostController,
                snackBarHostState = snackBarHostState
            )
        }
        composable(route = Screen.AccountScreen.route) {

        }
        exerciseNavGraph(paddingValues = paddingValues, navHostController = navHostController)
        calendarNavGraph(paddingValues = paddingValues, navHostController = navHostController)
    }
}