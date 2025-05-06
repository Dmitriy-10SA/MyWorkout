package com.andef.myworkout.data.workout.api

import com.andef.myworkout.data.workout.dto.WorkoutAddRequestDto
import com.andef.myworkout.data.workout.dto.WorkoutDto
import com.andef.myworkout.data.workout.dto.WorkoutExerciseAddRequestDto
import com.andef.myworkout.data.workout.dto.WorkoutExerciseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query

/**
 * @property getWorkoutByDate получение списка тренировок на дату
 * @property addWorkout добавление тренировки
 * @property removeWorkout удаление тренировки
 * @property addWorkoutExercise добавление упражнения для тренировки
 * @property removeWorkoutExercise удаление упражнения для тренировки
 * @property getWorkoutExercisesByWorkoutId получение списка упражнений для тренировки по id тренировки
 *
 * @see WorkoutDto
 * @see WorkoutAddRequestDto
 * @see WorkoutExerciseDto
 * @see WorkoutExerciseAddRequestDto
 */
interface WorkoutService {
    @GET("workout/by-date")
    suspend fun getWorkoutByDate(
        @Header("Authorization") token: String,
        @Query("date") date: String
    ): Response<List<WorkoutDto>>

    @PUT("workout/add")
    suspend fun addWorkout(
        @Header("Authorization") token: String,
        @Body workoutAddRequestDto: WorkoutAddRequestDto
    ): Response<Unit>

    @DELETE("workout/delete-workout")
    suspend fun removeWorkout(
        @Header("Authorization") token: String,
        @Query("workoutId") workoutId: Long
    ): Response<Unit>

    @PUT("workout/add-workout-exercise")
    suspend fun addWorkoutExercise(
        @Header("Authorization") token: String,
        @Body workoutExerciseAddRequestDto: WorkoutExerciseAddRequestDto
    ): Response<Unit>

    @DELETE("workout/delete-workout-exercise")
    suspend fun removeWorkoutExercise(
        @Header("Authorization") token: String,
        @Query("workoutExerciseId") workoutExerciseId: Long
    ): Response<Unit>

    @GET("workout/workout-exercises")
    suspend fun getWorkoutExercisesByWorkoutId(
        @Header("Authorization") token: String,
        @Query("workoutId") workoutId: Long
    ): Response<List<WorkoutExerciseDto>>
}