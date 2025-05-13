package com.andef.myworkout.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.andef.myworkout.ViewModelFactory
import com.andef.myworkout.navigation.Screen

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
        authNavGraph(
            paddingValues = paddingValues,
            navHostController = navHostController,
            viewModelFactory = viewModelFactory
        )
        calendarNavGraph(
            paddingValues = paddingValues,
            navHostController = navHostController,
            viewModelFactory = viewModelFactory
        )
        exerciseNavGraph(
            paddingValues = paddingValues,
            navHostController = navHostController,
            viewModelFactory = viewModelFactory
        )
        accountNavGraph(
            paddingValues = paddingValues,
            navHostController = navHostController,
            viewModelFactory = viewModelFactory
        )
    }
}