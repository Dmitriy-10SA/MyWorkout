package com.andef.myworkout.presentation.auth.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.andef.myworkout.R
import com.andef.myworkout.design.button.state.UiButtonState
import com.andef.myworkout.design.button.ui.UiButton
import com.andef.myworkout.design.dialog.ui.UiDialog
import com.andef.myworkout.design.loading.UiLoadingOverlay
import com.andef.myworkout.presentation.auth.main.AuthScreenIntent
import com.andef.myworkout.presentation.auth.main.AuthScreenState
import com.andef.myworkout.presentation.auth.main.AuthScreenViewModel

@Composable
fun ForgotPasswordDialog(
    paddingValues: PaddingValues,
    viewModel: AuthScreenViewModel,
    state: State<AuthScreenState>
) {
    if (state.value.isLoading) {
        UiLoadingOverlay(paddingValues = paddingValues)
    } else {
        UiDialog(
            title = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.change_password_dialog_title)
                )
            },
            text = { DialogContent(viewModel = viewModel, state = state) },
            dismiss = { viewModel.send(AuthScreenIntent.ShowForgotPasswordVisibleChange) }
        )
    }
}

@Composable
private fun DialogContent(viewModel: AuthScreenViewModel, state: State<AuthScreenState>) {
    Column {
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
        Spacer(modifier = Modifier.padding(8.dp))
        DialogButtons(viewModel = viewModel, state = state)
    }
}

@Composable
private fun DialogButtons(viewModel: AuthScreenViewModel, state: State<AuthScreenState>) {
    Row {
        UiButton(
            state = UiButtonState.Base(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 2.dp),
                textModifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            ),
            text = stringResource(R.string.cancel),
            onClick = {
                viewModel.send(AuthScreenIntent.ShowForgotPasswordVisibleChange)
            }
        )
        UiButton(
            state = UiButtonState.Base(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 2.dp),
                enabled = state.value.isValidEmailAndPassword,
                textModifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
            ),
            text = stringResource(R.string.ok),
            onClick = { viewModel.send(AuthScreenIntent.PasswordChange) }
        )
    }
}