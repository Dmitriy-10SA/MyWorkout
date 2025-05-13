package com.andef.myworkout.presentation.exercises.addorchange.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.design.button.state.UiButtonState
import com.andef.myworkout.design.button.ui.UiButton
import com.andef.myworkout.design.input.state.UiInputState
import com.andef.myworkout.design.input.ui.UiInput
import com.andef.myworkout.design.menu.ui.UiMenu
import com.andef.myworkout.domain.exercise.entities.ExerciseType
import com.andef.myworkout.domain.exercise.entities.Group
import com.andef.myworkout.presentation.exercises.addorchange.ExercisesAddOrChangeScreenIntent
import com.andef.myworkout.presentation.exercises.addorchange.ExercisesAddOrChangeScreenState
import com.andef.myworkout.presentation.exercises.addorchange.ExercisesAddOrChangeScreenViewModel
import com.andef.myworkout.ui.utils.onUnauthorizedNavigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ExercisesAddOrChangeContent(
    modifier: Modifier = Modifier,
    viewModel: ExercisesAddOrChangeScreenViewModel,
    state: State<ExercisesAddOrChangeScreenState>,
    types: List<ExerciseType>,
    groups: List<Group>,
    typeExpanded: MutableState<Boolean>,
    groupExpanded: MutableState<Boolean>,
    muscleExpanded: MutableState<Boolean>,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val keyboardOptions = LocalSoftwareKeyboardController.current

    Column(modifier = modifier) {
        MainContent(
            modifier = Modifier.weight(1f),
            types = types,
            groups = groups,
            typeExpanded = typeExpanded,
            groupExpanded = groupExpanded,
            muscleExpanded = muscleExpanded,
            viewModel = viewModel,
            state = state
        )
        UiButton(
            state = UiButtonState.Base(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp)
                    .navigationBarsPadding()
                    .imePadding(),
                textModifier = Modifier.padding(top = 7.dp, bottom = 7.dp),
                enabled = state.value.isValidInfoForAdd
            ),
            text = stringResource(R.string.save),
            onClick = {
                keyboardOptions?.hide()
                viewModel.send(
                    ExercisesAddOrChangeScreenIntent.AddExercise(
                        onSuccess = {
                            navHostController.popBackStack()
                        },
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

@Composable
private fun MainContent(
    modifier: Modifier,
    viewModel: ExercisesAddOrChangeScreenViewModel,
    state: State<ExercisesAddOrChangeScreenState>,
    types: List<ExerciseType>,
    groups: List<Group>,
    typeExpanded: MutableState<Boolean>,
    groupExpanded: MutableState<Boolean>,
    muscleExpanded: MutableState<Boolean>,
) {
    Box(modifier = modifier) {
        LazyColumn {
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
                            ExercisesAddOrChangeScreenIntent.DescriptionInput(
                                description = it
                            )
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
                Spacer(modifier = Modifier.padding(6.dp))
            }
            if (state.value.type?.id == 1) {
                item {
                    UiMenu(
                        upText = stringResource(R.string.group),
                        expanded = groupExpanded.value,
                        onExpandedChange = { groupExpanded.value = it },
                        onDismiss = { groupExpanded.value = false },
                        value = state.value.group?.name ?: stringResource(
                            R.string.touch_for_choose
                        ),
                        items = groups,
                        itemToString = { it.name },
                        onItemClick = {
                            viewModel.send(
                                ExercisesAddOrChangeScreenIntent.GroupInput(
                                    group = it
                                )
                            )
                            groupExpanded.value = false
                        }
                    )
                    Spacer(modifier = Modifier.padding(6.dp))
                }
                state.value.group?.let { group ->
                    state.value.muscles?.let { muscles ->
                        muscles[group]?.let { items ->
                            item {
                                UiMenu(
                                    upText = stringResource(R.string.muscle),
                                    expanded = muscleExpanded.value,
                                    onExpandedChange = { muscleExpanded.value = it },
                                    onDismiss = { muscleExpanded.value = false },
                                    value = state.value.muscle?.name ?: stringResource(
                                        R.string.touch_for_choose
                                    ),
                                    items = items,
                                    itemToString = { it.name },
                                    onItemClick = {
                                        viewModel.send(
                                            ExercisesAddOrChangeScreenIntent.MuscleInput(
                                                muscle = it
                                            )
                                        )
                                        muscleExpanded.value = false
                                    }
                                )
                                Spacer(modifier = Modifier.padding(8.dp))
                            }
                        }
                    }

                }
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
                Spacer(modifier = Modifier.padding(2.dp))
            }
        }
    }
}