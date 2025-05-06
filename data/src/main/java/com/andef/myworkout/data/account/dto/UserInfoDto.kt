package com.andef.myworkout.data.account.dto

import com.google.gson.annotations.SerializedName

/**
 * Информация о пользователе
 *
 * @property id идентификатор (автоинкрементируемый)
 * @property mail почта
 * @property password пароль (хэшированный)
 * @property surname фамилия
 * @property name имя
 * @property patronymic отчество
 * @property photo фото (может быть null)
 */
data class UserInfoDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("mail")
    val mail: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("patronymic")
    val patronymic: String,
    @SerializedName("photo")
    val photo: String?
)
