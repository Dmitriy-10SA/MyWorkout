package com.andef.myworkout.presentation.exercises.addorchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myworkout.Requester
import com.andef.myworkout.domain.auth.usecases.ClearTokenUseCase
import com.andef.myworkout.domain.auth.usecases.GetTokenUseCase
import com.andef.myworkout.domain.exercise.entities.ExerciseAddRequest
import com.andef.myworkout.domain.exercise.entities.ExerciseType
import com.andef.myworkout.domain.exercise.entities.Group
import com.andef.myworkout.domain.exercise.entities.Muscle
import com.andef.myworkout.domain.exercise.usecases.AddExerciseUseCase
import com.andef.myworkout.domain.exercise.usecases.GetAllExerciseTypesUseCase
import com.andef.myworkout.domain.exercise.usecases.GetAllGroupsUseCase
import com.andef.myworkout.domain.exercise.usecases.GetAllMusclesForGroupUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ExercisesAddOrChangeScreenViewModel @Inject constructor(
    private val requester: Requester,
    private val addExerciseUseCase: AddExerciseUseCase,
    private val getAllMusclesForGroupUseCase: GetAllMusclesForGroupUseCase,
    private val getAllGroupsUseCase: GetAllGroupsUseCase,
    private val getAllExerciseTypesUseCase: GetAllExerciseTypesUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val clearTokenUseCase: ClearTokenUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ExercisesAddOrChangeScreenState())
    val state: StateFlow<ExercisesAddOrChangeScreenState> = _state

    fun send(intent: ExercisesAddOrChangeScreenIntent) {
        when (intent) {
            is ExercisesAddOrChangeScreenIntent.DescriptionInput -> {
                changeInput(description = intent.description)
            }

            is ExercisesAddOrChangeScreenIntent.NameInput -> {
                changeInput(name = intent.name)
            }

            is ExercisesAddOrChangeScreenIntent.TypeInput -> {
                changeInput(type = intent.type)
            }

            is ExercisesAddOrChangeScreenIntent.LoadAll -> {
                loadAll(onUnauthorized = intent.onUnauthorized)
            }

            is ExercisesAddOrChangeScreenIntent.GroupInput -> {
                changeInput(
                    group = intent.group,
                    muscle = null
                )
            }

            is ExercisesAddOrChangeScreenIntent.MuscleInput -> {
                changeInput(muscle = intent.muscle)
            }

            is ExercisesAddOrChangeScreenIntent.AddExercise -> {
                addExercise(onSuccess = intent.onSuccess, onUnauthorized = intent.onUnauthorized)
            }
        }
    }

    private fun addExercise(onSuccess: () -> Unit, onUnauthorized: () -> Unit) {
        requester.networkRequest(
            viewModelScope = viewModelScope,
            beforeRequest = { _state.value = _state.value.copy(isLoading = true, isError = false) },
            request = { token ->
                addExerciseUseCase.invoke(
                    token = token,
                    exerciseAddRequest = ExerciseAddRequest(
                        name = _state.value.name,
                        description = _state.value.description,
                        video = _state.value.video ?: "",
                        muscleId = if (state.value.type!!.id == 1) {
                            _state.value.muscle!!.id
                        } else {
                            null
                        },
                        exerciseTypeId = _state.value.type!!.id
                    )
                )
                onSuccess()
            },
            onUnauthorized = onUnauthorized,
            onError = { _state.value = _state.value.copy(isError = true) },
            onFinally = { _state.value = _state.value.copy(isLoading = false) }
        )
    }

    private fun loadAll(onUnauthorized: () -> Unit) {
        requester.networkRequest(
            viewModelScope = viewModelScope,
            beforeRequest = { _state.value = _state.value.copy(isLoading = true, isError = false) },
            request = { token ->
                val typesDeferred = async { getAllExerciseTypesUseCase.invoke(token) }
                val groupsDeferred = async { getAllGroupsUseCase.invoke(token) }
                val groups = groupsDeferred.await()
                val muscles = groups.associateWith { group ->
                    async { getAllMusclesForGroupUseCase.invoke(token, group.id) }.await()
                }
                val types = typesDeferred.await()
                _state.value = _state.value.copy(
                    types = types,
                    groups = groups,
                    muscles = muscles
                )
            },
            onUnauthorized = onUnauthorized,
            onError = { _state.value = _state.value.copy(isError = true) },
            onFinally = { _state.value = _state.value.copy(isLoading = false) }
        )
    }

    private fun changeInput(
        name: String = _state.value.name,
        description: String = _state.value.description,
        type: ExerciseType? = _state.value.type,
        group: Group? = _state.value.group,
        muscle: Muscle? = _state.value.muscle
    ) {
        val isValidInfoForAdd = if (type?.id == 1) {
            name.isNotEmpty() && muscle != null
        } else {
            name.isNotEmpty() && type != null
        }

        _state.value = _state.value.copy(
            name = name,
            description = description,
            type = type,
            group = group,
            muscle = muscle,
            isValidInfoForAdd = isValidInfoForAdd
        )
    }
}