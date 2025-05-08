package com.andef.myworkout.presentation.auth.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.design.chooser.ui.UiChooser
import com.andef.myworkout.design.loading.UiLoadingOverlay
import com.andef.myworkout.di.viewmodel.ViewModelFactory
import com.andef.myworkout.presentation.auth.content.AuthScreenTemplate
import com.andef.myworkout.presentation.auth.content.ForgotPasswordDialog
import com.andef.myworkout.presentation.auth.content.LoginContent
import com.andef.myworkout.presentation.auth.content.SignUpContent
import kotlinx.coroutines.delay

@Composable
fun AuthScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController
) {
    val viewModel: AuthScreenViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.state.collectAsState()

    var showEmptyAuthScreenTemplate = remember { mutableStateOf(false) }
    var showForgotDialog = remember { mutableStateOf(false) }

    ShowForgotPasswordEffect(
        state = state,
        showEmptyAuthScreenTemplate = showEmptyAuthScreenTemplate,
        showForgotDialog = showForgotDialog
    )

    when (showEmptyAuthScreenTemplate.value) {
        true -> {
            AuthScreenTemplate(paddingValues = paddingValues)
            if (showForgotDialog.value) ForgotPasswordDialog(
                viewModel = viewModel,
                state = state,
                paddingValues = paddingValues
            )
        }

        false -> {
            AuthScreenTemplate(paddingValues = paddingValues) {
                AuthScreenContent(viewModel = viewModel, state = state)
            }
        }
    }
    if (state.value.isLoading && !showForgotDialog.value) {
        UiLoadingOverlay(text = stringResource(R.string.my_workout), paddingValues = paddingValues)
    }
}

@Composable
private fun ShowForgotPasswordEffect(
    state: State<AuthScreenState>,
    showEmptyAuthScreenTemplate: MutableState<Boolean>,
    showForgotDialog: MutableState<Boolean>
) {
    LaunchedEffect(state.value.isShowForgotPassword) {
        if (state.value.isShowForgotPassword) {
            showEmptyAuthScreenTemplate.value = true
            delay(800)
            showForgotDialog.value = true
        } else {
            showForgotDialog.value = false
            delay(800)
            showEmptyAuthScreenTemplate.value = false
        }
    }
}

@Composable
private fun AuthScreenContent(viewModel: AuthScreenViewModel, state: State<AuthScreenState>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        item {
            UiChooser(
                enabledLeft = !state.value.isLogin,
                enabledRight = state.value.isLogin,
                textLeft = stringResource(R.string.login),
                textRight = stringResource(R.string.sign_up),
                onClickLeft = { viewModel.send(AuthScreenIntent.LoginChoose) },
                onClickRight = { viewModel.send(AuthScreenIntent.SignUpChoose) }
            )
        }
        when (state.value.isLogin) {
            true -> item { LoginContent(viewModel = viewModel, state = state) }
            false -> item { SignUpContent(viewModel = viewModel, state = state) }
        }
    }
}