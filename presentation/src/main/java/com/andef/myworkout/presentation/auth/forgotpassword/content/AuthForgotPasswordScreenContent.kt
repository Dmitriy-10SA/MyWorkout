package com.andef.myworkout.presentation.auth.forgotpassword.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.design.button.state.UiButtonState
import com.andef.myworkout.design.button.ui.UiButton
import com.andef.myworkout.design.iconbutton.ui.UiIconButton
import com.andef.myworkout.design.loading.ui.UiLoadingOverlay
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.design.snackbar.state.UiSnackBarState
import com.andef.myworkout.design.snackbar.ui.UiSnackBarHost
import com.andef.myworkout.design.topbar.ui.UiTopBar
import com.andef.myworkout.presentation.auth.EmailInput
import com.andef.myworkout.presentation.auth.PasswordInput
import com.andef.myworkout.presentation.auth.forgotpassword.AuthForgotPasswordScreenIntent
import com.andef.myworkout.presentation.auth.forgotpassword.AuthForgotPasswordScreenState
import com.andef.myworkout.presentation.auth.forgotpassword.AuthForgotPasswordScreenViewModel
import com.andef.myworkout.ui.utils.navigateToMainScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AuthForgotPasswordScreenContent(
    paddingValues: PaddingValues,
    viewModel: AuthForgotPasswordScreenViewModel,
    state: State<AuthForgotPasswordScreenState>,
    showLoading: State<Boolean>,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    navHostController: NavHostController
) {
    UiScaffold(
        topBar = {
            if (!state.value.isSwitchToMainScreen) {
                UiTopBar(
                    title = stringResource(R.string.change_password_dialog_title),
                    navigationIcon = {
                        UiIconButton(
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = stringResource(R.string.arrow_back)
                        ) { navHostController.popBackStack() }
                    }
                )
            }
        }
    ) { topPadding ->
        if (!state.value.isSwitchToMainScreen) {
            MainContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = topPadding.calculateTopPadding() + 16.dp)
                    .padding(bottom = paddingValues.calculateBottomPadding() + 1.dp)
                    .padding(horizontal = 16.dp)
                    .imePadding(),
                viewModel = viewModel,
                state = state,
                scope = scope,
                snackBarHostState = snackBarHostState,
                navHostController = navHostController
            )
        }

        if (showLoading.value) {
            UiLoadingOverlay(
                text = stringResource(R.string.my_workout),
                paddingValues = paddingValues
            )
        }
        UiSnackBarHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topPadding.calculateTopPadding()),
            snackBarHostState = snackBarHostState,
            state = UiSnackBarState.Error
        )
    }
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    viewModel: AuthForgotPasswordScreenViewModel,
    state: State<AuthForgotPasswordScreenState>,
    navHostController: NavHostController,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState
) {
    val keyboardOptions = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn {
                item { EmailInput(viewModel = viewModel, state = state) }
                item { Spacer(modifier = Modifier.padding(6.dp)) }
                item {
                    PasswordInput(
                        viewModel = viewModel,
                        state = state,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done
                        )
                    )
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
                enabled = state.value.isValidEmailAndPassword
            ),
            text = stringResource(R.string.change),
            onClick = {
                keyboardOptions?.hide()
                viewModel.send(
                    AuthForgotPasswordScreenIntent.ChangePassword(
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
        )
    }
}