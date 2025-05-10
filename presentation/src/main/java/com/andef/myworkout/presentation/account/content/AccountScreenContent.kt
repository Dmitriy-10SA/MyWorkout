package com.andef.myworkout.presentation.account.content

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
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andef.myworkout.R
import com.andef.myworkout.design.button.state.UiButtonState
import com.andef.myworkout.design.button.ui.UiButton
import com.andef.myworkout.design.dialog.ui.UiDialog
import com.andef.myworkout.design.error.ui.UiErrorOverlay
import com.andef.myworkout.design.fab.state.UiFABState
import com.andef.myworkout.design.fab.ui.UiFAB
import com.andef.myworkout.design.iconbutton.ui.UiIconButton
import com.andef.myworkout.design.input.state.UiInputState
import com.andef.myworkout.design.input.ui.UiInput
import com.andef.myworkout.design.loading.ui.UiLoadingOverlay
import com.andef.myworkout.design.scaffold.ui.UiScaffold
import com.andef.myworkout.design.snackbar.state.UiSnackBarState
import com.andef.myworkout.design.snackbar.ui.UiSnackBarHost
import com.andef.myworkout.design.topbar.ui.UiTopBar
import com.andef.myworkout.presentation.account.main.AccountScreenIntent
import com.andef.myworkout.presentation.account.main.AccountScreenState
import com.andef.myworkout.presentation.account.main.AccountScreenViewModel
import com.andef.myworkout.ui.utils.onUnauthorizedNavigate
import com.andef.myworkout.ui.utils.slideInUp
import com.andef.myworkout.ui.utils.slideOutDown
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import kotlin.math.min

@Composable
fun AccountScreenContent(
    viewModel: AccountScreenViewModel,
    snackBarHostState: SnackbarHostState,
    state: State<AccountScreenState>,
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    scope: CoroutineScope
) {
    if (state.value.showEditInfoDialog) {
        UiDialog(
            text = {
                ChangeInfoDialogContent(
                    viewModel = viewModel,
                    state = state,
                    navHostController = navHostController,
                    scope = scope,
                    snackBarHostState = snackBarHostState
                )
            },
            dismiss = { viewModel.send(AccountScreenIntent.ChangeEditInfoDialogVisible) }
        )
    }
    UiScaffold(
        topBar = {
            TopBarContent(viewModel = viewModel, navHostController = navHostController)
        },
        floatingActionButton = {
            AnimatedContent(
                targetState = !state.value.isError,
                transitionSpec = { slideInUp.togetherWith(slideOutDown) }
            ) { state ->
                if (state) FABContent(viewModel = viewModel, paddingValues = paddingValues)
            }
        }
    ) { topPadding ->
        UiSnackBarHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topPadding.calculateTopPadding()),
            snackBarHostState = snackBarHostState,
            state = UiSnackBarState.Error
        )
        Content(
            state = state,
            topPadding = topPadding,
            paddingValues = paddingValues,
            viewModel = viewModel,
            navHostController = navHostController,
            scope = scope,
            snackBarHostState = snackBarHostState
        )
    }
}

@Composable
private fun Content(
    state: State<AccountScreenState>,
    paddingValues: PaddingValues,
    topPadding: PaddingValues,
    viewModel: AccountScreenViewModel,
    scope: CoroutineScope,
    navHostController: NavHostController,
    snackBarHostState: SnackbarHostState
) {
    val context = LocalContext.current

    state.value.userInfo?.let { userInfo ->
        UserInfoContent(
            paddingValues = PaddingValues(
                bottom = paddingValues.calculateBottomPadding(),
                top = topPadding.calculateTopPadding()
            ),
            userInfo = userInfo,
            viewModel = viewModel,
            scope = scope,
            navHostController = navHostController,
            snackBarHostState = snackBarHostState
        )
    }

    if (state.value.isLoading) {
        UiLoadingOverlay(
            text = stringResource(R.string.my_workout),
            paddingValues = PaddingValues(
                bottom = paddingValues.calculateBottomPadding(),
                top = topPadding.calculateTopPadding()
            )
        )
    } else if (state.value.isError) {
        UiErrorOverlay(
            paddingValues = PaddingValues(
                bottom = paddingValues.calculateBottomPadding(),
                top = topPadding.calculateTopPadding()
            ),
            onRetry = {
                viewModel.send(
                    AccountScreenIntent.LoadUserInfo(
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

@Composable
private fun ChangeInfoDialogContent(
    viewModel: AccountScreenViewModel,
    state: State<AccountScreenState>,
    navHostController: NavHostController,
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState
) {
    val context = LocalContext.current

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
                    viewModel.send(AccountScreenIntent.PhotoInput(photo = base64String))
                } catch (_: Exception) {
                    viewModel.send(AccountScreenIntent.PhotoInput(photo = ""))
                }
            }
        }
    )

    LazyColumn {
        item {
            SurnameInput(viewModel = viewModel, state = state)
            Spacer(modifier = Modifier.padding(3.dp))
            NameInput(viewModel = viewModel, state = state)
            Spacer(modifier = Modifier.padding(3.dp))
            PatronymicInput(viewModel = viewModel, state = state)
            Spacer(modifier = Modifier.padding(4.dp))
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
            Spacer(modifier = Modifier.padding(4.dp))
            Row {
                UiButton(
                    state = UiButtonState.Base(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 2.dp),
                        textModifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                    ),
                    text = stringResource(R.string.cancel)
                ) {
                    viewModel.send(AccountScreenIntent.ChangeEditInfoDialogVisible)
                }
                UiButton(
                    state = UiButtonState.Base(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 2.dp),
                        enabled = state.value.isValidInfoForChange,
                        textModifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                    ),
                    text = stringResource(R.string.change_user_info)
                ) {
                    viewModel.send(AccountScreenIntent.ChangeEditInfoDialogVisible)
                    viewModel.send(
                        AccountScreenIntent.ChangeUserInfo(
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
            }
        }
    }
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

@Composable
private fun FABContent(viewModel: AccountScreenViewModel, paddingValues: PaddingValues) {
    UiFAB(
        modifier = Modifier
            .padding(bottom = paddingValues.calculateBottomPadding()),
        painter = painterResource(R.drawable.edit),
        contentDescription = stringResource(R.string.edit_account_info),
        state = UiFABState.Base
    ) {
        viewModel.send(AccountScreenIntent.ChangeEditInfoDialogVisible)
    }
}

@Composable
private fun TopBarContent(viewModel: AccountScreenViewModel, navHostController: NavHostController) {
    UiTopBar(
        title = stringResource(R.string.account),
        actions = {
            UiIconButton(
                painter = painterResource(R.drawable.logout),
                contentDescription = stringResource(R.string.logout)
            ) {
                viewModel.send(AccountScreenIntent.Logout(onLogout = {
                    onUnauthorizedNavigate(navHostController = navHostController)
                }))
            }
        }
    )
}

@Composable
private fun SurnameInput(viewModel: AccountScreenViewModel, state: State<AccountScreenState>) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.surname,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        onValueChange = { viewModel.send(AccountScreenIntent.SurnameInput(surname = it)) },
        placeholderText = stringResource(R.string.surname_hint),
        upText = stringResource(R.string.surname)
    )
}

@Composable
private fun NameInput(viewModel: AccountScreenViewModel, state: State<AccountScreenState>) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.name,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        onValueChange = { viewModel.send(AccountScreenIntent.NameInput(name = it)) },
        placeholderText = stringResource(R.string.name_hint),
        upText = stringResource(R.string.name)
    )
}

@Composable
private fun PatronymicInput(viewModel: AccountScreenViewModel, state: State<AccountScreenState>) {
    UiInput(
        modifier = Modifier.fillMaxWidth(),
        state = UiInputState.Base,
        value = state.value.patronymic,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        onValueChange = { viewModel.send(AccountScreenIntent.PatronymicInput(patronymic = it)) },
        placeholderText = stringResource(R.string.patronymic_hint),
        upText = stringResource(R.string.patronymic)
    )
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