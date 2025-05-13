package com.andef.myworkout.oldPresentation.calendar.main

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.ViewModelFactory
import com.andef.myworkout.oldPresentation.calendar.main.content.CalendarScreenContent
import com.andef.myworkout.ui.utils.onUnauthorizedNavigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController
) {
    val viewModel: CalendarScreenViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val currentSelectedDate = rememberSaveable { mutableStateOf(LocalDate.now()) }

    val context = LocalContext.current
    val showLoading = remember { mutableStateOf(false) }

    Effects(
        currentSelectedDate = currentSelectedDate,
        viewModel = viewModel,
        navHostController = navHostController,
        scope = scope,
        snackBarHostState = snackBarHostState,
        context = context,
        state = state,
        showLoading = showLoading
    )

    CalendarScreenContent(
        currentSelectedDate = currentSelectedDate,
        snackBarHostState = snackBarHostState,
        state = state,
        showLoading = showLoading,
        paddingValues = paddingValues,
        onRetry = {
            loadWorkout(
                viewModel = viewModel,
                currentSelectedDate = currentSelectedDate,
                scope = scope,
                snackBarHostState = snackBarHostState,
                context = context,
                navHostController = navHostController
            )
        }
    )
}

@Composable
private fun Effects(
    state: State<CalendarScreenState>,
    showLoading: MutableState<Boolean>,
    currentSelectedDate: State<LocalDate>,
    viewModel: CalendarScreenViewModel,
    navHostController: NavHostController,
    scope: CoroutineScope,
    context: Context,
    snackBarHostState: SnackbarHostState
) {
    LaunchedEffect(currentSelectedDate.value) {
        loadWorkout(
            viewModel = viewModel,
            currentSelectedDate = currentSelectedDate,
            scope = scope,
            snackBarHostState = snackBarHostState,
            context = context,
            navHostController = navHostController
        )
    }

    LaunchedEffect(state.value.isLoading) {
        if (state.value.isLoading) {
            delay(600)
            showLoading.value = state.value.isLoading
        } else {
            showLoading.value = false
        }
    }
}

private fun loadWorkout(
    viewModel: CalendarScreenViewModel,
    currentSelectedDate: State<LocalDate>,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    context: Context,
    navHostController: NavHostController
) {
    viewModel.send(
        CalendarScreenIntent.LoadWorkout(
            date = currentSelectedDate.value,
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