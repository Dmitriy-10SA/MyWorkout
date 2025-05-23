package com.andef.myworkout.navigation

import android.net.Uri

sealed class Screen(val route: String) {
    data object AuthScreen : Screen(AUTH_SCREEN_ROUTE) {
        data object MainScreen : Screen(AUTH_MAIN_SCREEN_ROUTE)
        data object ForgotPasswordScreen : Screen(AUTH_FORGOT_PASSWORD_SCREEN_ROUTE)
    }

    data object AccountScreen : Screen(ACCOUNT_SCREEN_ROUTE) {
        data object MainScreen : Screen(ACCOUNT_MAIN_SCREEN_ROUTE)
        data object ChangeInfoScreen : Screen(ACCOUNT_CHANGE_INFO_SCREEN_ROUTE +
                "/{$SURNAME}/{$NAME}/{$PATRONYMIC}/{$PHOTO}") {
            fun passParams(
                surname: String,
                name: String,
                patronymic: String,
                photo: String
            ): String {
                return "$ACCOUNT_CHANGE_INFO_SCREEN_ROUTE/${
                    Uri.encode(surname)
                }/${
                    Uri.encode(name)
                }/${
                    Uri.encode(patronymic)
                }/${
                    Uri.encode(photo)
                }"
            }
        }
    }

    data object ExercisesScreen : Screen(EXERCISES_SCREEN_ROUTE) {
        data object MainScreen : Screen(EXERCISES_MAIN_SCREEN_ROUTE)
        data object AddOrChangeScreen : Screen("$EXERCISES_ADD_OR_CHANGE_SCREEN_ROUTE/{$IS_ADD}") {
            fun passParams(isAdd: Boolean): String {
                return "$EXERCISES_ADD_OR_CHANGE_SCREEN_ROUTE/$isAdd"
            }
        }
    }

    data object CalendarScreen : Screen(CALENDAR_SCREEN_ROUTE) {
        data object MainScreen : Screen(CALENDAR_MAIN_SCREEN_ROUTE)
    }

    companion object {
        private const val AUTH_SCREEN_ROUTE = "auth"
        private const val AUTH_MAIN_SCREEN_ROUTE = "auth_main"
        private const val AUTH_FORGOT_PASSWORD_SCREEN_ROUTE = "auth_forgot_password"

        private const val ACCOUNT_SCREEN_ROUTE = "account"
        private const val ACCOUNT_MAIN_SCREEN_ROUTE = "account_main"
        private const val ACCOUNT_CHANGE_INFO_SCREEN_ROUTE = "account_change_info"

        private const val EXERCISES_SCREEN_ROUTE = "exercises"
        private const val EXERCISES_MAIN_SCREEN_ROUTE = "exercises_main"
        private const val EXERCISES_ADD_OR_CHANGE_SCREEN_ROUTE = "exercises_add_or_change"

        private const val CALENDAR_SCREEN_ROUTE = "calendar"
        private const val CALENDAR_MAIN_SCREEN_ROUTE = "calendar_main"

        const val IS_ADD = "is_add"
        const val SURNAME = "surname"
        const val NAME = "name"
        const val PATRONYMIC = "patronymic"
        const val PHOTO = "photo"
    }
}