package com.andef.myworkout.domain.exercise.usecases

import com.andef.myworkout.domain.exercise.entities.Exercise
import com.andef.myworkout.domain.exercise.repository.ExerciseRepository
import javax.inject.Inject

/**
 * Получение списка упражнений для конкретного типа упражнения
 *
 * @see ExerciseRepository
 */
class GetAllExerciseForExerciseTypeUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend fun invoke(token: String, exerciseTypeId: Int): List<Exercise> {
        return repository.getAllExerciseForExerciseType(token, exerciseTypeId)
    }
}