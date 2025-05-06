package com.andef.myworkout.data.auth.dto

import com.google.gson.annotations.SerializedName

/**
 * Запрос к серверу при входе
 *
 * @property mail почта
 * @property password пароль
 */
data class LoginRequestDto(
    @SerializedName("mail")
    val mail: String,
    @SerializedName("password")
    val password: String
)
