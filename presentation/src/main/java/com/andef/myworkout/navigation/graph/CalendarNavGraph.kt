package com.andef.myworkout.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.andef.myworkout.di.viewmodel.ViewModelFactory
import com.andef.myworkout.navigation.Screen
import com.andef.myworkout.presentation.calendar.main.CalendarScreen

fun NavGraphBuilder.calendarNavGraph(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController
) {
    navigation(
        route = Screen.CalendarScreen.route,
        startDestination = Screen.CalendarScreen.MainScreen.route
    ) {
        composable(route = Screen.CalendarScreen.MainScreen.route) {
            CalendarScreen(
                paddingValues = paddingValues,
                viewModelFactory = viewModelFactory,
                navHostController = navHostController
            )
        }
    }
}