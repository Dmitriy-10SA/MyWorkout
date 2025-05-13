package com.andef.myworkout.navigation.graph

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.andef.myworkout.ViewModelFactory
import com.andef.myworkout.navigation.Screen
import com.andef.myworkout.presentation.account.changeinfo.AccountChangeInfoScreen
import com.andef.myworkout.presentation.account.main.AccountMainScreen

fun NavGraphBuilder.accountNavGraph(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController
) {
    navigation(
        route = Screen.AccountScreen.route,
        startDestination = Screen.AccountScreen.MainScreen.route
    ) {
        composable(route = Screen.AccountScreen.MainScreen.route) {
            AccountMainScreen(
                paddingValues = paddingValues,
                viewModelFactory = viewModelFactory,
                navHostController = navHostController
            )
        }
        composable(
            route = Screen.AccountScreen.ChangeInfoScreen.route,
            arguments = listOf(
                navArgument(Screen.SURNAME) { type = NavType.StringType },
                navArgument(Screen.NAME) { type = NavType.StringType },
                navArgument(Screen.PATRONYMIC) { type = NavType.StringType },
                navArgument(Screen.PHOTO) { type = NavType.StringType }
            )
        ) {
            val surname = Uri.decode(it.arguments?.getString(Screen.SURNAME) ?: "")
            val name = Uri.decode(it.arguments?.getString(Screen.NAME) ?: "")
            val patronymic = Uri.decode(it.arguments?.getString(Screen.PATRONYMIC) ?: "")
            val photo = Uri.decode(it.arguments?.getString(Screen.PHOTO) ?: "")

            AccountChangeInfoScreen(
                paddingValues = paddingValues,
                viewModelFactory = viewModelFactory,
                navHostController = navHostController,
                surname = surname,
                name = name,
                patronymic = patronymic,
                photo = photo
            )
        }
    }
}