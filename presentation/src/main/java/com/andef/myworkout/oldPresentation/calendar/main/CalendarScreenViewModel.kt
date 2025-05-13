package com.andef.myworkout.oldPresentation.calendar.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myworkout.data.ApiException
import com.andef.myworkout.domain.auth.usecases.ClearTokenUseCase
import com.andef.myworkout.domain.auth.usecases.GetTokenUseCase
import com.andef.myworkout.domain.workout.usecases.GetWorkoutByDateUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDate
import java.time.LocalDate
import javax.inject.Inject

class CalendarScreenViewModel @Inject constructor(
    private val getWorkoutByDateUseCase: GetWorkoutByDateUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val clearTokenUseCase: ClearTokenUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CalendarScreenState())
    val state: StateFlow<CalendarScreenState> = _state

    var currentLoadWorkoutJob: Job? = null

    fun send(intent: CalendarScreenIntent) {
        when (intent) {
            is CalendarScreenIntent.LoadWorkout -> {
                loadWorkoutByDate(date = intent.date, onUnauthorized = intent.onUnauthorized)
            }
        }
    }

    private fun loadWorkoutByDate(date: LocalDate, onUnauthorized: () -> Unit) {
        currentLoadWorkoutJob?.cancel()
        currentLoadWorkoutJob = networkRequest(
            request = { token ->
                val workout = getWorkoutByDateUseCase.invoke(
                    token = token,
                    date = date.toKotlinLocalDate()
                )
                _state.value = _state.value.copy(workout = workout)
            },
            onUnauthorized = onUnauthorized
        )
    }

    private fun networkRequest(request: suspend (String) -> Unit, onUnauthorized: () -> Unit): Job {
        return viewModelScope.launch {
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