package com.andef.myworkout.domain.exercise.usecases

import com.andef.myworkout.domain.exercise.entities.Exercise
import com.andef.myworkout.domain.exercise.repository.ExerciseRepository
import javax.inject.Inject

/**
 * Получение списка упражнений для конкретной мышцы
 *
 * @see ExerciseRepository
 */
class GetAllExerciseForMuscleUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend fun invoke(token: String, muscleId: Int): List<Exercise> {
        return repository.getAllExerciseForMuscle(token, muscleId)
    }
}