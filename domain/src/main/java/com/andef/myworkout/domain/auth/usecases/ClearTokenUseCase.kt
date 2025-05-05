package com.andef.myworkout.domain.auth.usecases

import com.andef.myworkout.domain.auth.repository.AuthRepository
import javax.inject.Inject

/**
 * Отчистка последнего сохраненного токена пользователя (из SharedPreferences)
 *
 * @see AuthRepository
 */
class ClearTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    fun invoke() = repository.clearToken()
}