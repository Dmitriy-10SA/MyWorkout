package com.andef.myworkout.domain.exercise.repository

import com.andef.myworkout.domain.exercise.entities.Exercise
import com.andef.myworkout.domain.exercise.entities.ExerciseAddRequest
import com.andef.myworkout.domain.exercise.entities.ExerciseType
import com.andef.myworkout.domain.exercise.entities.Group
import com.andef.myworkout.domain.exercise.entities.Muscle

/**
 * @property getAllMusclesForGroup получение списка мышц по мышечной группе
 * @property getAllGroups получение списка групп
 * @property getAllExerciseTypes получение списка типов упражнений
 * @property addExercise добавление упражнения
 * @property getAllUserExercise получение всех упражнений, созданных пользователем
 * @property changeUserExercise изменение упражнения пользователя
 * @property removeUserExercise удаление упражнения пользователя
 * @property getAllExerciseForExerciseType получение списка упражнений для конкретного типа упражнения
 * @property getAllExerciseForMuscle получение списка упражнений для конкретной мышцы
 * @property getAllExercise получение списка всех упражнений
 * @property getAllExerciseByName получение списка всех упражнений, подходящих (или похожих) по названию
 *
 * @see Muscle
 * @see Group
 * @see ExerciseType
 * @see Exercise
 * @see ExerciseAddRequest
 */
interface ExerciseRepository {
    suspend fun getAllMusclesForGroup(token: String, groupId: Int): List<Muscle>
    suspend fun getAllGroups(token: String): List<Group>
    suspend fun getAllExerciseTypes(token: String): List<ExerciseType>
    suspend fun addExercise(token: String, exerciseAddRequest: ExerciseAddRequest)
    suspend fun getAllUserExercise(token: String): List<Exercise>
    suspend fun changeUserExercise(token: String, exercise: Exercise)
    suspend fun removeUserExercise(token: String, exerciseId: Long)
    suspend fun getAllExerciseForExerciseType(token: String, exerciseTypeId: Int): List<Exercise>
    suspend fun getAllExerciseForMuscle(token: String, muscleId: Int): List<Exercise>
    suspend fun getAllExercise(token: String): List<Exercise>
    suspend fun getAllExerciseByName(token: String, name: String): List<Exercise>
}