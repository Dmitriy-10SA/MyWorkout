package com.andef.myworkout

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.andef.myworkout.data.ApiException
import com.andef.myworkout.domain.auth.usecases.ClearTokenUseCase
import com.andef.myworkout.domain.auth.usecases.GetTokenUseCase
import com.andef.myworkout.presentation.auth.main.AuthMainScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class Requester @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase,
    private val clearTokenUseCase: ClearTokenUseCase
) {
    fun networkRequest(
        viewModelScope: CoroutineScope,
        beforeRequest: () -> Unit,
        request: suspend (String) -> Unit,
        onUnauthorized: () -> Unit,
        onError: () -> Unit,
        onFinally: () -> Unit
    ): Job {
        return viewModelScope.launch {
            try {
                beforeRequest()
                getTokenUseCase.invoke()?.let { token -> request(token) }
            } catch (e: ApiException) {
                when (e) {
                    ApiException.Unauthorized -> {
                        clearTokenUseCase.invoke()
                        onUnauthorized()
                    }

                    else -> {
                        onError()
                    }
                }
            } catch (_: Exception) {
                onError()
            } finally {
                onFinally()
            }
        }
    }

    fun networkRequest(
        viewModelScope: CoroutineScope,
        state: MutableStateFlow<AuthMainScreenState>,
        request: suspend () -> Unit,
        onError: () -> Unit = {},
        unauthorizedTextResId: Int = R.string.unknown_error,
        serverErrorTextResId: Int = R.string.unknown_error,
        isCheckToken: Boolean = false
    ) {
        viewModelScope.launch {
            try {
                state.value = state.value.copy(
                    isLoading = true,
                    isError = false,
                    errorMsgResId = null
                )
                if (isCheckToken) {
                    state.value = state.value.copy(isCheckTokenLoading = true)
                }
                request()
            } catch (e: ApiException) {
                val errorMsgResId = when (e) {
                    ApiException.Unauthorized -> unauthorizedTextResId
                    ApiException.ServerError -> serverErrorTextResId
                    else -> R.string.unknown_network_error
                }
                if (isCheckToken) {
                    state.value = state.value.copy(isCheckTokenLoading = false)
                }
                state.value = state.value.copy(isError = true, errorMsgResId = errorMsgResId)
                onError()
            } catch (_: Exception) {
                state.value = state.value.copy(
                    isError = true,
                    errorMsgResId = R.string.unknown_network_error
                )
                if (isCheckToken) {
                    state.value = state.value.copy(isCheckTokenLoading = false)
                }
                onError()
            } finally {
                state.value = state.value.copy(isLoading = false)
                if (isCheckToken) {
                    state.value = state.value.copy(isCheckTokenLoading = false)
                }
            }
        }
    }
}