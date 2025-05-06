package com.andef.myworkout.data.exercise.dto

import com.google.gson.annotations.SerializedName

/**
 * Запрос к серверу по созданию упражнения
 *
 * @property name название
 * @property description описание
 * @property video видео
 * @property muscleId id мышцы
 */
data class ExerciseAddRequestDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("video")
    val video: String?,
    @SerializedName("muscleId")
    val muscleId: Int?,
    @SerializedName("exerciseTypeId")
    val exerciseTypeId: Int
)
