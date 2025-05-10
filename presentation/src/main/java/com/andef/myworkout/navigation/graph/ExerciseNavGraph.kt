package com.andef.myworkout.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.andef.myworkout.di.viewmodel.ViewModelFactory
import com.andef.myworkout.navigation.Screen
import com.andef.myworkout.presentation.exercises.addorchange.ExercisesAddOrChangeScreen
import com.andef.myworkout.presentation.exercises.main.ExercisesScreen

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
            ExercisesScreen(
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
            val isAdd = it.arguments?.getBoolean(Screen.IS_ADD) != false
            ExercisesAddOrChangeScreen(
                paddingValues = paddingValues,
                viewModelFactory = viewModelFactory,
                navHostController = navHostController,
                isAdd = isAdd
            )
        }
    }
}