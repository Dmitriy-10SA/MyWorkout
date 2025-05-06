package com.andef.myworkout.domain.exercise.entities

/**
 * Упражнение
 *
 * @property id идентификатор (автоинкрементируемый)
 * @property name название
 * @property description описание
 * @property video видео
 * @property ownerUserId id пользователя
 * @property muscleId id мышцы
 */
data class Exercise(
    val id: Long,
    val name: String,
    val description: String?,
    val video: String?,
    val ownerUserId: Int,
    val muscleId: Int?,
    val exerciseTypeId: Int
)
