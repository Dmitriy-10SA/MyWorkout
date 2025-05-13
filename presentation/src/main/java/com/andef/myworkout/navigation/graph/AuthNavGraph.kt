package com.andef.myworkout.navigation.graph

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.andef.myworkout.ViewModelFactory
import com.andef.myworkout.navigation.Screen
import com.andef.myworkout.presentation.auth.forgotpassword.AuthForgotPasswordScreen
import com.andef.myworkout.presentation.auth.main.AuthMainScreen

fun NavGraphBuilder.authNavGraph(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController
) {
    navigation(
        route = Screen.AuthScreen.route,
        startDestination = Screen.AuthScreen.MainScreen.route
    ) {
        composable(route = Screen.AuthScreen.MainScreen.route) {
            AuthMainScreen(
                paddingValues = paddingValues,
                viewModelFactory = viewModelFactory,
                navHostController = navHostController
            )
        }
        composable(route = Screen.AuthScreen.ForgotPasswordScreen.route) {
            AuthForgotPasswordScreen(
                paddingValues = paddingValues,
                viewModelFactory = viewModelFactory,
                navHostController = navHostController
            )
        }
    }
}