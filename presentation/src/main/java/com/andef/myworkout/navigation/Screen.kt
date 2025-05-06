package com.andef.myworkout.navigation

/**
 * @property AuthScreen экран входа/регистрации/забыл пароль
 *
 * @property AccountScreen экран аккаунта (профиля)
 *
 * @property ExercisesScreen иерархия экранов упражнения
 *
 * @property CalendarScreen иерархия экранов календарь (календарь тренировок)
 */
sealed class Screen(val route: String) {
    data object AuthScreen : Screen(AUTH_SCREEN_ROUTE)

    data object AccountScreen : Screen(ACCOUNT_SCREEN_ROUTE)

    /**
     * @property MainScreen главный экран
     */
    data object ExercisesScreen : Screen(EXERCISES_SCREEN_ROUTE) {
        data object MainScreen : Screen(EXERCISES_MAIN_SCREEN_ROUTE)
    }

    /**
     * @property MainScreen главный экран
     */
    data object CalendarScreen : Screen(CALENDAR_SCREEN_ROUTE) {
        data object MainScreen : Screen(CALENDAR_MAIN_SCREEN_ROUTE)
    }

    companion object {
        private const val AUTH_SCREEN_ROUTE = "auth"

        private const val ACCOUNT_SCREEN_ROUTE = "account"

        private const val EXERCISES_SCREEN_ROUTE = "exercises"
        private const val EXERCISES_MAIN_SCREEN_ROUTE = "exercises_main"

        private const val CALENDAR_SCREEN_ROUTE = "calendar"
        private const val CALENDAR_MAIN_SCREEN_ROUTE = "calendar_main"
    }
}