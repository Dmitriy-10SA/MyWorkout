package com.andef.myworkout.presentation.account.main.content

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.domain.account.entities.UserInfo
import com.andef.myworkout.presentation.account.main.AccountMainScreenIntent
import com.andef.myworkout.presentation.account.main.AccountMainScreenViewModel
import com.andef.myworkout.ui.theme.Black
import com.andef.myworkout.ui.theme.Gray
import com.andef.myworkout.ui.utils.onUnauthorizedNavigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun UserInfoContent(
    paddingValues: PaddingValues,
    userInfo: UserInfo,
    viewModel: AccountMainScreenViewModel,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    navHostController: NavHostController
) {
    val context = LocalContext.current

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
                    if (bitmap.allocationByteCount < 15 * 1024 * 1024) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            modifier = Modifier
                                .size(150.dp)
                                .clip(CircleShape)
                                .background(Black),
                            contentDescription = stringResource(R.string.account_photo)
                        )
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.error_account_photo_load),
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.send(AccountMainScreenIntent.PhotoInput(photo = ""))
                        viewModel.send(AccountMainScreenIntent.ChangeUserInfo(onUnauthorized = {
                            scope.launch {
                                snackBarHostState.currentSnackbarData?.dismiss()
                                snackBarHostState.showSnackbar(
                                    message = context.getString(R.string.unauthorized),
                                    withDismissAction = true
                                )
                                onUnauthorizedNavigate(navHostController = navHostController)
                            }
                        }))
                    }
                }
            } else {
                AccountSamplePhoto()
            }
        }
        item {
            Spacer(modifier = Modifier.padding(5.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 3.dp),
                textAlign = TextAlign.Center,
                text = getFullUserName(userInfo),
                color = Black,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 3.dp),
                textAlign = TextAlign.Center,
                text = userInfo.mail,
                color = Black
            )
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