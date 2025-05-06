package com.andef.myworkout.data.exercise.mapper

import com.andef.myworkout.data.exercise.dto.ExerciseAddRequestDto
import com.andef.myworkout.domain.exercise.entities.ExerciseAddRequest
import javax.inject.Inject

/**
 * @property map преобразование ExerciseAddRequest -> ExerciseAddRequestDto
 *
 * @see ExerciseAddRequestDto
 * @see ExerciseAddRequest
 */
class ExerciseAddRequestMapper @Inject constructor() {
    fun map(exerciseAddRequest: ExerciseAddRequest) = ExerciseAddRequestDto(
        name = exerciseAddRequest.name,
        description = exerciseAddRequest.description,
        video = exerciseAddRequest.video,
        muscleId = exerciseAddRequest.muscleId,
        exerciseTypeId = exerciseAddRequest.exerciseTypeId
    )
}