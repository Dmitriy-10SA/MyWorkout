package com.andef.myworkout.presentation.account.main

sealed class AccountMainScreenIntent {
    data class LoadUserInfo(val onUnauthorized: () -> Unit) : AccountMainScreenIntent()
    data class Logout(val onLogout: () -> Unit) : AccountMainScreenIntent()
    data class ChangeUserInfo(val onUnauthorized: () -> Unit) : AccountMainScreenIntent()
    data class PhotoInput(val photo: String) : AccountMainScreenIntent()
}