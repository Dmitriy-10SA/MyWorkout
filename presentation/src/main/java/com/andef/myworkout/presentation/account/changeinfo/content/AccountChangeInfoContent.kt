package com.andef.myworkout.presentation.account.changeinfo.content

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.design.button.state.UiButtonState
import com.andef.myworkout.design.button.ui.UiButton
import com.andef.myworkout.design.input.state.UiInputState
import com.andef.myworkout.design.input.ui.UiInput
import com.andef.myworkout.presentation.account.changeinfo.AccountChangeInfoScreenIntent
import com.andef.myworkout.presentation.account.changeinfo.AccountChangeInfoScreenState
import com.andef.myworkout.presentation.account.changeinfo.AccountChangeInfoScreenViewModel
import com.andef.myworkout.ui.theme.Black
import com.andef.myworkout.ui.utils.onUnauthorizedNavigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import kotlin.math.min

@Composable
fun AccountChangeInfoScreenContent(
    modifier: Modifier = Modifier,
    viewModel: AccountChangeInfoScreenViewModel,
    state: State<AccountChangeInfoScreenState>,
    navHostController: NavHostController,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState
) {
    val context = LocalContext.current
    val keyboardOptions = LocalSoftwareKeyboardController.current

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let { imageUri ->
                try {
                    val size = getUriFileSize(context, uri)
                    if (size > 15 * 1024 * 1024) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.large_file_more_15_mb),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@let
                    }
                    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        val source = ImageDecoder.createSource(context.contentResolver, imageUri)
                        ImageDecoder.decodeBitmap(source) { decoder, info, _ ->
                            val width = info.size.width
                            val height = info.size.height
                            if (width > 1024 || height > 1024) {
                                val scale = min(1024f / width, 1024f / height)
                                decoder.setTargetSize(
                                    (width * scale).toInt(),
                                    (height * scale).toInt()
                                )
                            }
                        }
                    } else {
                        decodeAndResizeBitmap(context, imageUri)
                    } ?: run {
                        Toast.makeText(
                            context,
                            context.getString(R.string.error_account_photo_load),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@let
                    }
                    val base64String = bitmapToBase64(bitmap)
                    viewModel.send(AccountChangeInfoScreenIntent.PhotoInput(photo = base64String))
                } catch (_: Exception) {
                    viewModel.send(AccountChangeInfoScreenIntent.PhotoInput(photo = ""))
                }
            }
        }
    )

    Column(modifier = modifier) {
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn {
                item { SurnameInput(viewModel = viewModel, state = state) }
                item { Spacer(modifier = Modifier.padding(6.dp)) }
                item { NameInput(viewModel = viewModel, state = state) }
                item { Spacer(modifier = Modifier.padding(6.dp)) }
                item { PatronymicInput(viewModel = viewModel, state = state) }
                item { Spacer(modifier = Modifier.padding(6.dp)) }
                item {
                    Row {
                        UiButton(
                            state = UiButtonState.Chooser(
                                modifier = Modifier.weight(1f),
                                textModifier = Modifier.padding(vertical = 4.dp),
                                icon = painterResource(R.drawable.photo),
                                contentDescription = stringResource(R.string.photo)
                            ),
                            text = stringResource(R.string.photo_choose)
                        ) {
                            pickImageLauncher.launch("image/*")
                        }
                    }
                }
                item {
                    state.value.photo?.let {
                        Spacer(modifier = Modifier.padding(6.dp))
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
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
                                viewModel.send(AccountChangeInfoScreenIntent.PhotoInput(photo = ""))
                            }
                        }
                        Spacer(modifier = Modifier.padding(2.dp))
                    }
                }
            }
        }
        UiButton(
            state = UiButtonState.Base(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 2.dp)
                    .navigationBarsPadding()
                    .imePadding(),
                textModifier = Modifier.padding(top = 7.dp, bottom = 7.dp),
                enabled = state.value.isValidInfoForChange
            ),
            text = stringResource(R.string.save),
            onClick = {
                keyboardOptions?.hide()
                viewModel.send(
                    AccountChangeInfoScreenIntent.ChangeUserInfo(
                        onSuccess = {
                            navHostController.popBackStack()
                        },
                        onUnauthorized = {
                            scope.launch {
                                snackBarHostState.currentSnackbarData?.dismiss()
                                snackBarHostState.showSnackbar(
                                    message = context.getString(R.string.unauthorized),
                                    withDismissAction = true
                                )
                                onUnauthorizedNavigate(navHostController = navHostController)
                            }
                        }
                    )
                )
            }
        )
    }
}

private fun base64ToBitmap(base64: String): Bitmap {
    val decodedBytes = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}

@Composable
private fun SurnameInput(
    viewModel: AccountChangeInfoScreenViewModel,
    state: State<AccountChangeInfoScreenState>
) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.surname,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        onValueChange = {
            viewModel.send(AccountChangeInfoScreenIntent.SurnameInput(surname = it.trim()))
        },
        placeholderText = stringResource(R.string.surname_hint),
        upText = stringResource(R.string.surname)
    )
}

@Composable
private fun NameInput(
    viewModel: AccountChangeInfoScreenViewModel,
    state: State<AccountChangeInfoScreenState>
) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.name,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        onValueChange = {
            viewModel.send(AccountChangeInfoScreenIntent.NameInput(name = it.trim()))
        },
        placeholderText = stringResource(R.string.name_hint),
        upText = stringResource(R.string.name)
    )
}

@Composable
private fun PatronymicInput(
    viewModel: AccountChangeInfoScreenViewModel,
    state: State<AccountChangeInfoScreenState>
) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.patronymic,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        onValueChange = {
            viewModel.send(AccountChangeInfoScreenIntent.PatronymicInput(patronymic = it.trim()))
        },
        placeholderText = stringResource(R.string.patronymic_hint),
        upText = stringResource(R.string.patronymic)
    )
}

private fun decodeAndResizeBitmap(context: Context, uri: Uri): Bitmap? {
    return try {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        context.contentResolver.openInputStream(uri)?.use { stream ->
            BitmapFactory.decodeStream(stream, null, options)
        }

        options.inSampleSize = calculateInSampleSize(options)
        options.inJustDecodeBounds = false

        context.contentResolver.openInputStream(uri)?.use { stream ->
            BitmapFactory.decodeStream(stream, null, options)
        }
    } catch (_: Exception) {
        null
    }
}

private fun calculateInSampleSize(options: BitmapFactory.Options): Int {
    val (height, width) = options.run { outHeight to outWidth }
    var inSampleSize = 1

    if (height > 1024 || width > 1024) {
        val halfHeight = height / 2
        val halfWidth = width / 2
        while (halfHeight / inSampleSize >= 1024 && halfWidth / inSampleSize >= 1024) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

private fun getUriFileSize(context: Context, uri: Uri): Long {
    return context.contentResolver.openFileDescriptor(uri, "r")?.use { pfd ->
        pfd.statSize
    } ?: 0L
}

private fun bitmapToBase64(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    var quality = 80
    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)

    while (byteArrayOutputStream.size() > 5 * 1024 * 1024 && quality > 30) {
        byteArrayOutputStream.reset()
        quality -= 10
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
    }

    return Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT)
}