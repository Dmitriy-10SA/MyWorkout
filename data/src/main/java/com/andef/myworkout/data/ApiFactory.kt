package com.andef.myworkout.data

import com.andef.myworkout.data.auth.api.AuthService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @property authService получение AuthService
 *
 * @see AuthService
 */
object ApiFactory {
    private const val BASE_URL = "http://localhost:8080/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authService = retrofit.create(AuthService::class.java)
}