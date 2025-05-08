package com.andef.myworkout.presentation.auth.content

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.andef.myworkout.R
import com.andef.myworkout.design.iconbutton.ui.UiIconButton
import com.andef.myworkout.design.input.state.UiInputState
import com.andef.myworkout.design.input.ui.UiInput
import com.andef.myworkout.presentation.auth.main.AuthScreenIntent
import com.andef.myworkout.presentation.auth.main.AuthScreenState
import com.andef.myworkout.presentation.auth.main.AuthScreenViewModel

@Composable
fun EmailInput(viewModel: AuthScreenViewModel, state: State<AuthScreenState>) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.email,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        onValueChange = { viewModel.send(AuthScreenIntent.EmailInput(email = it)) },
        placeholderText = stringResource(R.string.email_hint),
        upText = stringResource(R.string.email)
    )
}

@Composable
fun PasswordInput(
    viewModel: AuthScreenViewModel,
    state: State<AuthScreenState>,
    keyboardOptions: KeyboardOptions
) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.password,
        onValueChange = { viewModel.send(AuthScreenIntent.PasswordInput(password = it)) },
        visualTransformation = if (state.value.isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = {
            UiIconButton(
                painter = if (state.value.isPasswordVisible) {
                    painterResource(R.drawable.visibility)
                } else {
                    painterResource(R.drawable.visibility_off)
                },
                contentDescription = if (state.value.isPasswordVisible) {
                    stringResource(R.string.show_password)
                } else {
                    stringResource(R.string.not_show_password)
                },
                onClick = { viewModel.send(AuthScreenIntent.PasswordVisibleChange) }
            )
        },
        keyboardOptions = keyboardOptions,
        placeholderText = stringResource(R.string.password_hint),
        upText = stringResource(R.string.password)
    )
}