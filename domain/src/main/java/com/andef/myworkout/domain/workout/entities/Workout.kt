package com.andef.myworkout.domain.workout.entities

import kotlinx.datetime.LocalDate

/**
 * Тренировка
 *
 * @property id идентификатор (автоинкрементируемый)
 * @property name название
 * @property date дата
 * @property ownerUserId id пользователя-владельца
 */
data class Workout(
    val id: Long,
    val name: String?,
    val date: LocalDate,
    val ownerUserId: Int
)
