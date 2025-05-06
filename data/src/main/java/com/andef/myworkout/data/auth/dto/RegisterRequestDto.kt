package com.andef.myworkout.data.auth.dto

import com.google.gson.annotations.SerializedName

/**
 * Запрос к серверу при регистрации
 *
 * @property mail почта
 * @property password пароль
 * @property surname фамилия
 * @property name имя
 * @property patronymic отчество
 */
data class RegisterRequestDto(
    @SerializedName("mail")
    val mail: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("patronymic")
    val patronymic: String
)
