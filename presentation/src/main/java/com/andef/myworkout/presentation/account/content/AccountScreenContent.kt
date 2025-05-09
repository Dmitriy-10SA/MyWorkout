package com.andef.myworkout.presentation.account.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.design.button.state.UiButtonState
import com.andef.myworkout.design.button.ui.UiButton
import com.andef.myworkout.design.dialog.ui.UiDialog
import com.andef.myworkout.design.fab.state.UiFABState
import com.andef.myworkout.design.fab.ui.UiFAB
import com.andef.myworkout.design.iconbutton.ui.UiIconButton
import com.andef.myworkout.design.input.state.UiInputState
import com.andef.myworkout.design.input.ui.UiInput
import com.andef.myworkout.design.loading.UiLoadingOverlay
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.design.snackbar.state.UiSnackBarState
import com.andef.myworkout.design.snackbar.ui.UiSnackBarHost
import com.andef.myworkout.design.topbar.ui.UiTopBar
import com.andef.myworkout.presentation.account.main.AccountScreenIntent
import com.andef.myworkout.presentation.account.main.AccountScreenState
import com.andef.myworkout.presentation.account.main.AccountScreenViewModel
import com.andef.myworkout.ui.utils.onUnauthorizedNavigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AccountScreenContent(
    viewModel: AccountScreenViewModel,
    snackBarHostState: SnackbarHostState,
    showLoading: MutableState<Boolean>,
    state: State<AccountScreenState>,
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    scope: CoroutineScope
) {
    if (state.value.showEditInfoDialog) {
        UiDialog(
            text = {
                ChangeInfoDialogContent(
                    viewModel = viewModel,
                    state = state,
                    navHostController = navHostController,
                    scope = scope,
                    snackBarHostState = snackBarHostState
                )
            },
            dismiss = { viewModel.send(AccountScreenIntent.ChangeEditInfoDialogVisible) }
        )
    }
    UiScaffold(
        topBar = {
            TopBarContent(viewModel = viewModel, navHostController = navHostController)
        },
        floatingActionButton = {
            FABContent(viewModel = viewModel, paddingValues = paddingValues)
        },
        snackBarHost = {
            UiSnackBarHost(
                snackBarHostState = snackBarHostState,
                state = UiSnackBarState.Error
            )
        }
    ) { topPadding ->
        Content(
            state = state,
            showLoading = showLoading,
            topPadding = topPadding,
            paddingValues = paddingValues
        )
    }
}

@Composable
private fun Content(
    state: State<AccountScreenState>,
    paddingValues: PaddingValues,
    topPadding: PaddingValues,
    showLoading: State<Boolean>
) {
    state.value.userInfo?.let { userInfo ->
        UserInfoContent(
            paddingValues = PaddingValues(
                bottom = paddingValues.calculateBottomPadding(),
                top = topPadding.calculateTopPadding()
            ),
            userInfo = userInfo
        )
    }

    if (showLoading.value) {
        UiLoadingOverlay(
            text = stringResource(R.string.my_workout),
            paddingValues = PaddingValues(
                bottom = paddingValues.calculateBottomPadding(),
                top = topPadding.calculateTopPadding()
            )
        )
    }
}

@Composable
private fun ChangeInfoDialogContent(
    viewModel: AccountScreenViewModel,
    state: State<AccountScreenState>,
    navHostController: NavHostController,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState
) {
    val context = LocalContext.current

    LazyColumn {
        item {
            SurnameInput(viewModel = viewModel, state = state)
            Spacer(modifier = Modifier.padding(3.dp))
            NameInput(viewModel = viewModel, state = state)
            Spacer(modifier = Modifier.padding(3.dp))
            PatronymicInput(viewModel = viewModel, state = state)
            Spacer(modifier = Modifier.padding(4.dp))
            Row {
                UiButton(
                    state = UiButtonState.Base(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 2.dp),
                        textModifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                    ),
                    text = stringResource(R.string.cancel)
                ) {
                    viewModel.send(AccountScreenIntent.ChangeEditInfoDialogVisible)
                }
                UiButton(
                    state = UiButtonState.Base(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 2.dp),
                        enabled = state.value.isValidInfoForChange,
                        textModifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                    ),
                    text = stringResource(R.string.change_user_info)
                ) {
                    viewModel.send(AccountScreenIntent.ChangeEditInfoDialogVisible)
                    viewModel.send(
                        AccountScreenIntent.ChangeUserInfo(
                            onUnauthorized = {
                                scope.launch {
                                    snackBarHostState.currentSnackbarData?.dismiss()
                                    snackBarHostState.showSnackbar(
                                        message = context.getString(R.string.unauthorized),
                                        withDismissAction = true
                                    )
                                    onUnauthorizedNavigate(navHostController = navHostController)
                                }
                            }
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun FABContent(viewModel: AccountScreenViewModel, paddingValues: PaddingValues) {
    UiFAB(
        modifier = Modifier
            .padding(bottom = paddingValues.calculateBottomPadding()),
        painter = painterResource(R.drawable.edit),
        contentDescription = stringResource(R.string.edit_account_info),
        state = UiFABState.Base
    ) {
        viewModel.send(AccountScreenIntent.ChangeEditInfoDialogVisible)
    }
}

@Composable
private fun TopBarContent(viewModel: AccountScreenViewModel, navHostController: NavHostController) {
    UiTopBar(
        title = stringResource(R.string.account),
        actions = {
            UiIconButton(
                painter = painterResource(R.drawable.logout),
                contentDescription = stringResource(R.string.logout)
            ) {
                viewModel.send(AccountScreenIntent.Logout(onLogout = {
                    onUnauthorizedNavigate(navHostController = navHostController)
                }))
            }
        }
    )
}

@Composable
private fun SurnameInput(viewModel: AccountScreenViewModel, state: State<AccountScreenState>) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.surname,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        onValueChange = { viewModel.send(AccountScreenIntent.SurnameInput(surname = it)) },
        placeholderText = stringResource(R.string.surname_hint),
        upText = stringResource(R.string.surname)
    )
}

@Composable
private fun NameInput(viewModel: AccountScreenViewModel, state: State<AccountScreenState>) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.name,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        onValueChange = { viewModel.send(AccountScreenIntent.NameInput(name = it)) },
        placeholderText = stringResource(R.string.name_hint),
        upText = stringResource(R.string.name)
    )
}

@Composable
private fun PatronymicInput(viewModel: AccountScreenViewModel, state: State<AccountScreenState>) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.patronymic,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        onValueChange = { viewModel.send(AccountScreenIntent.PatronymicInput(patronymic = it)) },
        placeholderText = stringResource(R.string.patronymic_hint),
        upText = stringResource(R.string.patronymic)
    )
}