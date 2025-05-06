package com.andef.myworkout.data.auth.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.andef.myworkout.data.ApiResponse
import com.andef.myworkout.data.auth.api.AuthService
import com.andef.myworkout.data.auth.mapper.AuthResponseMapper
import com.andef.myworkout.data.auth.mapper.LoginRequestMapper
import com.andef.myworkout.data.auth.mapper.RegisterRequestMapper
import com.andef.myworkout.domain.auth.entities.AuthResponse
import com.andef.myworkout.domain.auth.entities.LoginRequest
import com.andef.myworkout.domain.auth.entities.RegisterRequest
import com.andef.myworkout.domain.auth.repository.AuthRepository
import javax.inject.Inject

/**
 * @property register регистрация пользователя
 * @property checkToken проверка токена пользователя
 * @property login логин пользователя
 * @property getToken получение последнего сохраненного токена пользователя (из SharedPreferences)
 * @property clearToken отчистка последнего сохраненного токена пользователя (из SharedPreferences)
 *
 * @see AuthRepository
 * @see AuthService
 * @see AuthResponseMapper
 * @see RegisterRequestMapper
 * @see LoginRequestMapper
 */
class AuthRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val authService: AuthService,
    private val apiResponse: ApiResponse,
    private val authResponseMapper: AuthResponseMapper,
    private val registerRequestMapper: RegisterRequestMapper,
    private val loginRequestMapper: LoginRequestMapper
) : AuthRepository {
    override suspend fun register(registerRequest: RegisterRequest): AuthResponse {
        val response = authService.register(registerRequestMapper.map(registerRequest))
        return authResponseMapper.map(apiResponse.parseResponseWithoutNullableBody(response))
    }

    override suspend fun checkToken(token: String) {
        apiResponse.parseResponseWithNullableBody(authService.checkToken(token))
    }

    override suspend fun login(loginRequest: LoginRequest): AuthResponse {
        val response = authService.login(loginRequestMapper.map(loginRequest))
        return authResponseMapper.map(apiResponse.parseResponseWithoutNullableBody(response))
    }

    override fun getToken() = sharedPreferences.getString(TOKEN_PREF, null)

    override fun clearToken() {
        sharedPreferences.edit { remove(TOKEN_PREF) }
    }

    companion object {
        private const val TOKEN_PREF = "token"
    }
}