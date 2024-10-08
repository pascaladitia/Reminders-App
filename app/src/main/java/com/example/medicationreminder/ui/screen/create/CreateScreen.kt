package com.example.medicationreminder.ui.screen.create

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.medicationreminder.data.local.model.UserEntity
import com.example.medicationreminder.domain.base.UiState
import com.example.medicationreminder.ui.component.LoadingScreen
import com.example.medicationreminder.ui.component.ShowErrorDialog
import com.example.medicationreminder.ui.theme.MedicationReminderTheme
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: CreateViewModel = koinViewModel(),
    onNavBack: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.profileState.collectAsState()
    var taskData by remember { mutableStateOf<UserEntity?>(null) }

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

        CreateContent(
            data = taskData
        ) { }
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
            val data = uiState as UiState.Success
            taskData = data.data
        }
    }
}

@Composable
fun CreateContent(
    modifier: Modifier = Modifier,
    data: UserEntity?,
    onCreate: () -> Unit
) {

}

@Preview
@Composable
private fun CreatePreview() {
    MedicationReminderTheme {
        CreateContent(
            data = UserEntity(),
            onCreate = {}
        )
    }
}