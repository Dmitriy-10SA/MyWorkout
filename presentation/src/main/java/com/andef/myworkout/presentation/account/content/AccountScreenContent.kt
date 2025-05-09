package com.andef.myworkout.presentation.account.content

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.res.stringResource
import com.andef.myworkout.R
import com.andef.myworkout.design.loading.UiLoadingOverlay
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.design.snackbar.state.UiSnackBarState
import com.andef.myworkout.design.snackbar.ui.UiSnackBarHost
import com.andef.myworkout.design.topbar.ui.UiTopBar
import com.andef.myworkout.presentation.account.main.AccountScreenState

@Composable
fun AccountScreenContent(
    snackBarHostState: SnackbarHostState,
    showLoading: MutableState<Boolean>,
    state: State<AccountScreenState>,
    paddingValues: PaddingValues
) {
    UiScaffold(
        topBar = {
            UiTopBar(
                title = stringResource(R.string.account)
            )
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