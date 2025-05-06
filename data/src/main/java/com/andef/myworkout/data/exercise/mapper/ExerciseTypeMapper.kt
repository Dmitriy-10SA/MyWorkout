package com.andef.myworkout.data.exercise.mapper

import com.andef.myworkout.data.exercise.dto.ExerciseTypeDto
import com.andef.myworkout.domain.exercise.entities.ExerciseType
import javax.inject.Inject

/**
 * @property map преобразование ExerciseTypeDto -> ExerciseType
 * @property map преобразование List<ExerciseTypeDto> -> List<ExerciseType>
 *
 * @see ExerciseTypeDto
 * @see ExerciseType
 */
class ExerciseTypeMapper @Inject constructor() {
    fun map(exerciseTypeDto: ExerciseTypeDto) = ExerciseType(
        id = exerciseTypeDto.id,
        name = exerciseTypeDto.name
    )

    fun map(listExerciseTypeDto: List<ExerciseTypeDto>): List<ExerciseType> {
        return listExerciseTypeDto.map {
            this.map(it)
        }
    }
}