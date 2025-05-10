package com.andef.myworkout.presentation.calendar.maincontent

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.andef.myworkout.R
import com.andef.myworkout.design.calendar.state.UiCalendarState
import com.andef.myworkout.design.calendar.ui.UiCalendar
import com.andef.myworkout.design.error.ui.UiErrorOverlay
import com.andef.myworkout.design.loading.ui.UiLoadingOverlay
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.design.snackbar.state.UiSnackBarState
import com.andef.myworkout.design.snackbar.ui.UiSnackBarHost
import com.andef.myworkout.presentation.calendar.main.CalendarScreenState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.LocalDate

@Composable
fun CalendarScreenContent(
    state: State<CalendarScreenState>,
    showLoading: MutableState<Boolean>,
    currentSelectedDate: MutableState<LocalDate>,
    snackBarHostState: SnackbarHostState,
    paddingValues: PaddingValues,
    onRetry: () -> Unit
) {
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
        UiSnackBarHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topPadding.calculateTopPadding()),
            snackBarHostState = snackBarHostState,
            state = UiSnackBarState.Error
        )

        MainContent(state = state)

        ErrorAndLoadingContent(
            paddingValues = PaddingValues(
                bottom = paddingValues.calculateBottomPadding(),
                top = topPadding.calculateTopPadding()
            ),
            state = state,
            showLoading = showLoading,
            onRetry = onRetry
        )
    }
}

@Composable
private fun MainContent(state: State<CalendarScreenState>) {
    LazyColumn {
        items(items = state.value.workout, key = { it.id }) {
            Text(it.toString())
        }
    }
}

@Composable
private fun ErrorAndLoadingContent(
    state: State<CalendarScreenState>,
    showLoading: MutableState<Boolean>,
    paddingValues: PaddingValues,
    onRetry: () -> Unit
) {
    if (showLoading.value) {
        UiLoadingOverlay(
            text = stringResource(R.string.my_workout),
            paddingValues = paddingValues
        )
    } else if (state.value.isError) {
        UiErrorOverlay(
            paddingValues = paddingValues,
            onRetry = onRetry
        )
    }
}