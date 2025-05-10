package com.andef.myworkout.presentation.calendar.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.andef.myworkout.design.calendar.state.UiCalendarState
import com.andef.myworkout.design.calendar.ui.UiCalendar
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.di.viewmodel.ViewModelFactory
import java.time.LocalDate

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues,
    viewModelFactory: ViewModelFactory,
    navHostController: NavHostController
) {
    val viewModel: CalendarScreenViewModel = viewModel(factory = viewModelFactory)
    val state = viewModel.state.collectAsState()

    val currentSelectedDate = remember { mutableStateOf(LocalDate.now()) }

    UiScaffold(
        topBar = {
            UiCalendar(
                currentSelectedDate = currentSelectedDate,
                state = UiCalendarState.WeekTop(
                    topPadding = paddingValues.calculateTopPadding() + 19.dp
                )
            )
        }
    ) { topPadding ->

    }
}