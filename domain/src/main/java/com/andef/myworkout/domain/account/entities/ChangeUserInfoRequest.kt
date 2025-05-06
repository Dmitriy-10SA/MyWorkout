package com.andef.myworkout.domain.account.entities

/**
 * Запрос к серверу на изменение информации о пользователе
 *
 * @property surname фамилия
 * @property name имя
 * @property patronymic отчество
 * @property photo фото (может быть null)
 */
data class ChangeUserInfoRequest(
    val surname: String,
    val name: String,
    val patronymic: String,
    val photo: String?
)