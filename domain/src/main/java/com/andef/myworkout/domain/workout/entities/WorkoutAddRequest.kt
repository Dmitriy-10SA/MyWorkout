package com.andef.myworkout.domain.workout.entities

import kotlinx.datetime.LocalDate

/**
 * Запрос на сервер для создания тренировки
 *
 * @property name название
 * @property date дата
 */
data class WorkoutAddRequest(
    val name: String?,
    val date: LocalDate
)
