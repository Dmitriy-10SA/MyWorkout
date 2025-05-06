package com.andef.myworkout.domain.exercise.usecases

import com.andef.myworkout.domain.exercise.repository.ExerciseRepository
import javax.inject.Inject

/**
 * Получение списка типов упражнений
 *
 * @see ExerciseRepository
 */
class GetAllExerciseTypesUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend fun invoke(token: String) = repository.getAllExerciseTypes(token)
}