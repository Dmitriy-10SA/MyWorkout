package com.andef.myworkout.data.exercise.repository

import com.andef.myworkout.data.ApiResponse
import com.andef.myworkout.data.exercise.api.ExerciseService
import com.andef.myworkout.data.exercise.mapper.ExerciseAddRequestMapper
import com.andef.myworkout.data.exercise.mapper.ExerciseMapper
import com.andef.myworkout.data.exercise.mapper.ExerciseTypeMapper
import com.andef.myworkout.data.exercise.mapper.GroupMapper
import com.andef.myworkout.data.exercise.mapper.MuscleMapper
import com.andef.myworkout.domain.exercise.entities.Exercise
import com.andef.myworkout.domain.exercise.entities.ExerciseAddRequest
import com.andef.myworkout.domain.exercise.entities.ExerciseType
import com.andef.myworkout.domain.exercise.entities.Group
import com.andef.myworkout.domain.exercise.entities.Muscle
import com.andef.myworkout.domain.exercise.repository.ExerciseRepository
import javax.inject.Inject

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
 * @see ExerciseService
 * @see ApiResponse
 * @see ExerciseAddRequestMapper
 * @see ExerciseMapper
 * @see ExerciseTypeMapper
 * @see GroupMapper
 * @see MuscleMapper
 */
class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseService: ExerciseService,
    private val apiResponse: ApiResponse,
    private val exerciseAddRequestMapper: ExerciseAddRequestMapper,
    private val exerciseMapper: ExerciseMapper,
    private val exerciseTypeMapper: ExerciseTypeMapper,
    private val groupMapper: GroupMapper,
    private val muscleMapper: MuscleMapper
) : ExerciseRepository {
    override suspend fun getAllMusclesForGroup(token: String, groupId: Int): List<Muscle> {
        val response = exerciseService.getAllMusclesForGroup(token, groupId)
        return muscleMapper.map(apiResponse.parseResponseWithoutNullableBody(response))
    }

    override suspend fun getAllGroups(token: String): List<Group> {
        val response = exerciseService.getAllGroups(token)
        return groupMapper.map(apiResponse.parseResponseWithoutNullableBody(response))
    }

    override suspend fun getAllExerciseTypes(token: String): List<ExerciseType> {
        val response = exerciseService.getAllExerciseTypes(token)
        return exerciseTypeMapper.map(apiResponse.parseResponseWithoutNullableBody(response))
    }

    override suspend fun addExercise(token: String, exerciseAddRequest: ExerciseAddRequest) {
        apiResponse.parseResponseWithNullableBody(
            exerciseService.addExercise(token, exerciseAddRequestMapper.map(exerciseAddRequest))
        )
    }

    override suspend fun getAllUserExercise(token: String): List<Exercise> {
        val response = exerciseService.getAllUserExercise(token)
        return exerciseMapper.map(apiResponse.parseResponseWithoutNullableBody(response))
    }

    override suspend fun changeUserExercise(token: String, exercise: Exercise) {
        apiResponse.parseResponseWithNullableBody(
            exerciseService.changeUserExercise(token, exerciseMapper.map(exercise))
        )
    }

    override suspend fun removeUserExercise(token: String, exerciseId: Long) {
        apiResponse.parseResponseWithNullableBody(
            exerciseService.removeUserExercise(token, exerciseId)
        )
    }

    override suspend fun getAllExerciseForExerciseType(
        token: String,
        exerciseTypeId: Int
    ): List<Exercise> {
        val response = exerciseService.getAllExerciseForExerciseType(token, exerciseTypeId)
        return exerciseMapper.map(apiResponse.parseResponseWithoutNullableBody(response))
    }

    override suspend fun getAllExerciseForMuscle(token: String, muscleId: Int): List<Exercise> {
        val response = exerciseService.getAllExerciseForMuscle(token, muscleId)
        return exerciseMapper.map(apiResponse.parseResponseWithoutNullableBody(response))
    }

    override suspend fun getAllExercise(token: String): List<Exercise> {
        val response = exerciseService.getAllExercise(token)
        return exerciseMapper.map(apiResponse.parseResponseWithoutNullableBody(response))
    }

    override suspend fun getAllExerciseByName(token: String, name: String): List<Exercise> {
        val response = exerciseService.getAllExerciseByName(token, name)
        return exerciseMapper.map(apiResponse.parseResponseWithoutNullableBody(response))
    }
}