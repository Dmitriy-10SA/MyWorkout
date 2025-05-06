package com.andef.myworkout.data.workout.repository

import com.andef.myworkout.data.ApiResponse
import com.andef.myworkout.data.workout.api.WorkoutService
import com.andef.myworkout.data.workout.mapper.WorkoutAddRequestMapper
import com.andef.myworkout.data.workout.mapper.WorkoutExerciseAddRequestMapper
import com.andef.myworkout.data.workout.mapper.WorkoutExerciseMapper
import com.andef.myworkout.data.workout.mapper.WorkoutMapper
import com.andef.myworkout.domain.workout.entities.Workout
import com.andef.myworkout.domain.workout.entities.WorkoutAddRequest
import com.andef.myworkout.domain.workout.entities.WorkoutExercise
import com.andef.myworkout.domain.workout.entities.WorkoutExerciseAddRequest
import com.andef.myworkout.domain.workout.repository.WorkoutRepository
import kotlinx.datetime.LocalDate
import javax.inject.Inject

/**
 * @property getWorkoutByDate получение списка тренировок на дату
 * @property addWorkout добавление тренировки
 * @property removeWorkout удаление тренировки
 * @property addWorkoutExercise добавление упражнения для тренировки
 * @property removeWorkoutExercise удаление упражнения для тренировки
 * @property getWorkoutExercisesByWorkoutId получение списка упражнений для тренировки по id тренировки
 *
 * @see WorkoutService
 * @see ApiResponse
 * @see WorkoutAddRequestMapper
 * @see WorkoutMapper
 * @see WorkoutExerciseMapper
 * @see WorkoutExerciseAddRequestMapper
 */
class WorkoutRepositoryImpl @Inject constructor(
    private val workoutService: WorkoutService,
    private val apiResponse: ApiResponse,
    private val workoutAddRequestMapper: WorkoutAddRequestMapper,
    private val workoutMapper: WorkoutMapper,
    private val workoutExerciseMapper: WorkoutExerciseMapper,
    private val workoutExerciseAddRequestMapper: WorkoutExerciseAddRequestMapper
) : WorkoutRepository {
    override suspend fun getWorkoutByDate(token: String, date: LocalDate): List<Workout> {
        val response = workoutService.getWorkoutByDate(token, date.toString())
        return workoutMapper.map(apiResponse.parseResponseWithoutNullableBody(response))
    }

    override suspend fun addWorkout(token: String, workoutAddRequest: WorkoutAddRequest) {
        apiResponse.parseResponseWithNullableBody(
            workoutService.addWorkout(token, workoutAddRequestMapper.map(workoutAddRequest))
        )
    }

    override suspend fun removeWorkout(token: String, workoutId: Long) {
        apiResponse.parseResponseWithNullableBody(
            workoutService.removeWorkout(token, workoutId)
        )
    }

    override suspend fun addWorkoutExercise(
        token: String,
        workoutExerciseAddRequest: WorkoutExerciseAddRequest
    ) {
        apiResponse.parseResponseWithNullableBody(
            workoutService.addWorkoutExercise(
                token,
                workoutExerciseAddRequestMapper.map(workoutExerciseAddRequest)
            )
        )
    }

    override suspend fun removeWorkoutExercise(token: String, workoutExerciseId: Long) {
        apiResponse.parseResponseWithNullableBody(
            workoutService.removeWorkoutExercise(token, workoutExerciseId)
        )
    }

    override suspend fun getWorkoutExercisesByWorkoutId(
        token: String,
        workoutId: Long
    ): List<WorkoutExercise> {
        val response = workoutService.getWorkoutExercisesByWorkoutId(token, workoutId)
        return workoutExerciseMapper.map(apiResponse.parseResponseWithoutNullableBody(response))
    }
}