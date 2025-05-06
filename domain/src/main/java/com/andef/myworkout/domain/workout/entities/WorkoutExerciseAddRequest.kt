package com.andef.myworkout.domain.workout.entities

/**
 * Запрос к серверу на создание упражнения на тренировку
 *
 * @property sets кол-во подходов
 * @property reps кол-во повторений
 * @property workoutId id тренировки
 * @property exerciseId id упражнения
 */
data class WorkoutExerciseAddRequest(
    val sets: Int?,
    val reps: Int?,
    val workoutId: Long,
    val exerciseId: Long
)
