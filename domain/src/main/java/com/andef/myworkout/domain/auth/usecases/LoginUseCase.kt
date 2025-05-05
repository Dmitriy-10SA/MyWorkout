package com.andef.myworkout.domain.auth.usecases

import com.andef.myworkout.domain.auth.entities.LoginRequest
import com.andef.myworkout.domain.auth.repository.AuthRepository
import javax.inject.Inject

/**
 * Логин пользователя
 *
 * @see AuthRepository
 */
class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun invoke(loginRequest: LoginRequest) = repository.login(loginRequest)
}