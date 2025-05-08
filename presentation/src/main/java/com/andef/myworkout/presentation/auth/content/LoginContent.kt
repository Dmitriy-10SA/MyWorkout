package com.andef.myworkout.presentation.auth.content

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.andef.myworkout.R
import com.andef.myworkout.design.button.state.UiButtonState
import com.andef.myworkout.design.button.ui.UiButton
import com.andef.myworkout.presentation.auth.main.AuthScreenIntent
import com.andef.myworkout.presentation.auth.main.AuthScreenState
import com.andef.myworkout.presentation.auth.main.AuthScreenViewModel

@Composable
fun LoginContent(viewModel: AuthScreenViewModel, state: State<AuthScreenState>) {
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
    Buttons(viewModel = viewModel, state = state)
}

@Composable
private fun Buttons(viewModel: AuthScreenViewModel, state: State<AuthScreenState>) {
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
        onClick = { viewModel.send(AuthScreenIntent.Login) }
    )
}