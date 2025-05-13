package com.andef.myworkout.presentation.account.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myworkout.Requester
import com.andef.myworkout.domain.account.entities.ChangeUserInfoRequest
import com.andef.myworkout.domain.account.usecases.ChangeUserInfoUseCase
import com.andef.myworkout.domain.account.usecases.GetUserInfoUseCase
import com.andef.myworkout.domain.auth.usecases.ClearTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AccountMainScreenViewModel @Inject constructor(
    private val requester: Requester,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val changeUserInfoUseCase: ChangeUserInfoUseCase,
    private val clearTokenUseCase: ClearTokenUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AccountMainScreenState())
    val state: StateFlow<AccountMainScreenState> = _state

    fun send(intent: AccountMainScreenIntent) {
        when (intent) {
            is AccountMainScreenIntent.LoadUserInfo -> {
                loadUserInfo(
                    onUnauthorized = intent.onUnauthorized
                )
            }

            is AccountMainScreenIntent.Logout -> {
                logout(onLogout = intent.onLogout)
            }

            is AccountMainScreenIntent.ChangeUserInfo -> {
                changeUserInfo(
                    onUnauthorized = intent.onUnauthorized
                )
            }

            is AccountMainScreenIntent.PhotoInput -> photoInput(photo = intent.photo)
        }
    }

    private fun photoInput(photo: String) {
        _state.value = _state.value.copy(photo = photo)
    }

    private fun changeUserInfo(onUnauthorized: () -> Unit) {
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
                loadUserInfo(onUnauthorized = onUnauthorized)
            },
            onUnauthorized = onUnauthorized
        )
    }

    private fun loadUserInfo(onUnauthorized: () -> Unit) {
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
                val userInfo = getUserInfoUseCase.invoke(token)
                _state.value = _state.value.copy(
                    userInfo = userInfo,
                    surname = userInfo.surname,
                    name = userInfo.name,
                    patronymic = userInfo.patronymic,
                    photo = userInfo.photo,
                    isValidInfoForChange = checkValidInfoForChange(
                        userInfo.surname,
                        userInfo.name,
                        userInfo.patronymic
                    )
                )
            },
            onUnauthorized = onUnauthorized
        )
    }

    private fun logout(onLogout: () -> Unit) {
        clearTokenUseCase.invoke()
        onLogout()
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