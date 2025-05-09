package com.andef.myworkout.presentation.account.content

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.andef.myworkout.domain.account.entities.UserInfo

@Composable
fun UserInfoContent(paddingValues: PaddingValues, userInfo: UserInfo) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Text(userInfo.toString())
    }
}