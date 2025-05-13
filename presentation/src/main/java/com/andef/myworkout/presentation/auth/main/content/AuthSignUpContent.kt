package com.andef.myworkout.presentation.auth.main.content

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.andef.myworkout.presentation.auth.main.AuthMainScreenState
import com.andef.myworkout.presentation.auth.main.AuthMainScreenViewModel

@Composable
fun AuthSignUpContent(viewModel: AuthMainScreenViewModel, state: State<AuthMainScreenState>) {
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
    Spacer(modifier = Modifier.padding(6.dp))
}