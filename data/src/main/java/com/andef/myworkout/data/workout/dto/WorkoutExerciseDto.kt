package com.andef.myworkout.data.workout.dto

import com.google.gson.annotations.SerializedName

/**
 * Упражнение на тренировку
 *
 * @property id идентификатор (автоинкрементируемый)
 * @property sets кол-во подходов
 * @property reps кол-во повторений
 * @property workoutId id тренировки
 * @property exerciseId id упражнения
 */
data class WorkoutExerciseDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("sets")
    val sets: Int?,
    @SerializedName("reps")
    val reps: Int?,
    @SerializedName("workoutId")
    val workoutId: Long,
    @SerializedName("exerciseId")
    val exerciseId: Long
)
