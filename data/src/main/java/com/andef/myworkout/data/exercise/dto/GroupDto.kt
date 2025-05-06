package com.andef.myworkout.data.exercise.dto

import com.google.gson.annotations.SerializedName

/**
 * Мышечная группа
 *
 * @property id идентификатор
 * @property name название
 */
data class GroupDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
