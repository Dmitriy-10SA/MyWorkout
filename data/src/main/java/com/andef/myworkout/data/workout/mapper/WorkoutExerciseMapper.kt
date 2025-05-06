package com.andef.myworkout.data.workout.mapper

import com.andef.myworkout.data.workout.dto.WorkoutExerciseDto
import com.andef.myworkout.domain.workout.entities.WorkoutExercise
import javax.inject.Inject

/**
 * @property map преобразование WorkoutExerciseDto -> WorkoutExercise
 * @property map преобразование List<WorkoutExerciseDto> -> List<WorkoutExercise>
 *
 * @see WorkoutExerciseDto
 * @see WorkoutExercise
 */
class WorkoutExerciseMapper @Inject constructor() {
    fun map(workoutExerciseDto: WorkoutExerciseDto) = WorkoutExercise(
        id = workoutExerciseDto.id,
        sets = workoutExerciseDto.sets,
        reps = workoutExerciseDto.reps,
        workoutId = workoutExerciseDto.workoutId,
        exerciseId = workoutExerciseDto.exerciseId
    )

    fun map(listWorkoutExerciseDto: List<WorkoutExerciseDto>): List<WorkoutExercise> {
        return listWorkoutExerciseDto.map {
            this.map(it)
        }
    }
}