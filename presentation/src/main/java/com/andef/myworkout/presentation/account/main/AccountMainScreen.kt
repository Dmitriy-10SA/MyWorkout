package com.andef.myworkout.presentation.account.main

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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.ViewModelFactory
import com.andef.myworkout.presentation.account.main.content.AccountMainScreenContent
import com.andef.myworkout.ui.utils.onUnauthorizedNavigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AccountMainScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController
) {
    val viewModel: AccountMainScreenViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val showLoading = remember { mutableStateOf(false) }

    Effects(
        viewModel = viewModel,
        showLoading = showLoading,
        scope = scope,
        navHostController = navHostController,
        state = state,
        snackBarHostState = snackBarHostState
    )

    AccountMainScreenContent(
        snackBarHostState = snackBarHostState,
        state = state,
        paddingValues = paddingValues,
        viewModel = viewModel,
        navHostController = navHostController,
        scope = scope,
        showLoading = showLoading
    )
}

@Composable
private fun Effects(
    viewModel: AccountMainScreenViewModel,
    showLoading: MutableState<Boolean>,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    navHostController: NavHostController,
    state: State<AccountMainScreenState>
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.send(
            AccountMainScreenIntent.LoadUserInfo(
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

    LaunchedEffect(state.value.isLoading) {
        if (state.value.isLoading) {
            delay(600)
            showLoading.value = state.value.isLoading
        } else {
            showLoading.value = false
        }
    }
}