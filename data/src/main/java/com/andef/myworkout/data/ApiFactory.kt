package com.andef.myworkout.data

import com.andef.myworkout.data.ApiFactory.accountService
import com.andef.myworkout.data.ApiFactory.authService
import com.andef.myworkout.data.ApiFactory.exerciseService
import com.andef.myworkout.data.ApiFactory.workoutService
import com.andef.myworkout.data.account.api.AccountService
import com.andef.myworkout.data.auth.api.AuthService
import com.andef.myworkout.data.exercise.api.ExerciseService
import com.andef.myworkout.data.workout.api.WorkoutService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @property authService получение AuthService
 * @property accountService получение AccountService
 * @property exerciseService получение ExerciseService
 * @property workoutService получение WorkoutService
 *
 * @see AuthService
 * @see AccountService
 * @see ExerciseService
 * @see WorkoutService
 */
object ApiFactory {
    private const val BASE_URL = "http://192.168.0.13:8080/"
    private val interceptor = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
        .client(client)
        .build()

    val authService: AuthService = retrofit.create(AuthService::class.java)
    val accountService: AccountService = retrofit.create(AccountService::class.java)
    val exerciseService: ExerciseService = retrofit.create(ExerciseService::class.java)
    val workoutService: WorkoutService = retrofit.create(WorkoutService::class.java)
}