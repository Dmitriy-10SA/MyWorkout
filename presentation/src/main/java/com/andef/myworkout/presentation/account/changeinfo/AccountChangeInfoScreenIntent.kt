package com.andef.myworkout.presentation.account.changeinfo

sealed class AccountChangeInfoScreenIntent {
    data class SurnameInput(val surname: String) : AccountChangeInfoScreenIntent()
    data class PatronymicInput(val patronymic: String) : AccountChangeInfoScreenIntent()
    data class NameInput(val name: String) : AccountChangeInfoScreenIntent()
    data class PhotoInput(val photo: String) : AccountChangeInfoScreenIntent()
    data class ChangeUserInfo(
        val onSuccess: () -> Unit,
        val onUnauthorized: () -> Unit
    ) : AccountChangeInfoScreenIntent()
}