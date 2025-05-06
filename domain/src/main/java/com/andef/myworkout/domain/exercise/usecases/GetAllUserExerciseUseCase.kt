package com.andef.myworkout.domain.exercise.usecases

import com.andef.myworkout.domain.exercise.repository.ExerciseRepository
import javax.inject.Inject

/**
 * Получение всех упражнений, созданных пользователем
 *
 * @see ExerciseRepository
 */
class GetAllUserExerciseUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend fun invoke(token: String) = repository.getAllUserExercise(token)
}