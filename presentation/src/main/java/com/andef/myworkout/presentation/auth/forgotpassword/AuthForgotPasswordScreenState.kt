package com.andef.myworkout.presentation.auth.forgotpassword

data class AuthForgotPasswordScreenState(
    val email: String = "",
    val password: String = "",
    val isValidEmailAndPassword: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMsgResId: Int? = null,
    val isSwitchToMainScreen: Boolean = false
)
