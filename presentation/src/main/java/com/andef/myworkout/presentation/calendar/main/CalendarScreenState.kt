package com.andef.myworkout.presentation.calendar.main

import com.andef.myworkout.domain.workout.entities.Workout

data class CalendarScreenState(
    val workout: List<Workout> = listOf<Workout>(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
