package com.andef.myworkout.data.workout.mapper

import com.andef.myworkout.data.workout.dto.WorkoutExerciseAddRequestDto
import com.andef.myworkout.domain.workout.entities.WorkoutExerciseAddRequest
import javax.inject.Inject

/**
 * @property map преобразование WorkoutExerciseAddRequest -> WorkoutExerciseAddRequestDto
 *
 * @see WorkoutExerciseAddRequest
 * @see WorkoutExerciseAddRequestDto
 */
class WorkoutExerciseAddRequestMapper @Inject constructor() {
    fun map(workoutExerciseAddRequest: WorkoutExerciseAddRequest) = WorkoutExerciseAddRequestDto(
        sets = workoutExerciseAddRequest.sets,
        reps = workoutExerciseAddRequest.reps,
        workoutId = workoutExerciseAddRequest.workoutId,
        exerciseId = workoutExerciseAddRequest.exerciseId
    )
}