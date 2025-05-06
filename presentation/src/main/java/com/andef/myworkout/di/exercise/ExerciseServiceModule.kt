package com.andef.myworkout.di.exercise

import com.andef.myworkout.data.ApiFactory
import com.andef.myworkout.data.exercise.api.ExerciseService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @property provideExerciseService получение ExerciseService
 *
 * @see ExerciseService
 * @see ApiFactory
 */
@Module
class ExerciseServiceModule {
    @Provides
    @Singleton
    fun provideExerciseService(): ExerciseService = ApiFactory.exerciseService
}