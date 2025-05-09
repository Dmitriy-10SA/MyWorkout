package com.andef.myworkout.design.input.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.andef.myworkout.design.Black
import com.andef.myworkout.design.DarkGray
import com.andef.myworkout.design.Gray
import com.andef.myworkout.design.White
import com.andef.myworkout.design.input.state.UiInputState

@Composable
fun UiInput(
    modifier: Modifier = Modifier,
    state: UiInputState,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String,
    upText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    when (state) {
        UiInputState.Base -> {
            BaseInput(
                modifier = modifier,
                value = value,
                onValueChange = onValueChange,
                visualTransformation = visualTransformation,
                trailingIcon = trailingIcon,
                placeholderText = placeholderText,
                upText = upText,
                keyboardOptions = keyboardOptions
            )
        }
    }
}

@Composable
private fun BaseInput(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeholderText: String,
    upText: String,
    keyboardOptions: KeyboardOptions
) {
    Column {
        Text(text = upText, color = DarkGray)
        OutlinedTextField(
            keyboardOptions = keyboardOptions,
            modifier = modifier,
            shape = RoundedCornerShape(10.dp),
            value = value,
            visualTransformation = visualTransformation,
            singleLine = true,
            placeholder = { Text(text = placeholderText) },
            trailingIcon = trailingIcon,
            colors = textFieldColors(),
            onValueChange = onValueChange
        )
    }
}

@Composable
fun textFieldColors() = TextFieldDefaults.colors(
    focusedTextColor = Black,
    disabledTextColor = Black,
    unfocusedTextColor = Black,
    focusedSupportingTextColor = Black,
    disabledSupportingTextColor = Black,
    unfocusedSupportingTextColor = Black,
    focusedContainerColor = White,
    disabledContainerColor = White,
    unfocusedContainerColor = White,
    errorContainerColor = White,
    focusedIndicatorColor = Black,
    disabledIndicatorColor = Black,
    unfocusedIndicatorColor = Black,
    disabledLeadingIconColor = Black,
    focusedTrailingIconColor = Black,
    focusedLeadingIconColor = Black,
    disabledTrailingIconColor = Black,
    unfocusedLeadingIconColor = Black,
    unfocusedTrailingIconColor = Black,
    cursorColor = Black,
    disabledPlaceholderColor = Black,
    focusedPlaceholderColor = Black,
    unfocusedPlaceholderColor = Black,
    unfocusedLabelColor = Black,
    focusedLabelColor = Black,
    disabledLabelColor = Black
)