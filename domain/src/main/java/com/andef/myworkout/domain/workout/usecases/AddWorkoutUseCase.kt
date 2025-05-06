package com.andef.myworkout.domain.workout.usecases

import com.andef.myworkout.domain.workout.entities.WorkoutAddRequest
import com.andef.myworkout.domain.workout.repository.WorkoutRepository
import javax.inject.Inject

/**
 * Добавление тренировки
 *
 * @see WorkoutRepository
 */
class AddWorkoutUseCase @Inject constructor(private val repository: WorkoutRepository) {
    suspend fun invoke(token: String, workoutAddRequest: WorkoutAddRequest) {
        repository.addWorkout(token, workoutAddRequest)
    }
}