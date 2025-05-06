package com.andef.myworkout.domain.workout.usecases

import com.andef.myworkout.domain.workout.repository.WorkoutRepository
import javax.inject.Inject

/**
 * Удаление упражнения для тренировки
 *
 * @see WorkoutRepository
 */
class RemoveWorkoutExerciseUseCase @Inject constructor(private val repository: WorkoutRepository) {
    suspend fun invoke(token: String, workoutExerciseId: Long) {
        repository.removeWorkout(token, workoutExerciseId)
    }
}