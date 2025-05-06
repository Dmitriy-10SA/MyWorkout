package com.andef.myworkout.data.auth.mapper

import com.andef.myworkout.data.auth.dto.LoginRequestDto
import com.andef.myworkout.domain.auth.entities.LoginRequest
import javax.inject.Inject

/**
 * @property map преобразование LoginRequest -> LoginRequestDto
 *
 * @see LoginRequest
 * @see LoginRequestDto
 */
class LoginRequestMapper @Inject constructor() {
    fun map(loginRequest: LoginRequest) = LoginRequestDto(
        mail = loginRequest.mail,
        password = loginRequest.password
    )
}