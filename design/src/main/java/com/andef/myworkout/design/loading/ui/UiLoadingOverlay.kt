package com.andef.myworkout.design.loading.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myworkout.design.Gray
import com.andef.myworkout.design.R
import com.andef.myworkout.design.White

@Composable
fun UiLoadingOverlay(text: String? = null, paddingValues: PaddingValues) {
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
            text?.let {
                WithText(it)
            } ?: WithoutText()
        }
    }
}

@Composable
private fun WithoutText() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = White, trackColor = Gray)
    }
}

@Composable
private fun WithText(text: String) {
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
                    text = text,
                    textAlign = TextAlign.Center,
                    color = White,
                    fontFamily = FontFamily(Font(R.font.michroma_regular)),
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.padding(10.dp))
                CircularProgressIndicator(color = White, trackColor = Gray)
            }
        }
    }
}