package com.andef.myworkout.presentation.auth.main.content

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.design.button.state.UiButtonState
import com.andef.myworkout.design.button.ui.UiButton
import com.andef.myworkout.navigation.Screen
import com.andef.myworkout.presentation.auth.main.AuthMainScreenState
import com.andef.myworkout.presentation.auth.main.AuthMainScreenViewModel

@Composable
fun AuthLoginContent(
    viewModel: AuthMainScreenViewModel,
    state: State<AuthMainScreenState>,
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
    Spacer(modifier = Modifier.padding(2.dp))
    UiButton(
        state = UiButtonState.ForgotPassword,
        text = stringResource(R.string.forgot_password),
        onClick = {
            navHostController.navigate(Screen.AuthScreen.ForgotPasswordScreen.route)
        }
    )
    Spacer(modifier = Modifier.padding(6.dp))
}