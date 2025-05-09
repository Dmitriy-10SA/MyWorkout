package com.andef.myworkout.presentation.account.main

sealed class AccountScreenIntent {
    data class LoadUserInfo(val onUnauthorized: () -> Unit) : AccountScreenIntent()
    data class ChangeUserInfo(val onUnauthorized: () -> Unit) : AccountScreenIntent()
    data class SurnameInput(val surname: String) : AccountScreenIntent()
    data class NameInput(val name: String) : AccountScreenIntent()
    data class PatronymicInput(val patronymic: String) : AccountScreenIntent()
    data class PhotoInput(val photo: String) : AccountScreenIntent()
    data class Logout(val onLogout: () -> Unit) : AccountScreenIntent()
    data object ChangeEditInfoDialogVisible : AccountScreenIntent()
}