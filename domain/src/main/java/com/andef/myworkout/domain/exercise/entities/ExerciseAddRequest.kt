package com.andef.myworkout.domain.exercise.entities

/**
 * Запрос к серверу по созданию упражнения
 *
 * @property name название
 * @property description описание
 * @property video видео
 * @property muscleId id мышцы
 */
data class ExerciseAddRequest(
    val name: String,
    val description: String?,
    val video: String?,
    val muscleId: Int?,
    val exerciseTypeId: Int
)
