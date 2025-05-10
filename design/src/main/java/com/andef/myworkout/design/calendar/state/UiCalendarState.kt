package com.andef.myworkout.design.calendar.state

import androidx.compose.ui.unit.Dp

sealed class UiCalendarState {
    data class WeekTop(val topPadding: Dp) : UiCalendarState()
}