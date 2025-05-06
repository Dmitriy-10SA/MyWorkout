package com.andef.myworkout.di.auth

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @property provideAuthSharedPreferences получение SharedPreferences для хранения токена
 */
@Module
class AuthSharedPreferencesModule {
    @Provides
    @Singleton
    fun provideAuthSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    }

    companion object {
        private const val PREFS_NAME = "auth_sh_prefs"
    }
}