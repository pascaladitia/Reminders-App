package com.example.medicationreminder.ui.screen.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medicationreminder.data.local.model.TaskEntity
import com.example.medicationreminder.data.local.model.UserEntity
import com.example.medicationreminder.domain.base.UiState
import com.example.medicationreminder.ui.component.ButtonComponent
import com.example.medicationreminder.ui.component.DatePickerComponent
import com.example.medicationreminder.ui.component.FormBasicComponent
import com.example.medicationreminder.ui.component.FormClickedComponent
import com.example.medicationreminder.ui.component.LoadingScreen
import com.example.medicationreminder.ui.component.ShowErrorDialog
import com.example.medicationreminder.ui.component.TimePickerComponent
import com.example.medicationreminder.ui.theme.MedicationReminderTheme
import com.example.medicationreminder.utils.showToast
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.ChevronDown
import compose.icons.feathericons.ChevronLeft
import compose.icons.feathericons.Trash
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    taskData: TaskEntity? = null,
    viewModel: CreateViewModel = koinViewModel(),
    onNavBack: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.createState.collectAsState()

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
            title = "Failed",
            message = errorMessage,
            isDialogVisible = isDialogVisible
        ) {
            isDialogVisible = false
        }

        CreateContent(
            data = taskData,
            onCreate = { result ->
                coroutineScope.launch {
                    viewModel.loadCreate(context, result)
                }
            }
        ) {
            onNavBack()
        }
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
            onNavBack()
        }
    }
}

@Composable
fun CreateContent(
    modifier: Modifier = Modifier,
    data: TaskEntity?,
    onCreate: (TaskEntity?) -> Unit,
    onNavBack: () -> Unit
) {
    val context = LocalContext.current
    var title by remember { mutableStateOf(data?.title ?: "") }
    var desc by remember { mutableStateOf(data?.description ?: "") }
    var dosis by remember { mutableStateOf(data?.dosis ?: "") }
    var date by remember { mutableStateOf(data?.date ?: "") }
    var time by remember { mutableStateOf(data?.time ?: "") }

    var isTitleError by remember { mutableStateOf(false) }
    var isDescError by remember { mutableStateOf(false) }
    var isDosisError by remember { mutableStateOf(false) }
    var isDateError by remember { mutableStateOf(false) }
    var isTimeError by remember { mutableStateOf(false) }

    var dialogDate by remember { mutableStateOf(false) }
    var dialogTime by remember { mutableStateOf(false) }

    if (dialogDate) {
        DatePickerComponent (
            onDismiss = {
                dialogDate = false
            },
            onConfirm = {
                dialogDate = false
                date = it
            }
        )
    }

    if (dialogTime) {
        TimePickerComponent (
            onDismiss = {
                dialogTime = false
            },
            onConfirm = {
                dialogTime = false
                time = it
            }
        )
    }

    Column(
        modifier = modifier
            .padding(24.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .clickable { onNavBack() }
                    .size(24.dp),
                imageVector = FeatherIcons.ChevronLeft,
                contentDescription = "",
                tint = Color.White
            )

            Spacer(Modifier.width(16.dp))

            Text(
                text = "Kembali",
                style = MaterialTheme.typography.titleSmall
            )
        }

        Spacer(Modifier.height(32.dp))

        Text(
            text = "Buat Pengingat",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Buat Pengingat dibawah ini agar tidak lupa dengan jadwalmu",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.tertiary
        )

        Spacer(Modifier.height(32.dp))

        FormBasicComponent(
            title = "Title",
            hintText = "Masukan title",
            value = title,
            onValueChange = {
                title = it
                isTitleError = false
            },
            onError = isTitleError
        )

        FormBasicComponent(
            modifier = Modifier.height(100.dp),
            title = "Deskripsi",
            hintText = "Masukan deskripsi",
            value = desc,
            onValueChange = {
                desc = it
                isDescError = false
            },
            onError = isDescError
        )

        FormBasicComponent(
            title = "Dosis",
            hintText = "Masukan dosis",
            value = dosis,
            onValueChange = {
                dosis = it
                isDosisError = false
            },
            onError = isDosisError
        )

        FormClickedComponent (
            title = "Tanggal",
            hintText = "Masukan tanggal",
            value = date,
            icons = FeatherIcons.ChevronDown,
            onValueChange = {
                date = it
                isDescError = false
            },
            onIconClick = {
                dialogDate = true
            },
            onError = isDateError
        )

        Spacer(Modifier.height(16.dp))

        FormClickedComponent (
            title = "Waktu",
            hintText = "Masukan Waktu",
            value = time,
            icons = FeatherIcons.ChevronDown,
            onValueChange = {
                time = it
                isTimeError = false
            },
            onIconClick = {
                dialogTime = true
            },
            onError = isTimeError
        )

        Spacer(Modifier.height(32.dp))

        ButtonComponent(
            modifier = Modifier.fillMaxWidth(),
            text = "Submit"
        ) {
            if (title.isBlank()) {
                isTitleError = true
            }
            if (desc.isBlank()) {
                isDescError = true
            }
            if (dosis.isBlank()) {
                isDosisError = true
            }
            if (date.isBlank()) {
                isDateError = true
            }
            if (time.isBlank()) {
                isTimeError = true
            }

            if (title.isNotBlank() && desc.isNotBlank() && dosis.isNotBlank()
                && date.isNotBlank() && time.isNotBlank()) {
                onCreate(
                    TaskEntity(
                        title = title,
                        description = desc,
                        dosis = dosis,
                        date = date,
                        time = time
                    )
                )
            } else {
                showToast(context, "Form harus di isi semua")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun CreatePreview() {
    MedicationReminderTheme {
        CreateContent(
            data = TaskEntity(),
            onCreate = {},
            onNavBack = {}
        )
    }
}