package com.andef.myworkout.presentation.auth.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.andef.myworkout.ViewModelFactory
import com.andef.myworkout.presentation.auth.main.content.AuthMainScreenContent
import com.andef.myworkout.ui.utils.navigateToMainScreen
import kotlinx.coroutines.delay

@Composable
fun AuthMainScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController
) {
    val viewModel: AuthMainScreenViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val showLoading = remember { mutableStateOf(false) }

    Effects(
        viewModel = viewModel,
        state = state,
        showLoading = showLoading,
        navHostController = navHostController
    )

    AuthMainScreenContent(
        paddingValues = paddingValues,
        viewModel = viewModel,
        state = state,
        showLoading = showLoading,
        scope = scope,
        snackBarHostState = snackBarHostState,
        navHostController = navHostController
    )
}

@Composable
private fun Effects(
    viewModel: AuthMainScreenViewModel,
    state: State<AuthMainScreenState>,
    showLoading: MutableState<Boolean>,
    navHostController: NavHostController
) {
    LaunchedEffect(state.value.isLoading) {
        if (state.value.isLoading) {
            delay(350)
            showLoading.value = state.value.isLoading
        } else {
            showLoading.value = false
        }
    }

    LaunchedEffect(Unit) {
        viewModel.send(
            AuthMainScreenIntent.CheckToken(
                onSuccess = {
                    navigateToMainScreen(navHostController = navHostController)
                }
            )
        )
    }
}