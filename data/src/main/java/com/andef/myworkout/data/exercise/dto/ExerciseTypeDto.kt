package com.andef.myworkout.data.exercise.dto

import com.google.gson.annotations.SerializedName

/**
 * Тип упражнения
 *
 * @property id идентификатор
 * @property name название
 */
data class ExerciseTypeDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
