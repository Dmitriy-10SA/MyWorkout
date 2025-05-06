package com.andef.myworkout.domain.exercise.usecases

import com.andef.myworkout.domain.exercise.entities.Exercise
import com.andef.myworkout.domain.exercise.repository.ExerciseRepository
import javax.inject.Inject

/**
 * Изменение упражнения пользователя
 *
 * @see ExerciseRepository
 */
class ChangeUserExerciseUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend fun invoke(token: String, exercise: Exercise) {
        repository.changeUserExercise(token, exercise)
    }
}