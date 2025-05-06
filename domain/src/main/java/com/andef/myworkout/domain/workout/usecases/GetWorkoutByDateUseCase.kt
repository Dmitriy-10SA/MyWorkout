package com.andef.myworkout.domain.workout.usecases

import com.andef.myworkout.domain.workout.repository.WorkoutRepository
import kotlinx.datetime.LocalDate
import javax.inject.Inject

/**
 * Получение списка тренировок на дату
 *
 * @see WorkoutRepository
 */
class GetWorkoutByDateUseCase @Inject constructor(private val repository: WorkoutRepository) {
    suspend fun invoke(token: String, date: LocalDate) = repository.getWorkoutByDate(token, date)
}