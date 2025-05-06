package com.andef.myworkout.data.auth.mapper

import com.andef.myworkout.data.auth.dto.AuthResponseDto
import com.andef.myworkout.domain.auth.entities.AuthResponse
import javax.inject.Inject

/**
 * @property map преобразование AuthResponseDto -> AuthResponse
 *
 * @see AuthResponseDto
 * @see AuthResponse
 */
class AuthResponseMapper @Inject constructor() {
    fun map(authResponseDto: AuthResponseDto) = AuthResponse(token = authResponseDto.token)
}