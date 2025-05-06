package com.andef.myworkout.data

import com.andef.myworkout.data.account.api.AccountService
import com.andef.myworkout.data.auth.api.AuthService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @property authService получение AuthService
 * @property accountService получение AccountService
 *
 * @see AuthService
 * @see AccountService
 */
object ApiFactory {
    private const val BASE_URL = "http://localhost:8080/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authService: AuthService = retrofit.create(AuthService::class.java)
    val accountService: AccountService = retrofit.create(AccountService::class.java)
}