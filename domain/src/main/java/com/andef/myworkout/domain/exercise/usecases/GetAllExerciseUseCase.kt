package com.andef.myworkout.domain.exercise.usecases

import com.andef.myworkout.domain.exercise.repository.ExerciseRepository
import javax.inject.Inject

/**
 * Получение списка всех упражнений
 *
 * @see ExerciseRepository
 */
class GetAllExerciseUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend fun invoke(token: String) = repository.getAllExercise(token)
}