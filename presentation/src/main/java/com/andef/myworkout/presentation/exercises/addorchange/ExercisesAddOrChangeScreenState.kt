package com.andef.myworkout.presentation.exercises.addorchange

import com.andef.myworkout.domain.exercise.entities.ExerciseType
import com.andef.myworkout.domain.exercise.entities.Group
import com.andef.myworkout.domain.exercise.entities.Muscle

data class ExercisesAddOrChangeScreenState(
    val name: String = "",
    val description: String = "",
    val video: String? = null,
    val type: ExerciseType? = null,
    val types: List<ExerciseType>? = null,
    val group: Group? = null,
    val groups: List<Group>? = null,
    val muscle: Muscle? = null,
    val muscles: Map<Group, List<Muscle>>? = null,
    val isValidInfoForAdd: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
