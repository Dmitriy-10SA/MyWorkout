package com.andef.myworkout.domain.auth.usecases

import com.andef.myworkout.domain.auth.repository.AuthRepository
import javax.inject.Inject

/**
 * Проверка токена пользователя
 *
 * @see AuthRepository
 */
class CheckTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend fun invoke(token: String) = repository.checkToken(token)
}