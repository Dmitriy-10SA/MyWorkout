package com.andef.myworkout.oldPresentation.exercises.addorchange

import com.andef.myworkout.domain.exercise.entities.ExerciseType

sealed class ExercisesAddOrChangeScreenIntent {
    data class NameInput(val name: String) : ExercisesAddOrChangeScreenIntent()
    data class DescriptionInput(val description: String) : ExercisesAddOrChangeScreenIntent()
    data class TypeInput(val type: ExerciseType) : ExercisesAddOrChangeScreenIntent()
    data class LoadTypes(val onUnauthorized: () -> Unit) : ExercisesAddOrChangeScreenIntent()
}