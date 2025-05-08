package com.andef.myworkout.design.snackbar.state

sealed class UiSnackBarState {
    data object Error : UiSnackBarState()
    data object Usual : UiSnackBarState()
}