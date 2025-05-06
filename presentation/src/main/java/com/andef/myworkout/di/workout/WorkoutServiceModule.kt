package com.andef.myworkout.di.workout

import com.andef.myworkout.data.ApiFactory
import com.andef.myworkout.data.workout.api.WorkoutService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @property provideWorkoutService получение WorkoutService
 *
 * @see WorkoutService
 * @see ApiFactory
 */
@Module
class WorkoutServiceModule {
    @Provides
    @Singleton
    fun provideWorkoutService(): WorkoutService = ApiFactory.workoutService
}