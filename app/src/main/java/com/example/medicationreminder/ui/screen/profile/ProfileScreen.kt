package com.example.medicationreminder.ui.screen.profile

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Announcement
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.EmojiEmotions
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.medicationreminder.R
import com.example.medicationreminder.data.local.model.UserEntity
import com.example.medicationreminder.data.prefs.PreferencesLogin
import com.example.medicationreminder.domain.base.UiState
import com.example.medicationreminder.ui.component.ButtonComponent
import com.example.medicationreminder.ui.component.CameraGalleryDialog
import com.example.medicationreminder.ui.component.LoadingScreen
import com.example.medicationreminder.ui.component.ShowErrorDialog
import com.example.medicationreminder.ui.theme.MedicationReminderTheme
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    viewModel: ProfileViewModel = koinViewModel(),
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val uiState by viewModel.profileState.collectAsState()
    var taskData by remember { mutableStateOf<UserEntity?>(null) }

    var isDialogVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isContentVisible by remember { mutableStateOf(false) }

    var isFoto by remember { mutableStateOf(PreferencesLogin.getFoto(context)) }
    var isDialogFoto by remember { mutableStateOf(false) }

    if (isDialogFoto) {
        CameraGalleryDialog(
            showDialogCapture = isDialogFoto,
            onSelect = {
                isDialogFoto = false
                PreferencesLogin.saveFoto(context, it.toString())
                isFoto = it.toString()
            }
        ) {
            isDialogFoto = false
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadProfile(context)
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

        ProfileContent(
            data = taskData,
            isFoto = isFoto.toString(),
            onChangeFoto = {
                isDialogFoto = true
            }
        ) {
            PreferencesLogin.deleteLoginData(context)
            onLogout()
        }
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
                val data = uiState as UiState.Success
                taskData = data.data
            }
        }
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    data: UserEntity?,
    isFoto: String = "",
    onChangeFoto: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ProfileHeader()

        Column(
            modifier = modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        ImageRequest.Builder(LocalContext.current)
                            .data(data = Uri.parse(isFoto))
                            .error(R.drawable.no_profile)
                            .apply { crossfade(true) }
                            .build()
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onChangeFoto() }
                        .size(80.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        modifier = Modifier,
                        text = data?.name ?: "No Name",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = data?.email ?: "No Email",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Feature",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.DarkGray)
                    .padding(12.dp)
            ) {
                ProfileMenu(
                    icons = Icons.Outlined.Person,
                    title = "Personal Information"
                ) {

                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                ProfileMenu(
                    icons = Icons.Outlined.WorkOutline,
                    title = "Status"
                ) {

                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                ProfileMenu(
                    icons = Icons.Outlined.Person,
                    title = "Notification"
                ) {

                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.DarkGray)
                    .padding(12.dp)
            ) {
                ProfileMenu(
                    icons = Icons.Outlined.StarOutline,
                    title = "Rate Our App"
                ) {

                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                ProfileMenu(
                    icons = Icons.Outlined.Settings,
                    title = "Settings"
                ) {

                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                ProfileMenu(
                    icons = Icons.Outlined.EmojiEmotions,
                    title = "Reviews"
                ) {

                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                ProfileMenu(
                    icons = Icons.AutoMirrored.Outlined.Announcement,
                    title = "About Us"
                ) {

                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            ButtonComponent(
                modifier = Modifier.fillMaxWidth(),
                text = "Logout"
            ) {
                onLogout()
            }
        }

    }
}

@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(8.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = "Profile",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ProfileMenu(
    modifier: Modifier = Modifier,
    icons: ImageVector = Icons.Default.Home,
    title: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = icons,
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(32.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Icon(
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterEnd),
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color.White
        )
    }
}


@Preview
@Composable
private fun ProfilePreview() {
    MedicationReminderTheme {
        ProfileContent(
            data = UserEntity(),
            onChangeFoto = {},
            onLogout = {}
        )
    }
}