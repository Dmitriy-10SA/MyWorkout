package com.andef.myworkout.domain.exercise.usecases

import com.andef.myworkout.domain.exercise.entities.Exercise
import com.andef.myworkout.domain.exercise.repository.ExerciseRepository
import javax.inject.Inject

/**
 * Получение списка всех упражнений, подходящих (или похожих) по названию
 *
 * @see ExerciseRepository
 */
class GetAllExerciseByNameUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend fun invoke(token: String, name: String): List<Exercise> {
        return repository.getAllExerciseByName(token, name)
    }
}