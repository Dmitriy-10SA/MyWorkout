package com.andef.myworkout.oldPresentation.exercises.addorchange

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.ViewModelFactory
import com.andef.myworkout.design.button.state.UiButtonState
import com.andef.myworkout.design.button.ui.UiButton
import com.andef.myworkout.design.iconbutton.ui.UiIconButton
import com.andef.myworkout.design.input.state.UiInputState
import com.andef.myworkout.design.input.ui.UiInput
import com.andef.myworkout.design.menu.ui.UiMenu
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.design.snackbar.state.UiSnackBarState
import com.andef.myworkout.design.snackbar.ui.UiSnackBarHost
import com.andef.myworkout.design.topbar.ui.UiTopBar
import com.andef.myworkout.ui.utils.onUnauthorizedNavigate
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisesAddOrChangeScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController,
    isAdd: Boolean
) {
    val viewModel: ExerciseAddOrChangeScreenViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.state.collectAsState()

    val typeExpanded = remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.send(
            ExercisesAddOrChangeScreenIntent.LoadTypes(
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
        UiSnackBarHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topPadding.calculateTopPadding()),
            snackBarHostState = snackBarHostState,
            state = UiSnackBarState.Error
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = paddingValues.calculateBottomPadding() + 1.dp)
                .padding(top = topPadding.calculateTopPadding() + 16.dp)
                .padding(horizontal = 16.dp)
        ) {
            item {
                UiInput(
                    modifier = Modifier.fillMaxWidth(),
                    state = UiInputState.Base,
                    value = state.value.name,
                    onValueChange = {
                        viewModel.send(ExercisesAddOrChangeScreenIntent.NameInput(name = it))
                    },
                    upText = stringResource(R.string.exercise_name),
                    placeholderText = stringResource(R.string.exercise_name_hint),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
                Spacer(modifier = Modifier.padding(6.dp))
            }
            item {
                UiInput(
                    modifier = Modifier.fillMaxWidth(),
                    state = UiInputState.Base,
                    value = state.value.description,
                    onValueChange = {
                        viewModel.send(
                            ExercisesAddOrChangeScreenIntent.DescriptionInput(description = it)
                        )
                    },
                    upText = stringResource(R.string.exercise_description),
                    placeholderText = stringResource(R.string.exercise_description_hint),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )
                Spacer(modifier = Modifier.padding(6.dp))
            }
            item {
                state.value.types?.let { types ->
                    UiMenu(
                        upText = stringResource(R.string.exercise_type),
                        expanded = typeExpanded.value,
                        onExpandedChange = { typeExpanded.value = it },
                        onDismiss = { typeExpanded.value = false },
                        value = state.value.type?.name ?: stringResource(
                            R.string.touch_for_choose
                        ),
                        items = types,
                        itemToString = { it.name },
                        onItemClick = {
                            viewModel.send(
                                ExercisesAddOrChangeScreenIntent.TypeInput(
                                    type = it
                                )
                            )
                            typeExpanded.value = false
                        }
                    )
                    Spacer(modifier = Modifier.padding(7.dp))
                }
            }
            item {
                UiButton(
                    state = UiButtonState.Chooser(
                        modifier = Modifier.fillMaxWidth(),
                        textModifier = Modifier.padding(vertical = 4.dp),
                        icon = painterResource(R.drawable.muscle),
                        contentDescription = stringResource(R.string.muscle_hint)
                    ),
                    text = stringResource(R.string.muscle_choose)
                ) {
                    TODO()
                }
                Spacer(modifier = Modifier.padding(7.dp))
            }
            item {
                UiButton(
                    state = UiButtonState.Chooser(
                        modifier = Modifier.fillMaxWidth(),
                        textModifier = Modifier.padding(vertical = 4.dp),
                        icon = painterResource(R.drawable.videocam),
                        contentDescription = stringResource(R.string.video_hint)
                    ),
                    text = stringResource(R.string.video_choose)
                ) {
                    TODO()
                }
                Spacer(modifier = Modifier.padding(7.dp))
            }
            item {
                UiButton(
                    state = UiButtonState.Base(
                        modifier = Modifier.fillMaxWidth(),
                        textModifier = Modifier.padding(vertical = 4.dp),
                        enabled = state.value.isValidInfoForAdd
                    ),
                    text = stringResource(R.string.save)
                ) { TODO() }
            }
        }
    }
}