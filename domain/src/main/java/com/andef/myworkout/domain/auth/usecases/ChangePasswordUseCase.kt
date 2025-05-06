package com.andef.myworkout.domain.auth.usecases

import com.andef.myworkout.domain.auth.entities.AuthResponse
import com.andef.myworkout.domain.auth.entities.PasswordChangeRequest
import com.andef.myworkout.domain.auth.repository.AuthRepository
import javax.inject.Inject

/**
 * Смена пароля
 *
 * @see AuthRepository
 */
class ChangePasswordUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun invoke(passwordChangeRequest: PasswordChangeRequest): AuthResponse {
        return repository.changePassword(passwordChangeRequest)
    }
}