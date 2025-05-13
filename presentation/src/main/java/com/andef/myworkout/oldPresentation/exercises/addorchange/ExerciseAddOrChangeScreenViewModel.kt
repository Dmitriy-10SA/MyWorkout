package com.andef.myworkout.oldPresentation.exercises.addorchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myworkout.data.ApiException
import com.andef.myworkout.domain.auth.usecases.ClearTokenUseCase
import com.andef.myworkout.domain.auth.usecases.GetTokenUseCase
import com.andef.myworkout.domain.exercise.entities.ExerciseType
import com.andef.myworkout.domain.exercise.usecases.GetAllExerciseTypesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExerciseAddOrChangeScreenViewModel @Inject constructor(
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

            is ExercisesAddOrChangeScreenIntent.LoadTypes -> {
                loadTypes(onUnauthorized = intent.onUnauthorized)
            }
        }
    }

    private fun loadTypes(onUnauthorized: () -> Unit) {
        networkRequest(
            request = { token ->
                val types = getAllExerciseTypesUseCase.invoke(token)
                _state.value = _state.value.copy(types = types)
            },
            onUnauthorized = onUnauthorized
        )
    }

    private fun changeInput(
        name: String = _state.value.name,
        description: String = _state.value.description,
        type: ExerciseType? = _state.value.type
    ) {
        _state.value = _state.value.copy(name = name, description = description, type = type)
    }

    private fun networkRequest(request: suspend (String) -> Unit, onUnauthorized: () -> Unit) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, isError = false)
                getTokenUseCase.invoke()?.let { token ->
                    request(token)
                } ?: {
                    clearTokenUseCase.invoke()
                    throw ApiException.Unauthorized
                }
            } catch (e: ApiException) {
                when (e) {
                    ApiException.Unauthorized -> {
                        clearTokenUseCase.invoke()
                        onUnauthorized()
                    }

                    else -> _state.value = _state.value.copy(isError = true)
                }
            } catch (_: Exception) {
                _state.value = _state.value.copy(isError = true)
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}