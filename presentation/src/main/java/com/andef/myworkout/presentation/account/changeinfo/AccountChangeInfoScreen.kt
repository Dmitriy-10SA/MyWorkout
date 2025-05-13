package com.andef.myworkout.presentation.account.changeinfo

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.ViewModelFactory
import com.andef.myworkout.design.iconbutton.ui.UiIconButton
import com.andef.myworkout.design.loading.ui.UiLoadingOverlay
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.design.snackbar.state.UiSnackBarState
import com.andef.myworkout.design.snackbar.ui.UiSnackBarHost
import com.andef.myworkout.design.topbar.ui.UiTopBar
import com.andef.myworkout.presentation.account.changeinfo.content.AccountChangeInfoScreenContent
import kotlinx.coroutines.delay

@Composable
fun AccountChangeInfoScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController,
    surname: String,
    name: String,
    patronymic: String,
    photo: String
) {
    val viewModel: AccountChangeInfoScreenViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val showLoading = remember { mutableStateOf(false) }

    Effects(
        viewModel = viewModel,
        state = state,
        showLoading = showLoading,
        surname = surname,
        name = name,
        patronymic = patronymic,
        photo = photo
    )

    UiScaffold(
        topBar = {
            UiTopBar(
                title = stringResource(R.string.change_user_info_dialog_title),
                navigationIcon = {
                    UiIconButton(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = stringResource(R.string.arrow_back)
                    ) { navHostController.popBackStack() }
                }
            )
        }
    ) { topPadding ->
        AccountChangeInfoScreenContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topPadding.calculateTopPadding() + 16.dp)
                .padding(bottom = paddingValues.calculateBottomPadding() + 1.dp)
                .padding(horizontal = 16.dp)
                .imePadding(),
            snackBarHostState = snackBarHostState,
            scope = scope,
            viewModel = viewModel,
            state = state,
            navHostController = navHostController
        )
        if (showLoading.value) {
            UiLoadingOverlay(
                text = stringResource(R.string.my_workout),
                paddingValues = PaddingValues(
                    top = topPadding.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
            )
        }
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
private fun Effects(
    viewModel: AccountChangeInfoScreenViewModel,
    state: State<AccountChangeInfoScreenState>,
    showLoading: MutableState<Boolean>,
    surname: String,
    name: String,
    patronymic: String,
    photo: String
) {
    LaunchedEffect(Unit) {
        viewModel.send(AccountChangeInfoScreenIntent.NameInput(name = name))
        viewModel.send(AccountChangeInfoScreenIntent.SurnameInput(surname = surname))
        viewModel.send(AccountChangeInfoScreenIntent.PatronymicInput(patronymic = patronymic))
        viewModel.send(AccountChangeInfoScreenIntent.PhotoInput(photo = photo))
    }

    LaunchedEffect(state.value.isLoading) {
        if (state.value.isLoading) {
            delay(350)
            showLoading.value = state.value.isLoading
        } else {
            showLoading.value = false
        }
    }
}