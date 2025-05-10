package com.andef.myworkout.presentation.auth.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.design.chooser.ui.UiChooser
import com.andef.myworkout.design.loading.ui.UiLoadingOverlay
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.design.snackbar.state.UiSnackBarState
import com.andef.myworkout.design.snackbar.ui.UiSnackBarHost
import com.andef.myworkout.di.viewmodel.ViewModelFactory
import com.andef.myworkout.presentation.auth.content.AuthScreenTemplate
import com.andef.myworkout.presentation.auth.content.ForgotPasswordDialog
import com.andef.myworkout.presentation.auth.content.LoginContent
import com.andef.myworkout.presentation.auth.content.SignUpContent
import com.andef.myworkout.ui.utils.navigateToMainScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@Composable
fun AuthScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController
) {
    val viewModel: AuthScreenViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val showEmptyAuthScreenTemplate = remember { mutableStateOf(false) }
    val showForgotDialog = remember { mutableStateOf(false) }

    CheckTokenEffect(viewModel = viewModel, navHostController = navHostController)

    ShowForgotPasswordEffect(
        state = state,
        showEmptyAuthScreenTemplate = showEmptyAuthScreenTemplate,
        showForgotDialog = showForgotDialog
    )

    UiScaffold(
        snackBarHost = {
            UiSnackBarHost(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(WindowInsets.statusBars.asPaddingValues()),
                snackBarHostState = snackBarHostState,
                state = UiSnackBarState.Error
            )
        }
    ) {
        if (!state.value.isCheckTokenLoading && !state.value.isSwitchToMainScreen) {
            when (showEmptyAuthScreenTemplate.value) {
                true -> {
                    AuthScreenTemplate(paddingValues = paddingValues)
                    if (showForgotDialog.value) ForgotPasswordDialog(
                        viewModel = viewModel,
                        state = state,
                        snackBarHostState = snackBarHostState,
                        scope = scope,
                        navHostController = navHostController
                    )
                }

                false -> {
                    AuthScreenTemplate(paddingValues = paddingValues) {
                        AuthScreenContent(
                            viewModel = viewModel,
                            state = state,
                            snackBarHostState = snackBarHostState,
                            scope = scope,
                            navHostController = navHostController
                        )
                    }
                }
            }
        }
        if (state.value.isLoading && !showForgotDialog.value) {
            UiLoadingOverlay(
                text = stringResource(R.string.my_workout),
                paddingValues = paddingValues
            )
        }
    }
}

@Composable
private fun CheckTokenEffect(viewModel: AuthScreenViewModel, navHostController: NavHostController) {
    LaunchedEffect(Unit) {
        viewModel.send(
            AuthScreenIntent.CheckToken(
                onSuccess = {
                    navigateToMainScreen(navHostController = navHostController)
                }
            )
        )
    }
}

@Composable
private fun ShowForgotPasswordEffect(
    state: State<AuthScreenState>,
    showEmptyAuthScreenTemplate: MutableState<Boolean>,
    showForgotDialog: MutableState<Boolean>
) {
    LaunchedEffect(state.value.isShowForgotPassword) {
        if (state.value.isShowForgotPassword) {
            showEmptyAuthScreenTemplate.value = true
            delay(800)
            showForgotDialog.value = true
        } else {
            showForgotDialog.value = false
            delay(800)
            showEmptyAuthScreenTemplate.value = false
        }
    }
}

@Composable
private fun AuthScreenContent(
    viewModel: AuthScreenViewModel, state: State<AuthScreenState>,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    navHostController: NavHostController
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 1.dp)
    ) {
        item {
            UiChooser(
                enabledLeft = !state.value.isLogin,
                enabledRight = state.value.isLogin,
                textLeft = stringResource(R.string.login),
                textRight = stringResource(R.string.sign_up),
                onClickLeft = { viewModel.send(AuthScreenIntent.LoginChoose) },
                onClickRight = { viewModel.send(AuthScreenIntent.SignUpChoose) }
            )
        }
        when (state.value.isLogin) {
            true -> item {
                LoginContent(
                    viewModel = viewModel,
                    state = state,
                    snackBarHostState = snackBarHostState,
                    scope = scope,
                    navHostController = navHostController
                )
            }

            false -> item {
                SignUpContent(
                    viewModel = viewModel,
                    state = state,
                    snackBarHostState = snackBarHostState,
                    scope = scope,
                    navHostController = navHostController
                )
            }
        }
    }
}