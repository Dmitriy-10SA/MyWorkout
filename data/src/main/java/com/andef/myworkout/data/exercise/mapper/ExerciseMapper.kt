package com.andef.myworkout.data.exercise.mapper

import com.andef.myworkout.data.exercise.dto.ExerciseDto
import com.andef.myworkout.domain.exercise.entities.Exercise
import javax.inject.Inject

/**
 * @property map преобразование ExerciseDto -> Exercise
 * @property map преобразование List<ExerciseDto> -> List<Exercise>
 * @property map преобразование Exercise -> ExerciseDto
 *
 * @see ExerciseDto
 * @see Exercise
 */
class ExerciseMapper @Inject constructor() {
    fun map(exerciseDto: ExerciseDto) = Exercise(
        id = exerciseDto.id,
        name = exerciseDto.name,
        description = exerciseDto.description,
        video = exerciseDto.video,
        ownerUserId = exerciseDto.ownerUserId,
        muscleId = exerciseDto.muscleId,
        exerciseTypeId = exerciseDto.exerciseTypeId
    )

    fun map(listExerciseDto: List<ExerciseDto>): List<Exercise> {
        return listExerciseDto.map {
            this.map(it)
        }
    }

    fun map(exercise: Exercise) = ExerciseDto(
        id = exercise.id,
        name = exercise.name,
        description = exercise.description,
        video = exercise.video,
        ownerUserId = exercise.ownerUserId,
        muscleId = exercise.muscleId,
        exerciseTypeId = exercise.exerciseTypeId
    )
}