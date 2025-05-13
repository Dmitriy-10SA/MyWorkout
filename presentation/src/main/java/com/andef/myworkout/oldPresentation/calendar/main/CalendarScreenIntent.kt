package com.andef.myworkout.oldPresentation.calendar.main

import java.time.LocalDate

sealed class CalendarScreenIntent {
    data class LoadWorkout(
        val date: LocalDate,
        val onUnauthorized: () -> Unit
    ) : CalendarScreenIntent()
}