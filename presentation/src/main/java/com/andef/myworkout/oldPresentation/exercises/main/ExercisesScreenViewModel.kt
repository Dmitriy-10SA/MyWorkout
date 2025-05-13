package com.andef.myworkout.oldPresentation.exercises.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ExercisesScreenViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(ExercisesScreenState())
    val state: StateFlow<ExercisesScreenState> = _state
}