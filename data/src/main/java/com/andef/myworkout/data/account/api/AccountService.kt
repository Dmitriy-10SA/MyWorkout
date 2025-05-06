package com.andef.myworkout.data.account.api

import com.andef.myworkout.data.account.dto.ChangeUserInfoRequestDto
import com.andef.myworkout.data.account.dto.UserInfoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

/**
 * @property getUserInfo получение информации о пользователе
 * @property changeUserInfo изменение информации о пользователе
 *
 * @see UserInfoDto
 * @see ChangeUserInfoRequestDto
 */
interface AccountService {
    @GET("account/info")
    suspend fun getUserInfo(@Header("Authorization") token: String): Response<UserInfoDto>

    @PUT("account/change-info")
    suspend fun changeUserInfo(
        @Header("Authorization") token: String,
        @Body changeUserInfoRequestDto: ChangeUserInfoRequestDto
    ): Response<Unit>
}