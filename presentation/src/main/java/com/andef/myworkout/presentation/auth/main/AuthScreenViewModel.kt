package com.andef.myworkout.presentation.auth.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.myworkout.ui.utils.isValidEmail
import com.andef.myworkout.ui.utils.isValidPassword
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthScreenViewModel @Inject constructor() : ViewModel() {
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
            AuthScreenIntent.Login -> login()
            AuthScreenIntent.SignUp -> signUp()
            AuthScreenIntent.PasswordChange -> passwordChange()
        }
    }

    private fun login() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            delay(10000)
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            delay(2000)
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    private fun passwordChange() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            delay(2000)
            _state.value = _state.value.copy(isLoading = false)
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
                name.isNotEmpty() && patronymic.isNotEmpty()

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