package com.andef.myworkout.domain.auth.usecases

import com.andef.myworkout.domain.auth.repository.AuthRepository
import javax.inject.Inject

/**
 * Сохранение токена пользователя (в SharedPreferences)
 *
 * @see AuthRepository
 */
class SaveTokenUseCase @Inject constructor(private val repository: AuthRepository) {
    fun invoke(token: String) = repository.saveToken(token)
}