package com.andef.myworkout.domain.auth.usecases

import com.andef.myworkout.domain.auth.entities.RegisterRequest
import com.andef.myworkout.domain.auth.repository.AuthRepository
import javax.inject.Inject

/**
 * Регистрация пользователя
 *
 * @see AuthRepository
 */
class RegisterUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun invoke(registerRequest: RegisterRequest) = repository.register(registerRequest)
}