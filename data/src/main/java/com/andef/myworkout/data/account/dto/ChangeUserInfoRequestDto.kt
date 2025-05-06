package com.andef.myworkout.data.account.dto

import com.google.gson.annotations.SerializedName

/**
 * Запрос к серверу на изменение информации о пользователе
 *
 * @property surname фамилия
 * @property name имя
 * @property patronymic отчество
 * @property photo фото (может быть null)
 */
data class ChangeUserInfoRequestDto(
    @SerializedName("surname")
    val surname: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("patronymic")
    val patronymic: String,
    @SerializedName("photo")
    val photo: String?
)