package com.andef.myworkout.domain.exercise.usecases

import com.andef.myworkout.domain.exercise.repository.ExerciseRepository
import javax.inject.Inject

/**
 * Получение списка групп
 *
 * @see ExerciseRepository
 */
class GetAllGroupsUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend fun invoke(token: String) = repository.getAllGroups(token)
}