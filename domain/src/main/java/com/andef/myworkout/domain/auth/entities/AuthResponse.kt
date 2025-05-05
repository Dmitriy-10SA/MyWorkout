package com.andef.myworkout.domain.auth.entities

/**
 * Ответ сервера при успешном входе
 *
 * @property token токен
 */
data class AuthResponse(
    val token: String
)
