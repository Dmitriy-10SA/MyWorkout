package com.andef.myworkout.presentation.account.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.design.fab.state.UiFABState
import com.andef.myworkout.design.fab.ui.UiFAB
import com.andef.myworkout.design.iconbutton.ui.UiIconButton
import com.andef.myworkout.design.loading.UiLoadingOverlay
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.design.snackbar.state.UiSnackBarState
import com.andef.myworkout.design.snackbar.ui.UiSnackBarHost
import com.andef.myworkout.design.topbar.ui.UiTopBar
import com.andef.myworkout.presentation.account.main.AccountScreenIntent
import com.andef.myworkout.presentation.account.main.AccountScreenState
import com.andef.myworkout.presentation.account.main.AccountScreenViewModel
import com.andef.myworkout.ui.utils.onUnauthorizedNavigate

@Composable
fun AccountScreenContent(
    viewModel: AccountScreenViewModel,
    snackBarHostState: SnackbarHostState,
    showLoading: MutableState<Boolean>,
    state: State<AccountScreenState>,
    paddingValues: PaddingValues,
    navHostController: NavHostController
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
                        viewModel.send(AccountScreenIntent.Logout(onLogout = {
                            onUnauthorizedNavigate(navHostController = navHostController)
                        }))
                    }
                }
            )
        },
        floatingActionButton = {
            UiFAB(
                modifier = Modifier
                    .padding(bottom = paddingValues.calculateBottomPadding()),
                painter = painterResource(R.drawable.edit),
                contentDescription = stringResource(R.string.edit_account_info),
                state = UiFABState.Base
            ) {
                TODO()
            }
        },
        snackBarHost = {
            UiSnackBarHost(
                snackBarHostState = snackBarHostState,
                state = UiSnackBarState.Error
            )
        }
    ) { topPadding ->
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
}