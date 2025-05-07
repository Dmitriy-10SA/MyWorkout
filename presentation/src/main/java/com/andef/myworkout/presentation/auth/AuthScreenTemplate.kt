package com.andef.myworkout.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myworkout.R
import com.andef.myworkout.ui.theme.Black
import com.andef.myworkout.ui.theme.MyWorkoutTheme
import com.andef.myworkout.ui.theme.White

@Composable
fun AuthScreenTemplate(paddingValues: PaddingValues, mainContent: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF111112),
                        Color(0xFF414148)
                    )
                )
            )
            .padding(paddingValues)
    ) {
        Spacer(modifier = Modifier.padding(14.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            text = stringResource(R.string.my_workout),
            textAlign = TextAlign.Center,
            color = White,
            fontFamily = FontFamily(Font(R.font.michroma_regular)),
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.padding(18.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            colors = CardDefaults.cardColors(containerColor = White, contentColor = Black),
            shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp)
        ) {
            mainContent()
        }
    }
}

@Preview
@Composable
private fun AuthScreenTemplatePreview() {
    MyWorkoutTheme {
        AuthScreenTemplate(paddingValues = PaddingValues()) { }
    }
}