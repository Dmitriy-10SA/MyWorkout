package com.andef.myworkout.presentation.account.main.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.design.error.ui.UiErrorOverlay
import com.andef.myworkout.design.fab.state.UiFABState
import com.andef.myworkout.design.fab.ui.UiFAB
import com.andef.myworkout.design.iconbutton.ui.UiIconButton
import com.andef.myworkout.design.loading.ui.UiLoadingOverlay
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.design.snackbar.state.UiSnackBarState
import com.andef.myworkout.design.snackbar.ui.UiSnackBarHost
import com.andef.myworkout.design.topbar.ui.UiTopBar
import com.andef.myworkout.navigation.Screen
import com.andef.myworkout.presentation.account.main.AccountMainScreenIntent
import com.andef.myworkout.presentation.account.main.AccountMainScreenState
import com.andef.myworkout.presentation.account.main.AccountMainScreenViewModel
import com.andef.myworkout.ui.utils.onUnauthorizedNavigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AccountMainScreenContent(
    viewModel: AccountMainScreenViewModel,
    showLoading: MutableState<Boolean>,
    snackBarHostState: SnackbarHostState,
    state: State<AccountMainScreenState>,
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    scope: CoroutineScope
) {
    UiScaffold(
        topBar = {
            UiTopBar(
                title = stringResource(R.string.account),
                actions = {
                    UiIconButton(
                        painter = painterResource(R.drawable.logout),
                        contentDescription = stringResource(R.string.logout)
                    ) {
                        viewModel.send(AccountMainScreenIntent.Logout(onLogout = {
                            onUnauthorizedNavigate(navHostController = navHostController)
                        }))
                    }
                }
            )
        },
        floatingActionButton = {
            if (!state.value.isLoading && !state.value.isError) {
                UiFAB(
                    modifier = Modifier
                        .padding(bottom = paddingValues.calculateBottomPadding()),
                    painter = painterResource(R.drawable.edit),
                    contentDescription = stringResource(R.string.edit_account_info),
                    state = UiFABState.Base
                ) {
                    navHostController.navigate(
                        Screen.AccountScreen.ChangeInfoScreen.passParams(
                            surname = state.value.surname,
                            name = state.value.name,
                            patronymic = state.value.patronymic,
                            photo = state.value.photo ?: ""
                        )
                    )
                }
            }
        }
    ) { topPadding ->
        MainContent(
            paddingValues = PaddingValues(
                bottom = paddingValues.calculateBottomPadding(),
                top = topPadding.calculateTopPadding()
            ),
            state = state,
            viewModel = viewModel,
            navHostController = navHostController,
            scope = scope,
            snackBarHostState = snackBarHostState,
            showLoading = showLoading
        )

        UiSnackBarHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topPadding.calculateTopPadding()),
            snackBarHostState = snackBarHostState,
            state = UiSnackBarState.Error
        )
    }
}

@Composable
private fun MainContent(
    viewModel: AccountMainScreenViewModel,
    showLoading: MutableState<Boolean>,
    snackBarHostState: SnackbarHostState,
    state: State<AccountMainScreenState>,
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    scope: CoroutineScope
) {
    val context = LocalContext.current

    state.value.userInfo?.let { userInfo ->
        UserInfoContent(
            paddingValues = paddingValues,
            userInfo = userInfo,
            viewModel = viewModel,
            scope = scope,
            navHostController = navHostController,
            snackBarHostState = snackBarHostState
        )
    }

    if (showLoading.value) {
        UiLoadingOverlay(
            text = stringResource(R.string.my_workout),
            paddingValues = paddingValues
        )
    } else if (state.value.isError) {
        UiErrorOverlay(
            paddingValues = paddingValues,
            onRetry = {
                viewModel.send(
                    AccountMainScreenIntent.LoadUserInfo(
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
        )
    }
}