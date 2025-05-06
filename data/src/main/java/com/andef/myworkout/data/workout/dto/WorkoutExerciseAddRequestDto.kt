package com.andef.myworkout.data.workout.dto

import com.google.gson.annotations.SerializedName

/**
 * Запрос к серверу на создание упражнения на тренировку
 *
 * @property sets кол-во подходов
 * @property reps кол-во повторений
 * @property workoutId id тренировки
 * @property exerciseId id упражнения
 */
data class WorkoutExerciseAddRequestDto(
    @SerializedName("sets")
    val sets: Int?,
    @SerializedName("reps")
    val reps: Int?,
    @SerializedName("workoutId")
    val workoutId: Long,
    @SerializedName("exerciseId")
    val exerciseId: Long
)
