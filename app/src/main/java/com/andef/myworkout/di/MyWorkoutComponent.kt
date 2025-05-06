package com.andef.myworkout.di

import android.app.Application
import com.andef.myworkout.di.auth.AuthRepositoryModule
import com.andef.myworkout.di.auth.AuthServiceModule
import com.andef.myworkout.di.auth.AuthSharedPreferencesModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        AuthServiceModule::class,
        AuthSharedPreferencesModule::class,
        AuthRepositoryModule::class
    ]
)
interface MyWorkoutComponent {
    @Component.Factory
    interface ComponentFactory {
        fun create(@BindsInstance application: Application): MyWorkoutComponent
    }
}