package com.andef.myworkout.oldPresentation.account.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myworkout.data.ApiException
import com.andef.myworkout.domain.account.entities.ChangeUserInfoRequest
import com.andef.myworkout.domain.account.usecases.ChangeUserInfoUseCase
import com.andef.myworkout.domain.account.usecases.GetUserInfoUseCase
import com.andef.myworkout.domain.auth.usecases.ClearTokenUseCase
import com.andef.myworkout.domain.auth.usecases.GetTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountScreenViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val changeUserInfoUseCase: ChangeUserInfoUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val clearTokenUseCase: ClearTokenUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AccountScreenState())
    val state: StateFlow<AccountScreenState> = _state

    fun send(intent: AccountScreenIntent) {
        when (intent) {
            is AccountScreenIntent.ChangeUserInfo -> {
                changeUserInfo(
                    onUnauthorized = intent.onUnauthorized
                )
            }

            is AccountScreenIntent.LoadUserInfo -> {
                loadUserInfo(
                    onUnauthorized = intent.onUnauthorized
                )
            }

            is AccountScreenIntent.NameInput -> {
                changeInput(
                    name = intent.name.trim()
                )
            }

            is AccountScreenIntent.PatronymicInput -> {
                changeInput(
                    patronymic = intent.patronymic.trim()
                )
            }

            is AccountScreenIntent.PhotoInput -> {
                changeInput(
                    photo = intent.photo
                )
            }

            is AccountScreenIntent.SurnameInput -> {
                changeInput(
                    surname = intent.surname.trim()
                )
            }

            is AccountScreenIntent.Logout -> {
                logout(onLogout = intent.onLogout)
            }

            AccountScreenIntent.ChangeEditInfoDialogVisible -> {
                editInfoDialogVisibleChange()
            }
        }
    }

    private fun editInfoDialogVisibleChange() {
        _state.value = _state.value.copy(showEditInfoDialog = !_state.value.showEditInfoDialog)
    }

    private fun logout(onLogout: () -> Unit) {
        clearTokenUseCase.invoke()
        onLogout()
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

    private fun changeUserInfo(onUnauthorized: () -> Unit) {
        networkRequest(
            request = { token ->
                changeUserInfoUseCase.invoke(
                    token,
                    ChangeUserInfoRequest(
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
        networkRequest(
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

    private fun networkRequest(request: suspend (String) -> Unit, onUnauthorized: () -> Unit) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, isError = false)
                getTokenUseCase.invoke()?.let { token ->
                    request(token)
                } ?: {
                    clearTokenUseCase.invoke()
                    throw ApiException.Unauthorized
                }
            } catch (e: ApiException) {
                when (e) {
                    ApiException.Unauthorized -> {
                        clearTokenUseCase.invoke()
                        onUnauthorized()
                    }

                    else -> _state.value = _state.value.copy(isError = true)
                }
            } catch (_: Exception) {
                _state.value = _state.value.copy(isError = true)
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}