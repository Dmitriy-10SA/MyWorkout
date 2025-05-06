package com.andef.myworkout.data.auth.api

import com.andef.myworkout.data.auth.dto.AuthResponseDto
import com.andef.myworkout.data.auth.dto.LoginRequestDto
import com.andef.myworkout.data.auth.dto.PasswordChangeRequestDto
import com.andef.myworkout.data.auth.dto.RegisterRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * @property register регистрация пользователя
 * @property checkToken проверка токена пользователя
 * @property login логин пользователя
 * @property changePassword смена пароля
 *
 * @see AuthResponseDto
 * @see RegisterRequestDto
 * @see LoginRequestDto
 * @see PasswordChangeRequestDto
 */
interface AuthService {
    @POST("auth/register")
    suspend fun register(@Body registerRequestDto: RegisterRequestDto): Response<AuthResponseDto>

    @GET("auth/check-token")
    suspend fun checkToken(@Header("Authorization") token: String): Response<Unit>

    @POST("auth/login")
    suspend fun login(@Body loginRequestDto: LoginRequestDto): Response<AuthResponseDto>

    @POST("auth/change-password")
    suspend fun changePassword(
        @Body passwordChangeRequestDto: PasswordChangeRequestDto
    ): Response<AuthResponseDto>
}