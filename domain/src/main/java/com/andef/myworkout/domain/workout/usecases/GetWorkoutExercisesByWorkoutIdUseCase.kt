package com.andef.myworkout.domain.workout.usecases

import com.andef.myworkout.domain.workout.entities.WorkoutExercise
import com.andef.myworkout.domain.workout.repository.WorkoutRepository
import javax.inject.Inject

/**
 * Получение списка упражнений для тренировки по id тренировки
 *
 * @see WorkoutRepository
 */
class GetWorkoutExercisesByWorkoutIdUseCase @Inject constructor(
    private val repository: WorkoutRepository
) {
    suspend fun invoke(token: String, workoutId: Long): List<WorkoutExercise> {
        return repository.getWorkoutExercisesByWorkoutId(token, workoutId)
    }
}