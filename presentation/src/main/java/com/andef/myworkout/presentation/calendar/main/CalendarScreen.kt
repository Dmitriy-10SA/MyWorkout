package com.andef.myworkout.presentation.calendar.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.andef.myworkout.design.calendar.state.UiCalendarState
import com.andef.myworkout.design.calendar.ui.UiCalendar
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.di.viewmodel.ViewModelFactory
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.LocalDate

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController
) {
    val viewModel: CalendarScreenViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val currentSelectedDate = rememberSaveable { mutableStateOf(LocalDate.now()) }

    UiScaffold(
        topBar = {
            UiCalendar(
                currentSelectedDate = currentSelectedDate,
                state = UiCalendarState.WeekTop(
                    topPadding = paddingValues.calculateTopPadding() + 19.dp,
                    calendarState = rememberWeekCalendarState(
                        startDate = LocalDate.now().minusWeeks(1),
                        endDate = LocalDate.now().plusWeeks(1)
                    )
                )
            )
        }
    ) { topPadding ->

    }
}