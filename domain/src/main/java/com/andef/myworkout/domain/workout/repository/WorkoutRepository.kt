package com.andef.myworkout.domain.workout.repository

import com.andef.myworkout.domain.workout.entities.Workout
import com.andef.myworkout.domain.workout.entities.WorkoutAddRequest
import com.andef.myworkout.domain.workout.entities.WorkoutExercise
import com.andef.myworkout.domain.workout.entities.WorkoutExerciseAddRequest
import kotlinx.datetime.LocalDate

/**
 * @property getWorkoutByDate получение списка тренировок на дату
 * @property addWorkout добавление тренировки
 * @property removeWorkout удаление тренировки
 * @property addWorkoutExercise добавление упражнения для тренировки
 * @property removeWorkoutExercise удаление упражнения для тренировки
 * @property getWorkoutExercisesByWorkoutId получение списка упражнений для тренировки по id тренировки
 *
 * @see Workout
 * @see WorkoutAddRequest
 * @see WorkoutExerciseAddRequest
 * @see WorkoutExercise
 */
interface WorkoutRepository {
    suspend fun getWorkoutByDate(token: String, date: LocalDate): List<Workout>
    suspend fun addWorkout(token: String, workoutAddRequest: WorkoutAddRequest)
    suspend fun removeWorkout(token: String, workoutId: Long)
    suspend fun addWorkoutExercise(
        token: String,
        workoutExerciseAddRequest: WorkoutExerciseAddRequest
    )

    suspend fun removeWorkoutExercise(token: String, workoutExerciseId: Long)
    suspend fun getWorkoutExercisesByWorkoutId(
        token: String,
        workoutId: Long
    ): List<WorkoutExercise>
}