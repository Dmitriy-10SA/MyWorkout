package com.andef.myworkout.data.workout.mapper

import com.andef.myworkout.data.workout.dto.WorkoutDto
import com.andef.myworkout.domain.workout.entities.Workout
import kotlinx.datetime.LocalDate
import javax.inject.Inject

/**
 * @property map преобразование WorkoutDto -> Workout
 * @property map преобразование List<WorkoutDto> -> List<Workout>
 *
 * @see WorkoutDto
 * @see Workout
 */
class WorkoutMapper @Inject constructor() {
    fun map(workoutDto: WorkoutDto) = Workout(
        id = workoutDto.id,
        name = workoutDto.name,
        date = LocalDate.parse(workoutDto.date),
        ownerUserId = workoutDto.ownerUserId
    )

    fun map(listWorkoutDto: List<WorkoutDto>): List<Workout> {
        return listWorkoutDto.map {
            this.map(it)
        }
    }
}