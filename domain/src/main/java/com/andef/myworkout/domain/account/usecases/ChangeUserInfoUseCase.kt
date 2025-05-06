package com.andef.myworkout.domain.account.usecases

import com.andef.myworkout.domain.account.entities.ChangeUserInfoRequest
import com.andef.myworkout.domain.account.repository.AccountRepository
import javax.inject.Inject

/**
 * Изменение информации о пользователе
 *
 * @see AccountRepository
 */
class ChangeUserInfoUseCase @Inject constructor(private val repository: AccountRepository) {
    suspend fun invoke(token: String, changeUserInfoRequest: ChangeUserInfoRequest) {
        repository.changeUserInfo(token, changeUserInfoRequest)
    }
}