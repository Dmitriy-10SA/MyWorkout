package com.andef.myworkout.domain.auth.entities

/**
 * Запрос к серверу на изменение пароля
 *
 * @property mail почта
 * @property password пароль
 */
data class PasswordChangeRequest(
    val mail: String,
    val password: String
)
