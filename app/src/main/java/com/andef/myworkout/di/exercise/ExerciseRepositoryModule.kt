package com.andef.myworkout.di.exercise

import com.andef.myworkout.data.exercise.repository.ExerciseRepositoryImpl
import com.andef.myworkout.domain.exercise.repository.ExerciseRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * @property bindExerciseRepository получение ExerciseRepository (точнее ExerciseRepositoryImpl, как интерфейс)
 *
 * @see ExerciseRepository
 * @see ExerciseRepositoryImpl
 */
@Module
interface ExerciseRepositoryModule {
    @Binds
    @Singleton
    fun bindExerciseRepository(impl: ExerciseRepositoryImpl): ExerciseRepository
}