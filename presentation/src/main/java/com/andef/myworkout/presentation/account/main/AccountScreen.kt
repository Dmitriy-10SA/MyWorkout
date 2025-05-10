package com.andef.myworkout.presentation.account.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.di.viewmodel.ViewModelFactory
import com.andef.myworkout.presentation.account.content.AccountScreenContent
import com.andef.myworkout.ui.utils.onUnauthorizedNavigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AccountScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController
) {
    val viewModel: AccountScreenViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    Effects(
        viewModel = viewModel,
        navHostController = navHostController,
        scope = scope,
        snackBarHostState = snackBarHostState,
        state = state
    )

    AccountScreenContent(
        snackBarHostState = snackBarHostState,
        state = state,
        paddingValues = paddingValues,
        viewModel = viewModel,
        navHostController = navHostController,
        scope = scope
    )
}

@Composable
private fun Effects(
    viewModel: AccountScreenViewModel,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    navHostController: NavHostController,
    state: State<AccountScreenState>
) {
    if (state.value.userInfo == null) {
        LoadUserInfoEffect(
            viewModel = viewModel,
            navHostController = navHostController,
            snackBarHostState = snackBarHostState,
            scope = scope
        )
    }
}

@Composable
private fun LoadUserInfoEffect(
    viewModel: AccountScreenViewModel,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    navHostController: NavHostController
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.send(
            AccountScreenIntent.LoadUserInfo(
                onUnauthorized = {
                    scope.launch {
                        snackBarHostState.currentSnackbarData?.dismiss()
                        snackBarHostState.showSnackbar(
                            message = context.getString(R.string.unauthorized),
                            withDismissAction = true
                        )
                        onUnauthorizedNavigate(navHostController = navHostController)
                    }
                }
            )
        )
    }
}