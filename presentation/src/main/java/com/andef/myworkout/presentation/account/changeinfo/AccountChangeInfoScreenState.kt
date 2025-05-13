package com.andef.myworkout.presentation.account.changeinfo

data class AccountChangeInfoScreenState(
    val surname: String = "",
    val name: String = "",
    val patronymic: String = "",
    val photo: String? = null,
    val isValidInfoForChange: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
