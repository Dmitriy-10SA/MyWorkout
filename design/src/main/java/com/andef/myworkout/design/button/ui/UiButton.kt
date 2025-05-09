package com.andef.myworkout.design.button.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myworkout.design.Black
import com.andef.myworkout.design.Blue
import com.andef.myworkout.design.White
import com.andef.myworkout.design.button.state.UiButtonState

@Composable
fun UiButton(state: UiButtonState, text: String, onClick: () -> Unit) {
    when (state) {
        is UiButtonState.Base -> {
            BaseButton(
                modifier = state.modifier,
                textModifier = state.textModifier,
                enabled = state.enabled,
                text = text,
                onClick = onClick
            )
        }

        UiButtonState.ForgotPassword -> {
            ForgotPasswordButton(text = text, onClick = onClick)
        }

        is UiButtonState.Chooser -> {
            ChooserButton(
                modifier = state.modifier,
                textModifier = state.textModifier,
                text = text,
                onClick = onClick
            )
        }
    }
}

@Composable
private fun ForgotPasswordButton(text: String, onClick: () -> Unit) {
    Row {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .clickable { onClick() },
            color = Blue.copy(alpha = 0.8f),
            text = text
        )
    }
}

@Composable
private fun ChooserButton(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton (
        modifier = modifier,
        onClick = onClick,
        border = BorderStroke(width = 1.dp, color = Black),
        colors = ButtonDefaults.buttonColors(containerColor = White, contentColor = Black),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(modifier = textModifier, text = text, color = Black, fontSize = 16.sp)
    }
}

@Composable
private fun BaseButton(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue,
            contentColor = White,
            disabledContentColor = White,
            disabledContainerColor = Blue.copy(alpha = 0.35f)
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(modifier = textModifier, text = text, color = White, fontSize = 16.sp)
    }
}