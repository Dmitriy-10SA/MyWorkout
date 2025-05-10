package com.andef.myworkout.design.calendar.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myworkout.design.Black
import com.andef.myworkout.design.Blue
import com.andef.myworkout.design.R
import com.andef.myworkout.design.White
import com.andef.myworkout.design.calendar.state.UiCalendarState
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.WeekDay
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun UiCalendar(currentSelectedDate: MutableState<LocalDate>, state: UiCalendarState) {
    when (state) {
        is UiCalendarState.WeekTop -> {
            val calendarState = rememberWeekCalendarState(
                startDate = LocalDate.now().minusMonths(2),
                endDate = LocalDate.now().plusMonths(4),
                firstVisibleWeekDate = LocalDate.now()
            )

            UiWeekCalendar(
                topPadding = state.topPadding,
                calendarState = calendarState,
                currentSelectedDate = currentSelectedDate
            )
        }
    }
}

@Composable
private fun UiWeekCalendar(
    topPadding: Dp,
    calendarState: WeekCalendarState,
    currentSelectedDate: MutableState<LocalDate>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                ambientColor = Black,
                spotColor = Black,
                shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
            )
            .border(
                width = 1.dp,
                color = Black.copy(alpha = 0.05f),
                shape = RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = White, contentColor = Black)
    ) {
        Spacer(modifier = Modifier.padding(top = topPadding))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 3.dp),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.calendar),
            fontSize = 24.sp,
            color = Black
        )
        WeekCalendar(
            modifier = Modifier.padding(top = 12.dp, start = 12.dp, end = 12.dp, bottom = 14.dp),
            state = calendarState,
            dayContent = { day ->
                Day(day = day, currentSelectedDate = currentSelectedDate)
            }
        )
    }
}

@Composable
private fun Day(day: WeekDay, currentSelectedDate: MutableState<LocalDate>) {
    val backgroundColor = if (currentSelectedDate.value == day.date) {
        Blue
    } else {
        Color.Transparent
    }
    val borderColor = if (day.date == LocalDate.now() && currentSelectedDate.value != day.date) {
        Black
    } else {
        Blue.copy(alpha = 0.3f)
    }

    Box(
        modifier = Modifier
            .padding(3.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { currentSelectedDate.value = day.date }
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(20.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(20.dp)
            )
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = getShortDayOfWeekName(day.date.dayOfWeek),
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                color = if (currentSelectedDate.value == day.date) {
                    White
                } else {
                    Black
                }
            )
            Spacer(modifier = Modifier.padding(2.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = day.date.dayOfMonth.toString(),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = if (currentSelectedDate.value == day.date) {
                    White
                } else {
                    Black
                }
            )
        }
    }
}

@Composable
private fun getShortDayOfWeekName(dayOfWeek: DayOfWeek) = when (dayOfWeek) {
    DayOfWeek.MONDAY -> stringResource(R.string.monday_short)
    DayOfWeek.TUESDAY -> stringResource(R.string.tuesday_short)
    DayOfWeek.WEDNESDAY -> stringResource(R.string.wednesday_short)
    DayOfWeek.THURSDAY -> stringResource(R.string.thursday_short)
    DayOfWeek.FRIDAY -> stringResource(R.string.friday_short)
    DayOfWeek.SATURDAY -> stringResource(R.string.saturday_short)
    DayOfWeek.SUNDAY -> stringResource(R.string.sunday_short)
}