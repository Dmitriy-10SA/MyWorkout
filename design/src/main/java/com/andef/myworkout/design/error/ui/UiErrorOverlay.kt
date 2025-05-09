package com.andef.myworkout.design.error.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myworkout.design.R
import com.andef.myworkout.design.White
import com.andef.myworkout.design.button.state.UiButtonState
import com.andef.myworkout.design.button.ui.UiButton

@Composable
fun UiErrorOverlay(paddingValues: PaddingValues, onRetry: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF111112), Color(0xFF414148))
                        )
                    )
            ) {
                Box(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.ui_error_overlay_my_workout),
                            textAlign = TextAlign.Center,
                            color = White,
                            fontFamily = FontFamily(Font(R.font.michroma_regular)),
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.padding(6.dp))
                        Text(
                            text = stringResource(R.string.ui_error_overlay_text),
                            textAlign = TextAlign.Center,
                            color = White,
                            fontSize = 22.sp
                        )
                        Spacer(modifier = Modifier.padding(6.dp))
                        UiButton(
                            state = UiButtonState.Base(
                                textModifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                            ),
                            text = stringResource(R.string.ui_error_overlay_retry_button_text),
                            onClick = onRetry
                        )
                    }
                }
            }
        }
    }
}