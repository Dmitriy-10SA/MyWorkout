package com.andef.myworkout.data.exercise.dto

import com.google.gson.annotations.SerializedName

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
data class ExerciseDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("video")
    val video: String?,
    @SerializedName("ownerUserId")
    val ownerUserId: Int,
    @SerializedName("muscleId")
    val muscleId: Int?,
    @SerializedName("exerciseTypeId")
    val exerciseTypeId: Int
)
