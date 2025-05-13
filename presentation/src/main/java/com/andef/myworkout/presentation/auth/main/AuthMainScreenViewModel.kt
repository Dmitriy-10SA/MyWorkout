package com.andef.myworkout.presentation.auth.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myworkout.R
import com.andef.myworkout.Requester
import com.andef.myworkout.domain.auth.entities.LoginRequest
import com.andef.myworkout.domain.auth.entities.RegisterRequest
import com.andef.myworkout.domain.auth.usecases.CheckTokenUseCase
import com.andef.myworkout.domain.auth.usecases.GetTokenUseCase
import com.andef.myworkout.domain.auth.usecases.LoginUseCase
import com.andef.myworkout.domain.auth.usecases.RegisterUseCase
import com.andef.myworkout.domain.auth.usecases.SaveTokenUseCase
import com.andef.myworkout.ui.utils.isValidEmail
import com.andef.myworkout.ui.utils.isValidPassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AuthMainScreenViewModel @Inject constructor(
    private val requester: Requester,
    private val checkTokenUseCase: CheckTokenUseCase,
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: RegisterUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AuthMainScreenState())
    val state: StateFlow<AuthMainScreenState> = _state

    fun send(intent: AuthMainScreenIntent) {
        when (intent) {
            is AuthMainScreenIntent.CheckToken -> checkToken(onSuccess = intent.onSuccess)
            is AuthMainScreenIntent.EmailInput -> changeState(email = intent.email)
            is AuthMainScreenIntent.Login -> login(
                onSuccess = intent.onSuccess,
                onError = intent.onError
            )

            AuthMainScreenIntent.LoginChoose -> changeState(isLogin = true)
            is AuthMainScreenIntent.NameInput -> changeState(name = intent.name)
            is AuthMainScreenIntent.PasswordInput -> changeState(password = intent.password)
            AuthMainScreenIntent.PasswordVisibleChange -> changeState(
                isPasswordVisible = !_state.value.isPasswordVisible
            )

            is AuthMainScreenIntent.PatronymicInput -> changeState(patronymic = intent.patronymic)
            is AuthMainScreenIntent.SignUp -> signUp(
                onSuccess = intent.onSuccess,
                onError = intent.onError
            )

            AuthMainScreenIntent.SignUpChoose -> changeState(isLogin = false)
            is AuthMainScreenIntent.SurnameInput -> changeState(surname = intent.surname)
        }
    }

    private fun checkToken(onSuccess: () -> Unit) {
        requester.networkRequest(
            viewModelScope = viewModelScope,
            state = _state,
            request = {
                getTokenUseCase.invoke()?.let { token ->
                    checkTokenUseCase.invoke(token)
                    _state.value = _state.value.copy(isSwitchToMainScreen = true)
                    onSuccess()
                }
            },
            isCheckToken = true
        )
    }

    private fun login(onSuccess: () -> Unit, onError: () -> Unit) {
        requester.networkRequest(
            viewModelScope = viewModelScope,
            state = _state,
            request = {
                val authResponse = loginUseCase.invoke(
                    LoginRequest(mail = _state.value.email, password = _state.value.password)
                )
                saveTokenUseCase.invoke(authResponse.token)
                _state.value = _state.value.copy(isSwitchToMainScreen = true)
                onSuccess()
            },
            onError = onError,
            unauthorizedTextResId = R.string.unauthorized_login_error,
            serverErrorTextResId = R.string.server_error
        )
    }

    private fun signUp(onSuccess: () -> Unit, onError: () -> Unit) {
        requester.networkRequest(
            viewModelScope = viewModelScope,
            state = _state,
            request = {
                val authResponse = signUpUseCase.invoke(
                    RegisterRequest(
                        mail = _state.value.email,
                        password = _state.value.password,
                        surname = _state.value.surname,
                        name = _state.value.name,
                        patronymic = _state.value.patronymic
                    )
                )
                saveTokenUseCase.invoke(authResponse.token)
                _state.value = _state.value.copy(isSwitchToMainScreen = true)
                onSuccess()
            },
            onError = onError,
            unauthorizedTextResId = R.string.unauthorized_sign_up_error,
            serverErrorTextResId = R.string.server_error
        )
    }

    private fun changeState(
        email: String = _state.value.email,
        password: String = _state.value.password,
        surname: String = _state.value.surname,
        name: String = _state.value.name,
        patronymic: String = _state.value.patronymic,
        isLogin: Boolean = _state.value.isLogin,
        isPasswordVisible: Boolean = _state.value.isPasswordVisible
    ) {
        val isValidEmailAndPassword = email.isValidEmail() && password.isValidPassword()
        val isValidSignUpInfo = isValidEmailAndPassword && surname.isNotEmpty() &&
                name.isNotEmpty() && patronymic.isNotEmpty() && surname.length < 45 &&
                name.length < 40 && patronymic.length < 45

        _state.value = _state.value.copy(
            email = email,
            password = password,
            surname = surname,
            name = name,
            patronymic = patronymic,
            isLogin = isLogin,
            isPasswordVisible = isPasswordVisible,
            isValidEmailAndPassword = isValidEmailAndPassword,
            isValidSignUpInfo = isValidSignUpInfo
        )
    }
}