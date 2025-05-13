package com.andef.myworkout.di.viewmodel

import androidx.lifecycle.ViewModel
import com.andef.myworkout.oldPresentation.calendar.main.CalendarScreenViewModel
import com.andef.myworkout.presentation.exercises.addorchange.ExercisesAddOrChangeScreenViewModel
import com.andef.myworkout.presentation.account.changeinfo.AccountChangeInfoScreenViewModel
import com.andef.myworkout.presentation.account.main.AccountMainScreenViewModel
import com.andef.myworkout.presentation.auth.forgotpassword.AuthForgotPasswordScreenViewModel
import com.andef.myworkout.presentation.auth.main.AuthMainScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CalendarScreenViewModel::class)
    fun bindCalendarScreenViewModel(impl: CalendarScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExercisesAddOrChangeScreenViewModel::class)
    fun bindExerciseAddOrChangeScreenViewModel(impl: ExercisesAddOrChangeScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthMainScreenViewModel::class)
    fun bindAuthMainScreenViewModel(impl: AuthMainScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthForgotPasswordScreenViewModel::class)
    fun bindAuthForgotPasswordScreenViewModel(impl: AuthForgotPasswordScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountMainScreenViewModel::class)
    fun bindAccountMainScreenViewModel(impl: AccountMainScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AccountChangeInfoScreenViewModel::class)
    fun bindAccountChangeInfoScreenViewModel(impl: AccountChangeInfoScreenViewModel): ViewModel
}