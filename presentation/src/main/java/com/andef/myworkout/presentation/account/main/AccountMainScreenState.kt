package com.andef.myworkout.presentation.account.main

import com.andef.myworkout.domain.account.entities.UserInfo

data class AccountMainScreenState(
    val userInfo: UserInfo? = null,
    val surname: String = "",
    val name: String = "",
    val patronymic: String = "",
    val photo: String? = null,
    val isValidInfoForChange: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)