package com.andef.myworkout.presentation.auth.main.content

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.design.button.state.UiButtonState
import com.andef.myworkout.design.button.ui.UiButton
import com.andef.myworkout.design.chooser.ui.UiChooser
import com.andef.myworkout.design.loading.ui.UiLoadingOverlay
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.design.snackbar.state.UiSnackBarState
import com.andef.myworkout.design.snackbar.ui.UiSnackBarHost
import com.andef.myworkout.presentation.auth.main.AuthMainScreenIntent
import com.andef.myworkout.presentation.auth.main.AuthMainScreenState
import com.andef.myworkout.presentation.auth.main.AuthMainScreenViewModel
import com.andef.myworkout.ui.utils.navigateToMainScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AuthMainScreenContent(
    paddingValues: PaddingValues,
    viewModel: AuthMainScreenViewModel,
    state: State<AuthMainScreenState>,
    showLoading: State<Boolean>,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    navHostController: NavHostController
) {
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
            AuthMainScreenTemplate(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF111112), Color(0xFF414148))
                        )
                    )
                    .padding(paddingValues)
                    .imePadding(),
            ) {
                MainContent(
                    state = state,
                    viewModel = viewModel,
                    scope = scope,
                    snackBarHostState = snackBarHostState,
                    navHostController = navHostController
                )
            }
        }
        if (showLoading.value) {
            UiLoadingOverlay(
                text = stringResource(R.string.my_workout),
                paddingValues = paddingValues
            )
        }
    }
}

@Composable
private fun MainContent(
    state: State<AuthMainScreenState>,
    viewModel: AuthMainScreenViewModel,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val keyboardOptions = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 1.dp)
    ) {
        UiChooser(
            enabledLeft = !state.value.isLogin,
            enabledRight = state.value.isLogin,
            textLeft = stringResource(R.string.login),
            textRight = stringResource(R.string.sign_up),
            onClickLeft = { viewModel.send(AuthMainScreenIntent.LoginChoose) },
            onClickRight = { viewModel.send(AuthMainScreenIntent.SignUpChoose) }
        )
        LazyColumn(modifier = Modifier.weight(1f)) {
            when (state.value.isLogin) {
                true -> item {
                    AuthLoginContent(
                        viewModel = viewModel,
                        state = state,
                        navHostController = navHostController
                    )
                }

                false -> item {
                    AuthSignUpContent(viewModel = viewModel, state = state)
                }
            }
        }
        UiButton(
            state = UiButtonState.Base(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp)
                    .navigationBarsPadding()
                    .imePadding(),
                textModifier = Modifier.padding(top = 7.dp, bottom = 7.dp),
                enabled = when (state.value.isLogin) {
                    true -> state.value.isValidEmailAndPassword
                    false -> state.value.isValidSignUpInfo
                }
            ),
            text = stringResource(
                when (state.value.isLogin) {
                    true -> R.string.login_button
                    false -> R.string.sign_up_button
                }
            ),
            onClick = {
                when (state.value.isLogin) {
                    true -> login(
                        viewModel = viewModel,
                        context = context,
                        keyboardOptions = keyboardOptions,
                        navHostController = navHostController,
                        scope = scope,
                        snackBarHostState = snackBarHostState,
                        state = state
                    )

                    false -> signUp(
                        viewModel = viewModel,
                        context = context,
                        keyboardOptions = keyboardOptions,
                        navHostController = navHostController,
                        scope = scope,
                        snackBarHostState = snackBarHostState,
                        state = state
                    )
                }
            }
        )
    }
}

private fun login(
    viewModel: AuthMainScreenViewModel,
    context: Context,
    keyboardOptions: SoftwareKeyboardController?,
    navHostController: NavHostController,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    state: State<AuthMainScreenState>
) {
    keyboardOptions?.hide()
    viewModel.send(
        AuthMainScreenIntent.Login(
            onSuccess = {
                navigateToMainScreen(navHostController = navHostController)
            },
            onError = {
                scope.launch {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    snackBarHostState.showSnackbar(
                        message = context.getString(
                            state.value.errorMsgResId ?: R.string.unknown_error
                        ),
                        withDismissAction = true
                    )
                }
            }
        )
    )
}

private fun signUp(
    viewModel: AuthMainScreenViewModel,
    context: Context,
    keyboardOptions: SoftwareKeyboardController?,
    navHostController: NavHostController,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    state: State<AuthMainScreenState>
) {
    keyboardOptions?.hide()
    viewModel.send(
        AuthMainScreenIntent.SignUp(
            onSuccess = {
                navigateToMainScreen(navHostController = navHostController)
            },
            onError = {
                scope.launch {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    snackBarHostState.showSnackbar(
                        message = context.getString(
                            state.value.errorMsgResId ?: R.string.unknown_error
                        ),
                        withDismissAction = true
                    )
                }
            }
        )
    )
}