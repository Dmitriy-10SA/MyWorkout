package com.andef.myworkout.data.account.repository

import com.andef.myworkout.data.ApiResponse
import com.andef.myworkout.data.account.api.AccountService
import com.andef.myworkout.data.account.mapper.ChangeUserInfoRequestMapper
import com.andef.myworkout.data.account.mapper.UserInfoMapper
import com.andef.myworkout.domain.account.entities.ChangeUserInfoRequest
import com.andef.myworkout.domain.account.entities.UserInfo
import com.andef.myworkout.domain.account.repository.AccountRepository
import javax.inject.Inject

/**
 * @property getUserInfo получение информации о пользователе
 * @property changeUserInfo изменение информации о пользователе
 *
 * @see AccountRepository
 * @see AccountService
 * @see UserInfoMapper
 * @see ChangeUserInfoRequestMapper
 * @see ApiResponse
 */
class AccountRepositoryImpl @Inject constructor(
    private val accountService: AccountService,
    private val apiResponse: ApiResponse,
    private val userInfoMapper: UserInfoMapper,
    private val changeUserInfoRequestMapper: ChangeUserInfoRequestMapper
) : AccountRepository {
    override suspend fun getUserInfo(token: String): UserInfo {
        val response = accountService.getUserInfo(token)
        return userInfoMapper.map(apiResponse.parseResponseWithoutNullableBody(response))
    }

    override suspend fun changeUserInfo(
        token: String,
        changeUserInfoRequest: ChangeUserInfoRequest
    ) {
        accountService.changeUserInfo(token, changeUserInfoRequestMapper.map(changeUserInfoRequest))
    }
}