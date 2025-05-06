package com.andef.myworkout.domain.exercise.usecases

import com.andef.myworkout.domain.exercise.repository.ExerciseRepository
import javax.inject.Inject

/**
 * Удаление упражнения пользователя
 *
 * @see ExerciseRepository
 */
class RemoveUserExerciseUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend fun invoke(token: String, exerciseId: Long) {
        repository.removeUserExercise(token, exerciseId)
    }
}