package com.andef.myworkout.presentation.auth.main

data class AuthMainScreenState(
    val email: String = "",
    val password: String = "",
    val surname: String = "",
    val name: String = "",
    val patronymic: String = "",
    val isLogin: Boolean = true,
    val isPasswordVisible: Boolean = false,
    val isValidEmailAndPassword: Boolean = false,
    val isValidSignUpInfo: Boolean = false,
    val errorMsgResId: Int? = null,
    val isCheckTokenLoading: Boolean = true,
    val isSwitchToMainScreen: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
