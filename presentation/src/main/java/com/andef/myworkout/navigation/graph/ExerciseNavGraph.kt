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
import com.andef.myworkout.presentation.exercises.addorchange.ExercisesAddOrChangeScreen
import com.andef.myworkout.presentation.exercises.main.ExercisesMainScreen

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
            ExercisesMainScreen(
                paddingValues = paddingValues,
                viewModelFactory = viewModelFactory,
                navHostController = navHostController
            )
        }
        composable(
            route = Screen.ExercisesScreen.AddOrChangeScreen.route,
            arguments = listOf(
                navArgument(Screen.IS_ADD) { type = NavType.BoolType }
            )
        ) {
            val isAdd = it.arguments?.getBoolean(Screen.IS_ADD) == true

            ExercisesAddOrChangeScreen(
                paddingValues = paddingValues,
                viewModelFactory = viewModelFactory,
                navHostController = navHostController,
                isAdd = isAdd
            )
        }
    }
}