package com.andef.myworkout.di

import android.app.Application
import com.andef.myworkout.di.account.AccountRepositoryModule
import com.andef.myworkout.di.account.AccountServiceModule
import com.andef.myworkout.di.auth.AuthRepositoryModule
import com.andef.myworkout.di.auth.AuthServiceModule
import com.andef.myworkout.di.auth.AuthSharedPreferencesModule
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        //Auth
        AuthServiceModule::class,
        AuthSharedPreferencesModule::class,
        AuthRepositoryModule::class,
        //Account
        AccountServiceModule::class,
        AccountRepositoryModule::class
    ]
)
interface MyWorkoutComponent {
    @Component.Factory
    interface ComponentFactory {
        fun create(@BindsInstance application: Application): MyWorkoutComponent
    }
}