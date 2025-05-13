package com.andef.myworkout.presentation.auth.forgotpassword

sealed class AuthForgotPasswordScreenIntent {
    data class EmailInput(val email: String) : AuthForgotPasswordScreenIntent()
    data class PasswordInput(val password: String) : AuthForgotPasswordScreenIntent()
    data object PasswordVisibleChange : AuthForgotPasswordScreenIntent()
    data class ChangePassword(
        val onSuccess: () -> Unit,
        val onError: () -> Unit
    ) : AuthForgotPasswordScreenIntent()
}