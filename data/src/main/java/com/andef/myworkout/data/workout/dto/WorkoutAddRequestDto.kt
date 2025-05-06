package com.andef.myworkout.data.workout.dto

import com.google.gson.annotations.SerializedName

/**
 * Запрос на сервер для создания тренировки
 *
 * @property name название
 * @property date дата
 */
data class WorkoutAddRequestDto(
    @SerializedName("name")
    val name: String?,
    @SerializedName("date")
    val date: String
)
