package com.andef.myworkout.data.workout.dto

import com.google.gson.annotations.SerializedName

/**
 * Тренировка
 *
 * @property id идентификатор (автоинкрементируемый)
 * @property name название
 * @property date дата
 * @property ownerUserId id пользователя-владельца
 */
data class WorkoutDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String?,
    @SerializedName("date")
    val date: String,
    @SerializedName("ownerUserId")
    val ownerUserId: Int
)
