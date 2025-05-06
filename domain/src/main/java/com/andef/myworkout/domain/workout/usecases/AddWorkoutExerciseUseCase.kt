package com.andef.myworkout.domain.workout.usecases

import com.andef.myworkout.domain.workout.entities.WorkoutExerciseAddRequest
import com.andef.myworkout.domain.workout.repository.WorkoutRepository
import javax.inject.Inject

/**
 * Добавление упражнения для тренировки
 *
 * @see WorkoutRepository
 */
class AddWorkoutExerciseUseCase @Inject constructor(private val repository: WorkoutRepository) {
    suspend fun invoke(token: String, workoutExerciseAddRequest: WorkoutExerciseAddRequest) {
        repository.addWorkoutExercise(token, workoutExerciseAddRequest)
    }
}