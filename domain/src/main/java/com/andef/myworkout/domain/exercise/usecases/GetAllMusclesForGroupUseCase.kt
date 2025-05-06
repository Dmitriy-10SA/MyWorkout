package com.andef.myworkout.domain.exercise.usecases

import com.andef.myworkout.domain.exercise.entities.Muscle
import com.andef.myworkout.domain.exercise.repository.ExerciseRepository
import javax.inject.Inject

/**
 * Получение списка мышц по мышечной группе
 *
 * @see ExerciseRepository
 */
class GetAllMusclesForGroupUseCase @Inject constructor(private val repository: ExerciseRepository) {
    suspend fun invoke(token: String, groupId: Int): List<Muscle> {
        return repository.getAllMusclesForGroup(token, groupId)
    }
}