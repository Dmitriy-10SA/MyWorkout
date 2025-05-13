package com.andef.myworkout.oldPresentation.auth.main.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LinearProgressIndicator
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
import com.andef.myworkout.oldPresentation.auth.main.AuthScreenIntent
import com.andef.myworkout.oldPresentation.auth.main.AuthScreenState
import com.andef.myworkout.oldPresentation.auth.main.AuthScreenViewModel
import com.andef.myworkout.ui.theme.Black
import com.andef.myworkout.ui.theme.Gray
import com.andef.myworkout.ui.utils.navigateToMainScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ForgotPasswordDialog(
    viewModel: AuthScreenViewModel,
    state: State<AuthScreenState>,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    navHostController: NavHostController
) {

//    UiDialog(
//        title = {
//            Text(
//                modifier = Modifier.fillMaxWidth(),
//                textAlign = TextAlign.Center,
//                text = stringResource(R.string.change_password_dialog_title)
//            )
//        },
//        content = {
//            DialogContent(
//                viewModel = viewModel,
//                state = state,
//                snackBarHostState = snackBarHostState,
//                scope = scope,
//                navHostController = navHostController
//            )
//        },
//        dismiss = { viewModel.send(AuthScreenIntent.ShowForgotPasswordVisibleChange) }
//    )

}

@Composable
private fun DialogContent(
    viewModel: AuthScreenViewModel,
    state: State<AuthScreenState>,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    navHostController: NavHostController
) {
    if (state.value.isLoading) {
        Column {
            Spacer(modifier = Modifier.padding(6.dp))
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = Gray,
                trackColor = Black
            )
        }
    } else {
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
            DialogButtons(
                viewModel = viewModel,
                state = state,
                snackBarHostState = snackBarHostState,
                scope = scope,
                navHostController = navHostController
            )
        }
    }
}

@Composable
private fun DialogButtons(
    viewModel: AuthScreenViewModel,
    state: State<AuthScreenState>,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val keyboardOptions = LocalSoftwareKeyboardController.current

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
            onClick = {
                keyboardOptions?.hide()
                viewModel.send(
                    AuthScreenIntent.PasswordChange(
                        onSuccess = {
                            viewModel.send(AuthScreenIntent.ShowForgotPasswordVisibleChange)
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