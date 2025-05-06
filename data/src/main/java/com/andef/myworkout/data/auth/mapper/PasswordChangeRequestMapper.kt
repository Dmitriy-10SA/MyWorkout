package com.andef.myworkout.data.auth.mapper

import com.andef.myworkout.data.auth.dto.PasswordChangeRequestDto
import com.andef.myworkout.domain.auth.entities.PasswordChangeRequest
import javax.inject.Inject

/**
 * @property map преобразование PasswordChangeRequest -> PasswordChangeRequestDto
 *
 * @see PasswordChangeRequest
 * @see PasswordChangeRequestDto
 */
class PasswordChangeRequestMapper @Inject constructor() {
    fun map(passwordChangeRequest: PasswordChangeRequest) = PasswordChangeRequestDto(
        mail = passwordChangeRequest.mail,
        password = passwordChangeRequest.password
    )
}