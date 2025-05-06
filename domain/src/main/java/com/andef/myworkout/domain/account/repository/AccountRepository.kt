package com.andef.myworkout.domain.account.repository

import com.andef.myworkout.domain.account.entities.ChangeUserInfoRequest
import com.andef.myworkout.domain.account.entities.UserInfo

/**
 * @property getUserInfo получение информации о пользователе
 * @property changeUserInfo изменение информации о пользователе
 *
 * @see UserInfo
 * @see ChangeUserInfoRequest
 */
interface AccountRepository {
    suspend fun getUserInfo(token: String): UserInfo
    suspend fun changeUserInfo(token: String, changeUserInfoRequest: ChangeUserInfoRequest)
}