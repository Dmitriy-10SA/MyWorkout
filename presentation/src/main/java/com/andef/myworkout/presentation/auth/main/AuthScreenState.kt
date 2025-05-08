package com.andef.myworkout.presentation.auth.main

data class AuthScreenState(
    val email: String = "a@m.r",
    val password: String = "12416",
    val surname: String = "",
    val name: String = "",
    val patronymic: String = "",
    val isLogin: Boolean = true,
    val isShowForgotPassword: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isValidEmailAndPassword: Boolean = false,
    val isValidSignUpInfo: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
