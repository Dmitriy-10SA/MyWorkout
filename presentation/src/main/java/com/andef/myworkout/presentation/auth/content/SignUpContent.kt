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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.design.button.state.UiButtonState
import com.andef.myworkout.design.button.ui.UiButton
import com.andef.myworkout.design.input.state.UiInputState
import com.andef.myworkout.design.input.ui.UiInput
import com.andef.myworkout.presentation.auth.main.AuthScreenIntent
import com.andef.myworkout.presentation.auth.main.AuthScreenState
import com.andef.myworkout.presentation.auth.main.AuthScreenViewModel
import com.andef.myworkout.ui.utils.navigateToMainScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun SignUpContent(
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
            imeAction = ImeAction.Next
        )
    )
    Spacer(modifier = Modifier.padding(6.dp))
    SurnameInput(viewModel = viewModel, state = state)
    Spacer(modifier = Modifier.padding(6.dp))
    NameInput(viewModel = viewModel, state = state)
    Spacer(modifier = Modifier.padding(6.dp))
    PatronymicInput(viewModel = viewModel, state = state)
    Spacer(modifier = Modifier.padding(8.dp))
    SignUpButton(
        viewModel = viewModel,
        state = state,
        snackBarHostState = snackBarHostState,
        scope = scope,
        navHostController = navHostController
    )
}

@Composable
private fun SurnameInput(viewModel: AuthScreenViewModel, state: State<AuthScreenState>) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.surname,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        onValueChange = { viewModel.send(AuthScreenIntent.SurnameInput(surname = it)) },
        placeholderText = stringResource(R.string.surname_hint),
        upText = stringResource(R.string.surname)
    )
}

@Composable
private fun NameInput(viewModel: AuthScreenViewModel, state: State<AuthScreenState>) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.name,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        onValueChange = { viewModel.send(AuthScreenIntent.NameInput(name = it)) },
        placeholderText = stringResource(R.string.name_hint),
        upText = stringResource(R.string.name)
    )
}

@Composable
private fun PatronymicInput(viewModel: AuthScreenViewModel, state: State<AuthScreenState>) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.patronymic,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        onValueChange = { viewModel.send(AuthScreenIntent.PatronymicInput(patronymic = it)) },
        placeholderText = stringResource(R.string.patronymic_hint),
        upText = stringResource(R.string.patronymic)
    )
}

@Composable
private fun SignUpButton(
    viewModel: AuthScreenViewModel,
    state: State<AuthScreenState>,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    navHostController: NavHostController
) {
    val context = LocalContext.current

    UiButton(
        state = UiButtonState.Base(
            modifier = Modifier.fillMaxWidth(),
            textModifier = Modifier.padding(top = 7.dp, bottom = 7.dp),
            enabled = state.value.isValidSignUpInfo
        ),
        text = stringResource(R.string.sign_up_button),
        onClick = {
            viewModel.send(
                AuthScreenIntent.SignUp(
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