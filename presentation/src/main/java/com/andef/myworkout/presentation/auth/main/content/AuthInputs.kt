package com.andef.myworkout.presentation.auth.main.content

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
import com.andef.myworkout.presentation.auth.main.AuthMainScreenIntent
import com.andef.myworkout.presentation.auth.main.AuthMainScreenState
import com.andef.myworkout.presentation.auth.main.AuthMainScreenViewModel
import com.andef.myworkout.ui.utils.isValidEmail
import com.andef.myworkout.ui.utils.isValidPassword

@Composable
fun EmailInput(viewModel: AuthMainScreenViewModel, state: State<AuthMainScreenState>) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.email,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        onValueChange = {
            viewModel.send(AuthMainScreenIntent.EmailInput(email = it.trim()))
        },
        placeholderText = stringResource(R.string.email_hint),
        upText = stringResource(R.string.email),
        isError = !state.value.email.isValidEmail() && allFieldsNotEmpty(state),
    )
}

@Composable
fun PasswordInput(
    viewModel: AuthMainScreenViewModel,
    state: State<AuthMainScreenState>,
    keyboardOptions: KeyboardOptions
) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.password,
        onValueChange = {
            viewModel.send(AuthMainScreenIntent.PasswordInput(password = it.trim()))
        },
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
                onClick = { viewModel.send(AuthMainScreenIntent.PasswordVisibleChange) }
            )
        },
        keyboardOptions = keyboardOptions,
        placeholderText = stringResource(R.string.password_hint),
        upText = stringResource(R.string.password),
        isError = !state.value.password.isValidPassword() && allFieldsNotEmpty(state)
    )
}

@Composable
fun SurnameInput(viewModel: AuthMainScreenViewModel, state: State<AuthMainScreenState>) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.surname,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        onValueChange = {
            viewModel.send(AuthMainScreenIntent.SurnameInput(surname = it.trim()))
        },
        placeholderText = stringResource(R.string.surname_hint),
        upText = stringResource(R.string.surname),
        isError = !state.value.surname.isNotEmpty() && allFieldsNotEmpty(state)
    )
}

@Composable
fun NameInput(viewModel: AuthMainScreenViewModel, state: State<AuthMainScreenState>) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.name,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        onValueChange = {
            viewModel.send(AuthMainScreenIntent.NameInput(name = it.trim()))
        },
        placeholderText = stringResource(R.string.name_hint),
        upText = stringResource(R.string.name),
        isError = !state.value.name.isNotEmpty() && allFieldsNotEmpty(state)
    )
}

@Composable
fun PatronymicInput(viewModel: AuthMainScreenViewModel, state: State<AuthMainScreenState>) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.patronymic,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        onValueChange = {
            viewModel.send(AuthMainScreenIntent.PatronymicInput(patronymic = it.trim()))
        },
        placeholderText = stringResource(R.string.patronymic_hint),
        upText = stringResource(R.string.patronymic),
        isError = !state.value.patronymic.isNotEmpty() && allFieldsNotEmpty(state)
    )
}

private fun allFieldsNotEmpty(state: State<AuthMainScreenState>): Boolean {
    return state.value.patronymic.isNotEmpty() && state.value.name.isNotEmpty() &&
            state.value.surname.isNotEmpty() && state.value.password.isNotEmpty() &&
            state.value.email.isNotEmpty()
}