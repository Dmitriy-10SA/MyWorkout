package com.andef.myworkout.domain.workout.entities

/**
 * Упражнение на тренировку
 *
 * @property id идентификатор (автоинкрементируемый)
 * @property sets кол-во подходов
 * @property reps кол-во повторений
 * @property workoutId id тренировки
 * @property exerciseId id упражнения
 */
data class WorkoutExercise(
    val id: Long,
    val sets: Int?,
    val reps: Int?,
    val workoutId: Long,
    val exerciseId: Long
)
