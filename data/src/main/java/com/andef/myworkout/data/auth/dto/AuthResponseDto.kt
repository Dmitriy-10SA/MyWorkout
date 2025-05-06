package com.andef.myworkout.data.auth.dto

import com.google.gson.annotations.SerializedName

/**
 * Ответ сервера при успешном входе
 *
 * @property token токен
 */
data class AuthResponseDto(@SerializedName("token") val token: String)
