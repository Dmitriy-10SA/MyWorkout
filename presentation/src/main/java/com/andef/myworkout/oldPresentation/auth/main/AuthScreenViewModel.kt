package com.andef.myworkout.oldPresentation.auth.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myworkout.R
import com.andef.myworkout.data.ApiException
import com.andef.myworkout.domain.auth.entities.LoginRequest
import com.andef.myworkout.domain.auth.entities.PasswordChangeRequest
import com.andef.myworkout.domain.auth.entities.RegisterRequest
import com.andef.myworkout.domain.auth.usecases.ChangePasswordUseCase
import com.andef.myworkout.domain.auth.usecases.CheckTokenUseCase
import com.andef.myworkout.domain.auth.usecases.GetTokenUseCase
import com.andef.myworkout.domain.auth.usecases.LoginUseCase
import com.andef.myworkout.domain.auth.usecases.RegisterUseCase
import com.andef.myworkout.domain.auth.usecases.SaveTokenUseCase
import com.andef.myworkout.ui.utils.isValidEmail
import com.andef.myworkout.ui.utils.isValidPassword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthScreenViewModel @Inject constructor(
    private val checkTokenUseCase: CheckTokenUseCase,
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: RegisterUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(AuthScreenState())
    val state: StateFlow<AuthScreenState> = _state

    fun send(intent: AuthScreenIntent) {
        when (intent) {
            AuthScreenIntent.LoginChoose -> loginChoose(isLogin = true)
            AuthScreenIntent.SignUpChoose -> loginChoose(isLogin = false)
            is AuthScreenIntent.EmailInput -> changeInput(email = intent.email)
            is AuthScreenIntent.PasswordInput -> changeInput(password = intent.password)
            is AuthScreenIntent.SurnameInput -> changeInput(surname = intent.surname)
            is AuthScreenIntent.NameInput -> changeInput(name = intent.name)
            is AuthScreenIntent.PatronymicInput -> changeInput(patronymic = intent.patronymic)
            AuthScreenIntent.PasswordVisibleChange -> passwordVisibleChange()
            AuthScreenIntent.ShowForgotPasswordVisibleChange -> forgotPasswordVisibleChange()
            is AuthScreenIntent.Login -> login(
                onSuccess = intent.onSuccess,
                onError = intent.onError
            )

            is AuthScreenIntent.SignUp -> signUp(
                onSuccess = intent.onSuccess,
                onError = intent.onError
            )

            is AuthScreenIntent.PasswordChange -> passwordChange(
                onSuccess = intent.onSuccess,
                onError = intent.onError
            )

            is AuthScreenIntent.CheckToken -> checkToken(onSuccess = intent.onSuccess)
        }
    }

    private fun checkToken(onSuccess: () -> Unit) {
        networkRequest(
            request = {
                error("")
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
        networkRequest(
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
        networkRequest(
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

    private fun passwordChange(onSuccess: () -> Unit, onError: () -> Unit) {
        networkRequest(
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

    private fun networkRequest(
        request: suspend () -> Unit,
        onError: () -> Unit = {},
        unauthorizedTextResId: Int = R.string.unknown_error,
        serverErrorTextResId: Int = R.string.unknown_error,
        isCheckToken: Boolean = false
    ) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(
                    isLoading = true,
                    isError = false,
                    errorMsgResId = null
                )
                if (isCheckToken) {
                    _state.value = _state.value.copy(isCheckTokenLoading = true)
                }
                request()
            } catch (e: ApiException) {
                val errorMsgResId = when (e) {
                    ApiException.Unauthorized -> unauthorizedTextResId
                    ApiException.ServerError -> serverErrorTextResId
                    else -> R.string.unknown_network_error
                }
                if (isCheckToken) {
                    _state.value = _state.value.copy(isCheckTokenLoading = false)
                }
                _state.value = _state.value.copy(isError = true, errorMsgResId = errorMsgResId)
                onError()
            } catch (_: Exception) {
                _state.value = _state.value.copy(
                    isError = true,
                    errorMsgResId = R.string.unknown_network_error
                )
                if (isCheckToken) {
                    _state.value = _state.value.copy(isCheckTokenLoading = false)
                }
                onError()
            } finally {
                _state.value = _state.value.copy(isLoading = false)
                if (isCheckToken) {
                    _state.value = _state.value.copy(isCheckTokenLoading = false)
                }
            }
        }
    }

    private fun changeInput(
        email: String = _state.value.email,
        password: String = _state.value.password,
        surname: String = _state.value.surname,
        name: String = _state.value.name,
        patronymic: String = _state.value.patronymic
    ) {
        val isValidEmailAndPassword = email.isValidEmail() && password.isValidPassword()
        val isValidRegisterRequestInfo = isValidEmailAndPassword && surname.isNotEmpty() &&
                name.isNotEmpty() && patronymic.isNotEmpty() && surname.length < 45 &&
                name.length < 40 && patronymic.length < 45

        _state.value = _state.value.copy(
            email = email,
            password = password,
            surname = surname,
            name = name,
            patronymic = patronymic,
            isValidEmailAndPassword = isValidEmailAndPassword,
            isValidSignUpInfo = isValidRegisterRequestInfo
        )
    }

    private fun forgotPasswordVisibleChange() {
        _state.value = _state.value.copy(
            isShowForgotPassword = !_state.value.isShowForgotPassword
        )
    }

    private fun passwordVisibleChange() {
        _state.value = _state.value.copy(
            isPasswordVisible = !_state.value.isPasswordVisible
        )
    }

    private fun loginChoose(isLogin: Boolean) {
        _state.value = _state.value.copy(isLogin = isLogin)
    }
}