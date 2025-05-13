package com.andef.myworkout.oldPresentation.auth.main

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
    data class Login(val onSuccess: () -> Unit, val onError: () -> Unit) : AuthScreenIntent()
    data class PasswordChange(
        val onSuccess: () -> Unit,
        val onError: () -> Unit
    ) : AuthScreenIntent()

    data class SignUp(val onSuccess: () -> Unit, val onError: () -> Unit) : AuthScreenIntent()
    data class CheckToken(val onSuccess: () -> Unit) : AuthScreenIntent()
}