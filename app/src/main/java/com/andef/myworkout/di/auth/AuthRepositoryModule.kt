package com.andef.myworkout.di.auth

import com.andef.myworkout.data.auth.repository.AuthRepositoryImpl
import com.andef.myworkout.domain.auth.repository.AuthRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * @property bindAuthRepository получение AuthRepository (точнее AuthRepositoryImpl, как интерфейс)
 *
 * @see AuthRepository
 * @see AuthRepositoryImpl
 */
@Module
interface AuthRepositoryModule {
    @Binds
    @Singleton
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}