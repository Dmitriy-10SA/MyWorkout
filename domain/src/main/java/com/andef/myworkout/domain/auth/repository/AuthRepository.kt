package com.andef.myworkout.domain.auth.repository

import com.andef.myworkout.domain.auth.entities.AuthResponse
import com.andef.myworkout.domain.auth.entities.LoginRequest
import com.andef.myworkout.domain.auth.entities.PasswordChangeRequest
import com.andef.myworkout.domain.auth.entities.RegisterRequest

/**
 * @property register регистрация пользователя
 * @property checkToken проверка токена пользователя
 * @property login логин пользователя
 * @property getToken получение последнего сохраненного токена пользователя (из SharedPreferences)
 * @property clearToken отчистка последнего сохраненного токена пользователя (из SharedPreferences)
 * @property saveToken сохранение токена пользователя (в SharedPreferences)
 *
 * @see AuthResponse
 * @see RegisterRequest
 * @see LoginRequest
 * @see PasswordChangeRequest
 */
interface AuthRepository {
    suspend fun register(registerRequest: RegisterRequest): AuthResponse
    suspend fun checkToken(token: String)
    suspend fun login(loginRequest: LoginRequest): AuthResponse
    suspend fun changePassword(passwordChangeRequest: PasswordChangeRequest): AuthResponse
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}