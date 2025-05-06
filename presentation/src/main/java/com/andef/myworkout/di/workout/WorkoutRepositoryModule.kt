package com.andef.myworkout.di.workout

import com.andef.myworkout.data.workout.repository.WorkoutRepositoryImpl
import com.andef.myworkout.domain.workout.repository.WorkoutRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * @property bindWorkoutRepository получение WorkoutRepository (точнее WorkoutRepositoryImpl, как интерфейс)
 *
 * @see WorkoutRepository
 * @see WorkoutRepositoryImpl
 */
@Module
interface WorkoutRepositoryModule {
    @Binds
    @Singleton
    fun bindWorkoutRepository(impl: WorkoutRepositoryImpl): WorkoutRepository
}