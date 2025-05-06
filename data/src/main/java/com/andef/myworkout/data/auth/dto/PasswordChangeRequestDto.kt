package com.andef.myworkout.data.auth.dto

import com.google.gson.annotations.SerializedName

/**
 * Запрос к серверу на изменение пароля
 *
 * @property mail почта
 * @property password пароль
 */
data class PasswordChangeRequestDto(
    @SerializedName("mail")
    val mail: String,
    @SerializedName("password")
    val password: String
)
