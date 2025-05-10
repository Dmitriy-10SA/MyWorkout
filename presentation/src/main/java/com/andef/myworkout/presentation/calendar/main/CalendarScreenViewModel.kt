package com.andef.myworkout.presentation.calendar.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CalendarScreenViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(CalendarScreenState())
    val state: StateFlow<CalendarScreenState> = _state

    fun send(intent: CalendarScreenIntent) {
//        when (intent) {
//
//        }
    }
}