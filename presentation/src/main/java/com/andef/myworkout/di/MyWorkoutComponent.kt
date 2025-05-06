package com.andef.myworkout.di

import android.app.Application
import androidx.activity.ComponentActivity
import com.andef.myworkout.di.account.AccountRepositoryModule
import com.andef.myworkout.di.account.AccountServiceModule
import com.andef.myworkout.di.auth.AuthRepositoryModule
import com.andef.myworkout.di.auth.AuthServiceModule
import com.andef.myworkout.di.auth.AuthSharedPreferencesModule
import com.andef.myworkout.di.exercise.ExerciseRepositoryModule
import com.andef.myworkout.di.exercise.ExerciseServiceModule
import com.andef.myworkout.di.workout.WorkoutRepositoryModule
import com.andef.myworkout.di.workout.WorkoutServiceModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        //ViewModel
        //ViewModelModule::class,
        //Auth
        AuthServiceModule::class,
        AuthSharedPreferencesModule::class,
        AuthRepositoryModule::class,
        //Account
        AccountServiceModule::class,
        AccountRepositoryModule::class,
        //Exercise
        ExerciseServiceModule::class,
        ExerciseRepositoryModule::class,
        //Workout
        WorkoutServiceModule::class,
        WorkoutRepositoryModule::class
    ]
)
interface MyWorkoutComponent {
    fun inject(activity: ComponentActivity)

    @Component.Factory
    interface ComponentFactory {
        fun create(@BindsInstance application: Application): MyWorkoutComponent
    }
}