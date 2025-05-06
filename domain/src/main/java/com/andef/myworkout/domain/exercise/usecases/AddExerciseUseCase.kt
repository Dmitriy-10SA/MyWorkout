package com.andef.myworkout.domain.exercise.usecases

import com.andef.myworkout.domain.exercise.entities.ExerciseAddRequest
import com.andef.myworkout.domain.exercise.repository.ExerciseRepository
import javax.inject.Inject

/**
 * Добавление упражнения
 *
 * @see ExerciseRepository
 */
class AddExerciseUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend fun invoke(token: String, exerciseAddRequest: ExerciseAddRequest) {
        repository.addExercise(token, exerciseAddRequest)
    }
}