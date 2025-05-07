package com.andef.myworkout.presentation.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.andef.myworkout.R
import com.andef.myworkout.presentation.auth.login.AuthScreenLoginContent
import com.andef.myworkout.presentation.auth.signup.AuthScreenSignUpContent
import com.andef.myworkout.ui.theme.Black
import com.andef.myworkout.ui.theme.DarkWhite
import com.andef.myworkout.ui.theme.MyWorkoutTheme
import com.andef.myworkout.ui.theme.White

@Composable
fun AuthScreen(paddingValues: PaddingValues, navHostController: NavHostController) {
    val isLoginScreen = remember { mutableStateOf(true) }

    AuthScreenTemplate(
        paddingValues = paddingValues,
        mainContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LoginOrSignUpChooseCard(isLoginScreen = isLoginScreen)
                if (isLoginScreen.value) {
                    AuthScreenLoginContent(navHostController = navHostController)
                } else {
                    AuthScreenSignUpContent(navHostController = navHostController)
                }
            }
        }
    )
}

@Composable
private fun LoginOrSignUpChooseCard(isLoginScreen: MutableState<Boolean>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = DarkWhite),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            LoginOrSignUpButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 2.dp, start = 4.dp),
                enabled = !isLoginScreen.value,
                textResId = R.string.login,
                onClick = { isLoginScreen.value = true }
            )
            LoginOrSignUpButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp, start = 2.dp),
                enabled = isLoginScreen.value,
                textResId = R.string.sign_up,
                onClick = { isLoginScreen.value = false }
            )
        }
    }
}

@Composable
private fun LoginOrSignUpButton(
    modifier: Modifier,
    enabled: Boolean,
    textResId: Int,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        interactionSource = null,
        onClick = onClick,
        colors = loginOrSignUpButtonColors(),
        enabled = enabled
    ) {
        Text(text = stringResource(textResId))
    }
}

@Composable
private fun loginOrSignUpButtonColors() = ButtonDefaults.buttonColors(
    contentColor = Black.copy(alpha = 0.35f),
    containerColor = Color.Transparent,
    disabledContentColor = Black,
    disabledContainerColor = White
)

@Preview
@Composable
private fun AuthScreenPreview() {
    MyWorkoutTheme {
        AuthScreen(
            paddingValues = PaddingValues(),
            navHostController = rememberNavController()
        )
    }
}