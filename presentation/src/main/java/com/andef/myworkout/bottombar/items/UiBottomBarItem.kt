package com.andef.myworkout.bottombar.items

import com.andef.myworkout.R
import com.andef.myworkout.navigation.Screen

sealed class UiBottomBarItem(
    val route: String,
    val iconResId: Int,
    val titleResId: Int,
    val contentDescriptionResId: Int
) {
    data object Calendar : UiBottomBarItem(
        route = Screen.CalendarScreen.MainScreen.route,
        iconResId = R.drawable.calendar,
        titleResId = R.string.calendar,
        contentDescriptionResId = R.string.calendar
    )

    data object Exercises : UiBottomBarItem(
        route = Screen.ExercisesScreen.MainScreen.route,
        iconResId = R.drawable.exercises,
        titleResId = R.string.exercises,
        contentDescriptionResId = R.string.exercises
    )

    data object Account : UiBottomBarItem(
        route = Screen.AccountScreen.route,
        iconResId = R.drawable.account,
        titleResId = R.string.account,
        contentDescriptionResId = R.string.account
    )

    companion object {
        fun getItemsList() = listOf<UiBottomBarItem>(Calendar, Exercises, Account)
    }
}