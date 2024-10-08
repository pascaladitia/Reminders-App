package com.example.medicationreminder.ui.screen.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medicationreminder.R
import com.example.medicationreminder.data.local.model.UserEntity
import com.example.medicationreminder.domain.base.UiState
import com.example.medicationreminder.ui.component.FormBasicComponent
import com.example.medicationreminder.ui.component.FormEmailComponent
import com.example.medicationreminder.ui.component.FormPasswordComponent
import com.example.medicationreminder.ui.component.LoadingScreen
import com.example.medicationreminder.ui.component.ShowErrorDialog
import com.example.medicationreminder.ui.theme.MedicationReminderTheme
import com.example.medicationreminder.utils.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: RegisterViewModel = koinViewModel(),
    onNavBack: () -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.registerState.collectAsState()
    var isDialogVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isContentVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(200)
        isContentVisible = true
    }

    Surface(
        modifier = modifier.padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    ) {

        ShowErrorDialog(
            title = "Register Failed",
            message = errorMessage,
            isDialogVisible = isDialogVisible
        ) {
            isDialogVisible = false
        }

        RegisterContent(
            modifier = modifier,
            isContentVisible = isContentVisible,
            onRegister = { nim, name, email, pass ->
                coroutineScope.launch {
                    viewModel.loadRegister(
                        context,
                        UserEntity(
                            nim = nim,
                            name = name,
                            email = email,
                            password = pass
                        )
                    )
                }
            },
            onNavBack = {
                isContentVisible = false
                coroutineScope.launch {
                    delay(200)
                    onNavBack()
                }
            }
        )
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is UiState.Empty -> {}
            is UiState.Loading -> {}

            is UiState.Error -> {
                val errorState = uiState as UiState.Error
                errorMessage = errorState.message
                isDialogVisible = true
            }

            is UiState.Success -> {
                delay(200)
                onNavBack()
            }
        }
    }
}

@Composable
fun RegisterContent(
    modifier: Modifier = Modifier,
    isContentVisible: Boolean = true,
    onRegister: (String, String, String, String) -> Unit,
    onNavBack: () -> Unit
) {
    val context = LocalContext.current
    var nim by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var password2 by remember { mutableStateOf("") }

    var isNikError by remember { mutableStateOf(false) }
    var isNameError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }
    var isPassword2Error by remember { mutableStateOf(false) }

    var isPasswordVisible by remember { mutableStateOf(false) }
    var isPassword2Visible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInVertically(),
            exit = fadeOut(tween(durationMillis = 500)) + slideOutVertically()
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .height(80.dp)
                    .padding(bottom = 18.dp)
            )
        }

        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally { fullHeight -> fullHeight },
            exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally { fullHeight -> fullHeight }
        ) {
            Column {
                Text(
                    text = "Daftar",
                    style = MaterialTheme.typography.headlineMedium
                )

                Text(
                    text = "Daftar dibawah ini untuk akses semua fiturnya",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally(),
            exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally()
        ) {
            FormBasicComponent(
                title = "NIM",
                hintText = "Masukan NIM",
                value = nim,
                onValueChange = {
                    nim = it
                    isNikError = false
                },
                onError = isNikError,
            )
        }

        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally { fullHeight -> fullHeight },
            exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally { fullHeight -> fullHeight }
        ) {
            FormBasicComponent(
                title = "Fullname",
                hintText = "Masukan nama lengkap",
                value = name,
                onValueChange = {
                    name = it
                    isNameError = false
                },
                onError = isNameError,
            )
        }

        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally(),
            exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally()
        ) {
            FormEmailComponent(
                title = "Email",
                hintText = "Masukan alamat email",
                value = email,
                onValueChange = {
                    email = it
                    isEmailError = false
                },
                onError = isEmailError,
            )
        }

        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally { fullHeight -> fullHeight },
            exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally { fullHeight -> fullHeight }
        ) {
            FormPasswordComponent(
                title = "Password",
                hintText = "Masukan password",
                value = password,
                onValueChange = {
                    password = it
                    isPasswordError = false
                },
                isPasswordVisible = isPasswordVisible,
                onError = isPasswordError,
                onIconClick = {
                    isPasswordVisible = !isPasswordVisible
                }
            )
        }

        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally(),
            exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally()
        ) {
            FormPasswordComponent(
                title = "Retype Password",
                hintText = "Masukan ulang password",
                value = password2,
                onValueChange = {
                    password2 = it
                    isPassword2Error = false
                },
                isPasswordVisible = isPassword2Visible,
                onError = isPassword2Error,
                onIconClick = {
                    isPassword2Visible = !isPassword2Visible
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInVertically { fullHeight -> fullHeight },
            exit = fadeOut(tween(durationMillis = 500)) + slideOutVertically { fullHeight -> fullHeight }
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(42.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                onClick = {
                    if (nim.isBlank()) {
                        isNikError = true
                    }
                    if (name.isBlank()) {
                        isNameError = true
                    }
                    if (email.isBlank()) {
                        isEmailError = true
                    }
                    if (password.isBlank()) {
                        isPasswordError = true
                    }
                    if (password2.isBlank()) {
                        isPassword2Error = true
                    }

                    if (email.isNotBlank() && password.isNotBlank() && password2.isNotBlank()) {
                        if (password == password2) {
                            onRegister(nim, name, email, password)
                        } else {
                            showToast(context, "Password tidak sama!")
                        }
                    }
                },
            ) {
                Text(
                    text = "Buat akun baru",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        val textRegister = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                append("Sudah punya akun? ")
            }
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("Yuk login!")
            }
        }
        AnimatedVisibility(
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInVertically { fullHeight -> fullHeight },
            exit = fadeOut(tween(durationMillis = 500)) + slideOutVertically { fullHeight -> fullHeight }
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
                    .clickable { onNavBack() },
                text = textRegister,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun RegisterPreview() {
    MedicationReminderTheme {
        RegisterContent(
            onRegister = { nik, name, email, pass -> },
            onNavBack = {}
        )
    }
}