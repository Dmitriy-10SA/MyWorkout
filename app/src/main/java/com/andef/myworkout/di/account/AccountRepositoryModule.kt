package com.andef.myworkout.di.account

import com.andef.myworkout.data.account.repository.AccountRepositoryImpl
import com.andef.myworkout.domain.account.repository.AccountRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * @property bindAccountRepository получение AccountRepository (точнее AccountRepositoryImpl, как интерфейс)
 *
 * @see AccountRepository
 * @see AccountRepositoryImpl
 */
@Module
interface AccountRepositoryModule {
    @Binds
    @Singleton
    fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository
}