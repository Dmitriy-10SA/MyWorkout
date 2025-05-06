package com.andef.myworkout.data.auth.mapper

import com.andef.myworkout.data.auth.dto.RegisterRequestDto
import com.andef.myworkout.domain.auth.entities.RegisterRequest
import javax.inject.Inject

/**
 * @property map преобразование RegisterRequest -> RegisterRequestDto
 *
 * @see RegisterRequest
 * @see RegisterRequestDto
 */
class RegisterRequestMapper @Inject constructor() {
    fun map(registerRequest: RegisterRequest) = RegisterRequestDto(
        mail = registerRequest.mail,
        password = registerRequest.password,
        surname = registerRequest.surname,
        name = registerRequest.name,
        patronymic = registerRequest.patronymic
    )
}