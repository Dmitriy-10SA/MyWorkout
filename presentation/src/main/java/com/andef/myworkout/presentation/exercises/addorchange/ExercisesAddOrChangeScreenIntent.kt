package com.andef.myworkout.presentation.exercises.addorchange

import com.andef.myworkout.domain.exercise.entities.ExerciseType
import com.andef.myworkout.domain.exercise.entities.Group
import com.andef.myworkout.domain.exercise.entities.Muscle

sealed class ExercisesAddOrChangeScreenIntent {
    data class NameInput(val name: String) : ExercisesAddOrChangeScreenIntent()
    data class DescriptionInput(val description: String) : ExercisesAddOrChangeScreenIntent()
    data class TypeInput(val type: ExerciseType) : ExercisesAddOrChangeScreenIntent()
    data class GroupInput(val group: Group) : ExercisesAddOrChangeScreenIntent()
    data class MuscleInput(val muscle: Muscle) : ExercisesAddOrChangeScreenIntent()
    data class LoadAll(val onUnauthorized: () -> Unit) : ExercisesAddOrChangeScreenIntent()
    data class AddExercise(
        val onSuccess: () -> Unit,
        val onUnauthorized: () -> Unit
    ) : ExercisesAddOrChangeScreenIntent()
}