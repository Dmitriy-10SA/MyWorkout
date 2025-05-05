package com.andef.myworkout.domain.auth.usecases

import com.andef.myworkout.domain.auth.repository.AuthRepository
import javax.inject.Inject

/**
 * Получение последнего сохраненного токена пользователя (из SharedPreferences)
 *
 * @see AuthRepository
 */
class GetTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    fun invoke() = repository.getToken()
}