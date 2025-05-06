package com.andef.myworkout.di.auth

import com.andef.myworkout.data.ApiFactory
import com.andef.myworkout.data.auth.api.AuthService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @property provideAuthService получение AuthService
 *
 * @see AuthService
 * @see ApiFactory
 */
@Module
class AuthServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(): AuthService = ApiFactory.authService
}