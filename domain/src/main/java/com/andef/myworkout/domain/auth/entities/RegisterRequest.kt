package com.andef.myworkout.domain.auth.entities

/**
 * Запрос к серверу при регистрации
 *
 * @property mail почта
 * @property password пароль
 * @property surname фамилия
 * @property name имя
 * @property patronymic отчество
 */
data class RegisterRequest(
    val mail: String,
    val password: String,
    val surname: String,
    val name: String,
    val patronymic: String
)
