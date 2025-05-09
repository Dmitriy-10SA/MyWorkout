package com.andef.myworkout.presentation.account.content

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
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
import com.andef.myworkout.ui.theme.Black
import com.andef.myworkout.ui.theme.Gray
import java.io.ByteArrayOutputStream

@Composable
fun UserInfoContent(paddingValues: PaddingValues, userInfo: UserInfo) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Spacer(modifier = Modifier.padding(8.dp))
            userInfo.photo?.let {
                Image(
                    bitmap = base64ToBitmap(it).asImageBitmap(),
                    modifier = Modifier.size(130.dp).clip(CircleShape),
                    contentDescription = stringResource(R.string.account_photo)
                )
            } ?: Image(
                painter = painterResource(R.drawable.account),
                modifier = Modifier.size(130.dp).clip(CircleShape),
                colorFilter = ColorFilter.tint(color = Gray),
                contentDescription = stringResource(R.string.account_photo)
            )
        }
        item {
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

private fun bitmapToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

private fun base64ToBitmap(base64: String): Bitmap {
    val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}

private fun getFullUserName(userInfo: UserInfo): String {
    return "${userInfo.surname} ${userInfo.name} ${userInfo.patronymic}"
}