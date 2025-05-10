package com.andef.myworkout.design.calendar.state

import androidx.compose.ui.unit.Dp
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState

sealed class UiCalendarState {
    data class WeekTop(val topPadding: Dp, val calendarState: WeekCalendarState) : UiCalendarState()
}