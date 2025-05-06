package com.andef.myworkout.data.workout.mapper

import com.andef.myworkout.data.workout.dto.WorkoutAddRequestDto
import com.andef.myworkout.domain.workout.entities.WorkoutAddRequest
import javax.inject.Inject

/**
 * @property map преобразование WorkoutAddRequest -> WorkoutAddRequestDto
 *
 * @see WorkoutAddRequest
 * @see WorkoutAddRequestDto
 */
class WorkoutAddRequestMapper @Inject constructor() {
    fun map(workoutAddRequest: WorkoutAddRequest) = WorkoutAddRequestDto(
        name = workoutAddRequest.name,
        date = workoutAddRequest.date.toString()
    )
}