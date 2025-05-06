package com.andef.myworkout.data.exercise.mapper

import com.andef.myworkout.data.exercise.dto.GroupDto
import com.andef.myworkout.domain.exercise.entities.Group
import javax.inject.Inject

/**
 * @property map преобразование GroupDto -> Group
 * @property map преобразование List<GroupDto> -> List<Group>
 *
 * @see GroupDto
 * @see Group
 */
class GroupMapper @Inject constructor() {
    fun map(groupDto: GroupDto) = Group(
        id = groupDto.id,
        name = groupDto.name
    )

    fun map(listGroupDto: List<GroupDto>): List<Group> {
        return listGroupDto.map {
            this.map(it)
        }
    }
}