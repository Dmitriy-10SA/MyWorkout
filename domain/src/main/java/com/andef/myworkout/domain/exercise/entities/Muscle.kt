package com.andef.myworkout.domain.exercise.entities

/**
 * Мышца
 *
 * @property id идентификатор
 * @property groupId id мышечной группы
 * @property name название
 */
data class Muscle(
    val id: Int,
    val groupId: Int,
    val name: String
)
