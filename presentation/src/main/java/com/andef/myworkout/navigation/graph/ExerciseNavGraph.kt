package com.andef.myworkout.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.andef.myworkout.ViewModelFactory
import com.andef.myworkout.navigation.Screen

fun NavGraphBuilder.exerciseNavGraph(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController
) {
    navigation(
        route = Screen.ExercisesScreen.route,
        startDestination = Screen.ExercisesScreen.MainScreen.route
    ) {
        composable(route = Screen.ExercisesScreen.MainScreen.route) {

        }
        composable(
            route = Screen.ExercisesScreen.AddOrChangeScreen.route,
            arguments = listOf(
                navArgument(Screen.IS_ADD) { type = NavType.BoolType }
            )
        ) {

        }
    }
}