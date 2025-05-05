package com.andef.myworkout.domain.auth.entities

/**
 * Запрос к серверу при входе
 *
 * @property mail почта
 * @property password пароль
 */
data class LoginRequest(
    val mail: String,
    val password: String
)
