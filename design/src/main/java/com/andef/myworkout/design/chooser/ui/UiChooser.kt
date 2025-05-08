package com.andef.myworkout.design.chooser.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.andef.myworkout.design.Black
import com.andef.myworkout.design.DarkWhite
import com.andef.myworkout.design.White

@Composable
fun UiChooser(
    modifier: Modifier = Modifier,
    enabledLeft: Boolean,
    enabledRight: Boolean,
    textLeft: String,
    textRight: String,
    onClickLeft: () -> Unit,
    onClickRight: () -> Unit
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = DarkWhite, contentColor = Black),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row {
            ChooserButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 2.dp, start = 4.dp),
                enabled = enabledLeft,
                text = textLeft,
                onClick = onClickLeft
            )
            ChooserButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp, start = 2.dp),
                enabled = enabledRight,
                text = textRight,
                onClick = onClickRight
            )
        }
    }
}

@Composable
private fun ChooserButton(
    modifier: Modifier,
    enabled: Boolean,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        onClick = onClick,
        colors = uiChooserColors(),
        enabled = enabled
    ) {
        Text(text = text)
    }
}

@Composable
private fun uiChooserColors() = ButtonDefaults.buttonColors(
    contentColor = Black.copy(alpha = 0.35f),
    containerColor = Color.Transparent,
    disabledContentColor = Black,
    disabledContainerColor = White
)