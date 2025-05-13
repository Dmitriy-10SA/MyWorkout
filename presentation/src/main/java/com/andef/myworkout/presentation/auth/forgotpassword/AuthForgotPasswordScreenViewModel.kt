package com.andef.myworkout.presentation.auth.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myworkout.R
import com.andef.myworkout.Requester
import com.andef.myworkout.domain.auth.entities.PasswordChangeRequest
import com.andef.myworkout.domain.auth.usecases.ChangePasswordUseCase
import com.andef.myworkout.domain.auth.usecases.SaveTokenUseCase
import com.andef.myworkout.ui.utils.isValidEmail
import com.andef.myworkout.ui.utils.isValidPassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AuthForgotPasswordScreenViewModel @Inject constructor(
    private val requester: Requester,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AuthForgotPasswordScreenState())
    val state: StateFlow<AuthForgotPasswordScreenState> = _state

    fun send(intent: AuthForgotPasswordScreenIntent) {
        when (intent) {
            is AuthForgotPasswordScreenIntent.ChangePassword -> changePassword(
                onSuccess = intent.onSuccess,
                onError = intent.onError
            )

            is AuthForgotPasswordScreenIntent.EmailInput -> changeState(email = intent.email)
            is AuthForgotPasswordScreenIntent.PasswordInput -> changeState(
                password = intent.password
            )

            AuthForgotPasswordScreenIntent.PasswordVisibleChange -> changeState(
                isPasswordVisible = !_state.value.isPasswordVisible
            )
        }
    }

    private fun changePassword(onSuccess: () -> Unit, onError: () -> Unit) {
        requester.networkRequest(
            viewModelScope = viewModelScope,
            state = _state,
            request = {
                val authResponse = changePasswordUseCase.invoke(
                    PasswordChangeRequest(
                        mail = _state.value.email,
                        password = _state.value.password
                    )
                )
                saveTokenUseCase.invoke(authResponse.token)
                _state.value = _state.value.copy(isSwitchToMainScreen = true)
                onSuccess()
            },
            onError = onError,
            unauthorizedTextResId = R.string.unauthorized_change_password_error,
            serverErrorTextResId = R.string.server_error
        )
    }

    private fun changeState(
        email: String = _state.value.email,
        password: String = _state.value.password,
        isPasswordVisible: Boolean = _state.value.isPasswordVisible
    ) {
        val isValidEmailAndPassword = email.isValidEmail() && password.isValidPassword()

        _state.value = _state.value.copy(
            email = email,
            password = password,
            isPasswordVisible = isPasswordVisible,
            isValidEmailAndPassword = isValidEmailAndPassword
        )
    }
}