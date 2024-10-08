package com.example.medicationreminder.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medicationreminder.R
import com.example.medicationreminder.data.local.model.TaskEntity
import com.example.medicationreminder.domain.base.UiState
import com.example.medicationreminder.ui.component.ButtonComponent
import com.example.medicationreminder.ui.component.LoadingScreen
import com.example.medicationreminder.ui.component.ShowErrorDialog
import com.example.medicationreminder.ui.theme.MedicationReminderTheme
import compose.icons.FeatherIcons
import compose.icons.feathericons.Plus
import compose.icons.feathericons.Tag
import compose.icons.feathericons.Trash
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = koinViewModel(),
    onCreate: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.taskState.collectAsState()
    var taskData by remember { mutableStateOf<List<TaskEntity?>?>(null) }

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

        HomeContent(
            data = taskData ?: emptyList()
        ) {
            onCreate()
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
            taskData = data.data
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    data: List<TaskEntity?>,
    onCreate: () -> Unit
) {
    Box {
        Column(
            modifier = modifier
                .padding(24.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1.2f)
                ) {
                    Text(
                        text = "Welcome",
                        style = MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        text = "Pengingat obat. dan masih banyak lagi.",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .width(80.dp)
                        .padding(bottom = 18.dp)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(data) {
                    HomeItem(data = it) {

                    }
                }
            }
        }

        ButtonComponent(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 42.dp)
                .shadow(8.dp, RoundedCornerShape(16.dp))
                .align(Alignment.BottomEnd),
            text = "Tambahkan",
            isIcon = 1,
            icon = FeatherIcons.Plus
        ) {
            onCreate()
        }
    }
}

@Composable
fun HomeItem(
    modifier: Modifier = Modifier,
    data: TaskEntity?,
    onDelete: (TaskEntity?) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = FeatherIcons.Tag,
                contentDescription = "",
                tint = Color.White
            )

            Spacer(Modifier.width(16.dp))

            Text(
                text = data?.title ?: "No Title",
                style = MaterialTheme.typography.titleSmall
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = data?.description ?: "No Description",
            style = MaterialTheme.typography.bodySmall
        )

        Spacer(Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = FeatherIcons.Tag,
                contentDescription = "",
                tint = Color.White
            )

            Spacer(Modifier.width(8.dp))

            Text(
                text = data?.date ?: "Date empty",
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .background(Color.Gray, RoundedCornerShape(6.dp))
                    .padding(6.dp),
                text = "Active",
                style = MaterialTheme.typography.bodySmall
            )

            Icon(
                modifier = Modifier
                    .clickable { onDelete(data) }
                    .size(24.dp),
                imageVector = FeatherIcons.Trash,
                contentDescription = "",
                tint = Color.Red
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomePreview() {
    MedicationReminderTheme {
        HomeContent(
            data = listOf(
                TaskEntity(),
                TaskEntity()
            ),
            onCreate = {}
        )
    }
}