package com.andef.myworkout.presentation.account.changeinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myworkout.Requester
import com.andef.myworkout.data.ApiException
import com.andef.myworkout.domain.account.entities.ChangeUserInfoRequest
import com.andef.myworkout.domain.account.usecases.ChangeUserInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AccountChangeInfoScreenViewModel @Inject constructor(
    private val requester: Requester,
    private val changeUserInfoUseCase: ChangeUserInfoUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AccountChangeInfoScreenState())
    val state: StateFlow<AccountChangeInfoScreenState> = _state

    fun send(intent: AccountChangeInfoScreenIntent) {
        when (intent) {
            is AccountChangeInfoScreenIntent.ChangeUserInfo -> changeUserInfo(
                onSuccess = intent.onSuccess,
                onUnauthorized = intent.onUnauthorized
            )

            is AccountChangeInfoScreenIntent.NameInput -> changeInput(name = intent.name.trim())
            is AccountChangeInfoScreenIntent.PatronymicInput -> changeInput(
                patronymic = intent.patronymic.trim()
            )

            is AccountChangeInfoScreenIntent.PhotoInput -> changeInput(photo = intent.photo)
            is AccountChangeInfoScreenIntent.SurnameInput -> changeInput(
                surname = intent.surname.trim()
            )
        }
    }

    private fun changeUserInfo(onSuccess: () -> Unit, onUnauthorized: () -> Unit) {
        requester.networkRequest(
            viewModelScope = viewModelScope,
            beforeRequest = {
                _state.value = _state.value.copy(isLoading = true, isError = false)
            },
            onError = {
                _state.value = _state.value.copy(isError = true)
            },
            onFinally = {
                _state.value = _state.value.copy(isLoading = false)
            },
            request = { token ->
                changeUserInfoUseCase.invoke(
                    token = token,
                    changeUserInfoRequest = ChangeUserInfoRequest(
                        surname = _state.value.surname,
                        name = _state.value.name,
                        patronymic = _state.value.patronymic,
                        photo = _state.value.photo
                    )
                )
                onSuccess()
            },
            onUnauthorized = onUnauthorized
        )
    }

    private fun changeInput(
        surname: String = _state.value.surname,
        name: String = _state.value.name,
        patronymic: String = _state.value.patronymic,
        photo: String? = _state.value.photo
    ) {
        _state.value = _state.value.copy(
            surname = surname,
            name = name,
            patronymic = patronymic,
            photo = photo,
            isValidInfoForChange = checkValidInfoForChange(surname, name, patronymic)
        )
    }

    private fun checkValidInfoForChange(
        surname: String,
        name: String,
        patronymic: String
    ): Boolean {
        return surname.isNotEmpty() && name.isNotEmpty() && patronymic.isNotEmpty() &&
                surname.length < 45 && name.length < 40 && patronymic.length < 45
    }
}