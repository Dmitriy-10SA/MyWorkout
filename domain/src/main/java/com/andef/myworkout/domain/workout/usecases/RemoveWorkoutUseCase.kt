package com.andef.myworkout.domain.workout.usecases

import com.andef.myworkout.domain.workout.repository.WorkoutRepository
import javax.inject.Inject

/**
 * Удаление тренировки
 *
 * @see WorkoutRepository
 */
class RemoveWorkoutUseCase @Inject constructor(private val repository: WorkoutRepository) {
    suspend fun invoke(token: String, workoutId: Long) {
        repository.removeWorkout(token, workoutId)
    }
}