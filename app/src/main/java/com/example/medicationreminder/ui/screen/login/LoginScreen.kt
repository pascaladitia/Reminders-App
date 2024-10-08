package com.example.medicationreminder.ui.screen.login

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medicationreminder.R
import com.example.medicationreminder.data.prefs.PreferencesLogin
import com.example.medicationreminder.domain.base.UiState
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
fun LoginScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: LoginViewModel = koinViewModel(),
    onLogin: () -> Unit,
    onRegister: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.loginState.collectAsState()
    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    var isSaveLogin by remember { mutableStateOf(false) }
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
            title = "Login Failed",
            message = errorMessage,
            isDialogVisible = isDialogVisible
        ) {
            isDialogVisible = false
        }

        LoginContent(
            modifier = modifier,
            isContentVisible = isContentVisible,
            onLogin = { email, password, isLogin ->
                emailValue = email
                passwordValue = password
                isSaveLogin = isLogin

                coroutineScope.launch {
                    viewModel.loadLogin(context, email, password)
                }
            },
            onRegister = {
                isContentVisible = false
                coroutineScope.launch {
                    delay(200)
                    onRegister()
                }
            }
        )
    }

    when (uiState) {
        is UiState.Empty -> {}
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Error -> {
            val errorState = uiState as UiState.Error
            errorMessage = errorState.message
            isDialogVisible = true
        }

        is UiState.Success -> {
            PreferencesLogin.saveIsLogin(context, emailValue, isSaveLogin)
            onLogin()
        }
    }
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    isContentVisible: Boolean = true,
    onLogin: (String, String, Boolean) -> Unit,
    onRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var saveLogin by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    Box {
        Column(
            modifier = modifier
                .padding(horizontal = 24.dp)
                .fillMaxSize()
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Center
        ) {

            AnimatedVisibility(
                visible = isContentVisible,
                enter = fadeIn(tween(durationMillis = 500)) + slideInVertically(),
                exit = fadeOut(tween(durationMillis = 500)) + slideOutVertically()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(bottom = 18.dp)
                )
            }

            AnimatedVisibility(
                visible = isContentVisible,
                enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally(),
                exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally()
            ) {
                Column {
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        text = "Log in di bawah untuk akses akun mu",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(
                visible = isContentVisible,
                enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally { fullHeight -> fullHeight },
                exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally { fullHeight -> fullHeight }
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
                enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally(),
                exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally()
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
                enter = fadeIn(tween(durationMillis = 500)) + slideInHorizontally { fullHeight -> fullHeight },
                exit = fadeOut(tween(durationMillis = 500)) + slideOutHorizontally { fullHeight -> fullHeight }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = saveLogin,
                        onCheckedChange = { saveLogin = it },
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = MaterialTheme.colorScheme.tertiary,
                            disabledCheckedColor = MaterialTheme.colorScheme.tertiary
                        ),
                    )
                    Text(
                        text = "Remember me",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }

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
                        if (email.isBlank()) {
                            isEmailError = true
                        }
                        if (password.isBlank()) {
                            isPasswordError = true
                        }

                        if (email.isNotBlank() && password.isNotBlank()) {
                            onLogin(email, password, saveLogin)
                        }
                    },
                ) {
                    Text(
                        text = "Masuk",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White
                        )
                    )
                }
            }
        }

        val textRegister = buildAnnotatedString {
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.tertiary)) {
                append("Belum punya akun? ")
            }
            withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                append("Daftar disini ya!")
            }
        }

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = isContentVisible,
            enter = fadeIn(tween(durationMillis = 500)) + slideInVertically { fullHeight -> fullHeight },
            exit = fadeOut(tween(durationMillis = 500)) + slideOutVertically { fullHeight -> fullHeight }
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .clickable { onRegister() },
                text = textRegister,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun LoginPreview() {
    MedicationReminderTheme {
        LoginContent(onLogin = { email, password, bol -> }, onRegister = {})
    }
}