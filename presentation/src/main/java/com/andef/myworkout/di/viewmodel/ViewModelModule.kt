package com.andef.myworkout.di.viewmodel

import androidx.lifecycle.ViewModel
import com.andef.myworkout.presentation.account.main.AccountScreenViewModel
import com.andef.myworkout.presentation.auth.main.AuthScreenViewModel
import com.andef.myworkout.presentation.calendar.main.CalendarScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthScreenViewModel::class)
    fun bindAuthScreenViewModel(impl: AuthScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountScreenViewModel::class)
    fun bindAccountScreenViewModel(impl: AccountScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CalendarScreenViewModel::class)
    fun bindCalendarScreenViewModel(impl: CalendarScreenViewModel): ViewModel
}