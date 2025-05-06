package com.andef.myworkout.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.andef.myworkout.navigation.Screen

fun NavGraphBuilder.calendarNavGraph(
    paddingValues: PaddingValues,
    navHostController: NavHostController
) {
    navigation(
        route = Screen.CalendarScreen.route,
        startDestination = Screen.CalendarScreen.MainScreen.route
    ) {
        composable(route = Screen.CalendarScreen.MainScreen.route) {

        }
    }
}