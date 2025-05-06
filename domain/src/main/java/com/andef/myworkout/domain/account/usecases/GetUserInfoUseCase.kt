package com.andef.myworkout.domain.account.usecases

import com.andef.myworkout.domain.account.repository.AccountRepository
import javax.inject.Inject

/**
 * Получение информации о пользователе
 *
 * @see AccountRepository
 */
class GetUserInfoUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend fun invoke(token: String) = repository.getUserInfo(token)
}