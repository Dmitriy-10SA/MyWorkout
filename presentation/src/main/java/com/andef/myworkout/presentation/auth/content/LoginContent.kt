package com.andef.myworkout.presentation.auth.content

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.design.button.state.UiButtonState
import com.andef.myworkout.design.button.ui.UiButton
import com.andef.myworkout.presentation.auth.main.AuthScreenIntent
import com.andef.myworkout.presentation.auth.main.AuthScreenState
import com.andef.myworkout.presentation.auth.main.AuthScreenViewModel
import com.andef.myworkout.ui.utils.navigateToMainScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginContent(
    viewModel: AuthScreenViewModel,
    state: State<AuthScreenState>,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    navHostController: NavHostController
) {
    Spacer(modifier = Modifier.padding(10.dp))
    EmailInput(viewModel = viewModel, state = state)
    Spacer(modifier = Modifier.padding(6.dp))
    PasswordInput(
        viewModel = viewModel,
        state = state,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        )
    )
    Spacer(modifier = Modifier.padding(4.dp))
    Buttons(
        viewModel = viewModel,
        state = state,
        snackBarHostState = snackBarHostState,
        scope = scope,
        navHostController = navHostController
    )
}


@Composable
private fun Buttons(
    viewModel: AuthScreenViewModel,
    state: State<AuthScreenState>,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val keyboardOptions = LocalSoftwareKeyboardController.current

    UiButton(
        state = UiButtonState.ForgotPassword,
        text = stringResource(R.string.forgot_password),
        onClick = { viewModel.send(AuthScreenIntent.ShowForgotPasswordVisibleChange) }
    )
    Spacer(modifier = Modifier.padding(8.dp))
    UiButton(
        state = UiButtonState.Base(
            modifier = Modifier.fillMaxWidth(),
            textModifier = Modifier.padding(top = 7.dp, bottom = 7.dp),
            enabled = state.value.isValidEmailAndPassword
        ),
        text = stringResource(R.string.login_button),
        onClick = {
            keyboardOptions?.hide()
            viewModel.send(
                AuthScreenIntent.Login(
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