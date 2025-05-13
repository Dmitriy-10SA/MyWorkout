package com.andef.myworkout.presentation.auth.main

sealed class AuthMainScreenIntent {
    data object LoginChoose : AuthMainScreenIntent()
    data object SignUpChoose : AuthMainScreenIntent()
    data class EmailInput(val email: String) : AuthMainScreenIntent()
    data class PasswordInput(val password: String) : AuthMainScreenIntent()
    data class SurnameInput(val surname: String) : AuthMainScreenIntent()
    data class NameInput(val name: String) : AuthMainScreenIntent()
    data class PatronymicInput(val patronymic: String) : AuthMainScreenIntent()
    data object PasswordVisibleChange : AuthMainScreenIntent()
    data class Login(val onSuccess: () -> Unit, val onError: () -> Unit) : AuthMainScreenIntent()
    data class SignUp(val onSuccess: () -> Unit, val onError: () -> Unit) : AuthMainScreenIntent()
    data class CheckToken(val onSuccess: () -> Unit) : AuthMainScreenIntent()
}