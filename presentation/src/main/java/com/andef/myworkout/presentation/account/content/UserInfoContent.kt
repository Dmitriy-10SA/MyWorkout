package com.andef.myworkout.presentation.account.content

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andef.myworkout.R
import com.andef.myworkout.domain.account.entities.UserInfo
import com.andef.myworkout.presentation.account.main.AccountScreenIntent
import com.andef.myworkout.presentation.account.main.AccountScreenViewModel
import com.andef.myworkout.ui.theme.Black
import com.andef.myworkout.ui.theme.Gray

@Composable
fun UserInfoContent(
    paddingValues: PaddingValues,
    userInfo: UserInfo,
    viewModel: AccountScreenViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Spacer(modifier = Modifier.padding(8.dp))
            if (userInfo.photo != null && !userInfo.photo.isNullOrEmpty()) {
                userInfo.photo?.let {
                    val bitmap = base64ToBitmap(base64 = it)
                    if (bitmap.allocationByteCount < 10 * 1024 * 1024) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                                .background(Black),
                            contentDescription = stringResource(R.string.account_photo)
                        )
                    } else {
                        viewModel.send(AccountScreenIntent.PhotoInput(photo = ""))
                        AccountSamplePhoto()
                    }
                }
            } else {
                AccountSamplePhoto()
            }
        }
        item {
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                text = getFullUserName(userInfo),
                color = Black,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Spacer(modifier = Modifier.padding(1.dp))
            Text(text = userInfo.mail, color = Black)
        }
    }
}

@Composable
private fun AccountSamplePhoto() {
    Image(
        painter = painterResource(R.drawable.account),
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape),
        colorFilter = ColorFilter.tint(color = Gray),
        contentDescription = stringResource(R.string.account_photo)
    )
}

private fun base64ToBitmap(base64: String): Bitmap {
    val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}

private fun getFullUserName(userInfo: UserInfo): String {
    return "${userInfo.surname} ${userInfo.name} ${userInfo.patronymic}"
}