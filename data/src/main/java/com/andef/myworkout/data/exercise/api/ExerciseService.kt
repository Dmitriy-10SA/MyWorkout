package com.andef.myworkout.data.exercise.api

import com.andef.myworkout.data.exercise.dto.ExerciseAddRequestDto
import com.andef.myworkout.data.exercise.dto.ExerciseDto
import com.andef.myworkout.data.exercise.dto.ExerciseTypeDto
import com.andef.myworkout.data.exercise.dto.GroupDto
import com.andef.myworkout.data.exercise.dto.MuscleDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Query

/**
 * @property getAllMusclesForGroup получение списка мышц по мышечной группе
 * @property getAllGroups получение списка групп
 * @property getAllExerciseTypes получение списка типов упражнений
 * @property addExercise добавление упражнения
 * @property getAllUserExercise получение всех упражнений, созданных пользователем
 * @property changeUserExercise изменение упражнения пользователя
 * @property removeUserExercise удаление упражнения пользователя
 * @property getAllExerciseForExerciseType получение списка упражнений для конкретного типа упражнения
 * @property getAllExerciseForMuscle получение списка упражнений для конкретной мышцы
 * @property getAllExercise получение списка всех упражнений
 * @property getAllExerciseByName получение списка всех упражнений, подходящих (или похожих) по названию
 *
 * @see MuscleDto
 * @see GroupDto
 * @see ExerciseTypeDto
 * @see ExerciseDto
 * @see ExerciseAddRequestDto
 */
interface ExerciseService {
    @GET("exercise/all-muscles")
    suspend fun getAllMusclesForGroup(
        @Header("Authorization") token: String,
        @Query("groupId") groupId: Int
    ): Response<List<MuscleDto>>

    @GET("exercise/all-groups")
    suspend fun getAllGroups(@Header("Authorization") token: String): Response<List<GroupDto>>

    @GET("exercise/all-exercise-types")
    suspend fun getAllExerciseTypes(
        @Header("Authorization") token: String
    ): Response<List<ExerciseTypeDto>>

    @PUT("exercise/add")
    suspend fun addExercise(
        @Header("Authorization") token: String,
        @Body exerciseAddRequestDto: ExerciseAddRequestDto
    ): Response<Unit>

    @GET("exercise/user-exercises")
    suspend fun getAllUserExercise(
        @Header("Authorization") token: String
    ): Response<List<ExerciseDto>>

    @PUT("exercise/change")
    suspend fun changeUserExercise(
        @Header("Authorization") token: String,
        @Body exerciseDto: ExerciseDto
    ): Response<Unit>


    @DELETE("exercise/delete")
    suspend fun removeUserExercise(
        @Header("Authorization") token: String,
        @Query("exerciseId") exerciseId: Long
    ): Response<Unit>

    @GET("exercise/all-for-exercise-type")
    suspend fun getAllExerciseForExerciseType(
        @Header("Authorization") token: String,
        @Query("exerciseTypeId") exerciseTypeId: Int
    ): Response<List<ExerciseDto>>

    @GET("exercise/all-for-muscle")
    suspend fun getAllExerciseForMuscle(
        @Header("Authorization") token: String,
        @Query("muscleId") muscleId: Int
    ): Response<List<ExerciseDto>>

    @GET("exercise/all")
    suspend fun getAllExercise(@Header("Authorization") token: String): Response<List<ExerciseDto>>

    @GET("exercise/all-by-name")
    suspend fun getAllExerciseByName(
        @Header("Authorization") token: String,
        @Query("name") name: String
    ): Response<List<ExerciseDto>>
}