package com.andef.myworkout.di.viewmodel

import androidx.lifecycle.ViewModel
import com.andef.myworkout.oldPresentation.account.main.AccountScreenViewModel
import com.andef.myworkout.oldPresentation.auth.main.AuthScreenViewModel
import com.andef.myworkout.oldPresentation.calendar.main.CalendarScreenViewModel
import com.andef.myworkout.oldPresentation.exercises.addorchange.ExerciseAddOrChangeScreenViewModel
import com.andef.myworkout.oldPresentation.exercises.main.ExercisesScreenViewModel
import com.andef.myworkout.presentation.auth.forgotpassword.AuthForgotPasswordScreenViewModel
import com.andef.myworkout.presentation.auth.main.AuthMainScreenViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(ExercisesScreenViewModel::class)
    fun bindExercisesScreenViewModel(impl: ExercisesScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ExerciseAddOrChangeScreenViewModel::class)
    fun bindExerciseAddOrChangeScreenViewModel(impl: ExerciseAddOrChangeScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthMainScreenViewModel::class)
    fun bindAuthMainScreenViewModel(impl: AuthMainScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthForgotPasswordScreenViewModel::class)
    fun bindAuthForgotPasswordScreenViewModel(impl: AuthForgotPasswordScreenViewModel): ViewModel
}