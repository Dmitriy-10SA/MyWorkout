package com.andef.myworkout.presentation.auth.main

sealed class AuthScreenIntent {
    data object LoginChoose : AuthScreenIntent()
    data object SignUpChoose : AuthScreenIntent()
    data class EmailInput(val email: String) : AuthScreenIntent()
    data class PasswordInput(val password: String) : AuthScreenIntent()
    data class SurnameInput(val surname: String) : AuthScreenIntent()
    data class NameInput(val name: String) : AuthScreenIntent()
    data class PatronymicInput(val patronymic: String) : AuthScreenIntent()
    data object PasswordVisibleChange : AuthScreenIntent()
    data object ShowForgotPasswordVisibleChange : AuthScreenIntent()
    data object Login : AuthScreenIntent()
    data object PasswordChange : AuthScreenIntent()
    data object SignUp : AuthScreenIntent()
}