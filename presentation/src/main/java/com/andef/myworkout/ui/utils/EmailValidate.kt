package com.andef.myworkout.ui.utils

fun String.isValidEmail(): Boolean {
    val emailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
    return emailRegex.matches(this)
}