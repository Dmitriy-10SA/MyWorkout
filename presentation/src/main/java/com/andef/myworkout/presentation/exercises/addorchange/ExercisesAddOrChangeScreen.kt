package com.andef.myworkout.presentation.exercises.addorchange

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.ViewModelFactory
import com.andef.myworkout.design.error.ui.UiErrorOverlay
import com.andef.myworkout.design.iconbutton.ui.UiIconButton
import com.andef.myworkout.design.loading.ui.UiLoadingOverlay
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.design.snackbar.state.UiSnackBarState
import com.andef.myworkout.design.snackbar.ui.UiSnackBarHost
import com.andef.myworkout.design.topbar.ui.UiTopBar
import com.andef.myworkout.presentation.exercises.addorchange.content.ExercisesAddOrChangeContent
import com.andef.myworkout.ui.utils.onUnauthorizedNavigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisesAddOrChangeScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController,
    isAdd: Boolean
) {
    val viewModel: ExercisesAddOrChangeScreenViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.state.collectAsState()

    val typeExpanded = remember { mutableStateOf(false) }
    val groupExpanded = remember { mutableStateOf(false) }
    val muscleExpanded = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val showLoading = remember { mutableStateOf(false) }

    val context = LocalContext.current

    Effects(
        viewModel = viewModel,
        scope = scope, snackBarHostState = snackBarHostState,
        context = context,
        navHostController = navHostController,
        state = state,
        showLoading = showLoading
    )

    UiScaffold(
        topBar = {
            UiTopBar(
                title = stringResource(R.string.exercise),
                navigationIcon = {
                    UiIconButton(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = stringResource(R.string.exercises)
                    ) { navHostController.popBackStack() }
                }
            )
        }
    ) { topPadding ->
        state.value.types?.let { types ->
            state.value.groups?.let { groups ->
                ExercisesAddOrChangeContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = paddingValues.calculateBottomPadding() + 1.dp)
                        .padding(top = topPadding.calculateTopPadding() + 16.dp)
                        .padding(horizontal = 16.dp),
                    viewModel = viewModel,
                    state = state,
                    types = types,
                    groups = groups,
                    typeExpanded = typeExpanded,
                    groupExpanded = groupExpanded,
                    muscleExpanded = muscleExpanded,
                    scope = scope,
                    navHostController = navHostController,
                    snackBarHostState = snackBarHostState
                )
            }
        }
        if (showLoading.value) {
            UiLoadingOverlay(
                text = stringResource(R.string.my_workout),
                paddingValues = PaddingValues(
                    top = topPadding.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                )
            )
        } else if (state.value.isError) {
            UiErrorOverlay(
                paddingValues = paddingValues,
                onRetry = {
                    viewModel.send(
                        ExercisesAddOrChangeScreenIntent.LoadAll(
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
    viewModel: ExercisesAddOrChangeScreenViewModel,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    context: Context,
    navHostController: NavHostController,
    state: State<ExercisesAddOrChangeScreenState>,
    showLoading: MutableState<Boolean>
) {
    LaunchedEffect(Unit) {
        viewModel.send(
            ExercisesAddOrChangeScreenIntent.LoadAll(
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

    LaunchedEffect(state.value.isLoading) {
        if (state.value.isLoading) {
            delay(600)
            showLoading.value = state.value.isLoading
        } else {
            showLoading.value = false
        }
    }
}