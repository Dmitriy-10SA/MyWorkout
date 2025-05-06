package com.andef.myworkout.data.exercise.dto

import com.google.gson.annotations.SerializedName

/**
 * Мышца
 *
 * @property id идентификатор
 * @property groupId id мышечной группы
 * @property name название
 */
data class MuscleDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("groupId")
    val groupId: Int,
    @SerializedName("name")
    val name: String
)
