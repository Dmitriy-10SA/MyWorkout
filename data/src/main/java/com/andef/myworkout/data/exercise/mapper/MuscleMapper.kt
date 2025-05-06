package com.andef.myworkout.data.exercise.mapper

import com.andef.myworkout.data.exercise.dto.MuscleDto
import com.andef.myworkout.domain.exercise.entities.Muscle
import javax.inject.Inject

/**
 * @property map преобразование MuscleDto -> Muscle
 * @property map преобразование List<MuscleDto> -> List<Muscle>
 *
 * @see MuscleDto
 * @see Muscle
 */
class MuscleMapper @Inject constructor() {
    fun map(muscleDto: MuscleDto) = Muscle(
        id = muscleDto.id,
        name = muscleDto.name,
        groupId = muscleDto.groupId
    )

    fun map(listMuscleDto: List<MuscleDto>): List<Muscle> {
        return listMuscleDto.map {
            this.map(it)
        }
    }
}