package com.andef.myworkout.domain.auth.repository

import com.andef.myworkout.domain.auth.entities.AuthResponse
import com.andef.myworkout.domain.auth.entities.LoginRequest
import com.andef.myworkout.domain.auth.entities.RegisterRequest
import retrofit2.Response

/**
 * @property register регистрация пользователя
 * @property checkToken проверка токена пользователя
 * @property login логин пользователя
 * @property getToken получение последнего сохраненного токена пользователя (из SharedPreferences)
 * @property clearToken отчистка последнего сохраненного токена пользователя (из SharedPreferences)
 *
 * @see AuthResponse
 * @see RegisterRequest
 * @see LoginRequest
 */
interface AuthRepository {
    suspend fun register(registerRequest: RegisterRequest): Response<AuthResponse>
    suspend fun checkToken(token: String): Response<Unit>
    suspend fun login(loginRequest: LoginRequest): Response<AuthResponse>
    fun getToken(): String
    fun clearToken()
}