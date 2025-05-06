package com.andef.myworkout.di.account

import com.andef.myworkout.data.ApiFactory
import com.andef.myworkout.data.account.api.AccountService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * @property provideAccountService получение AccountService
 *
 * @see AccountService
 * @see ApiFactory
 */
@Module
class AccountServiceModule {
    @Provides
    @Singleton
    fun provideAccountService(): AccountService = ApiFactory.accountService
}