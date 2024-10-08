package com.example.medicationreminder.ui.screen.create

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminder.data.local.model.TaskEntity
import com.example.medicationreminder.domain.base.UiState
import com.example.medicationreminder.domain.usecase.LocalUC
import com.example.medicationreminder.utils.NotifUtils.Companion.CHANNEL_ID
import com.example.medicationreminder.utils.NotifUtils.Companion.CHANNEL_NAME
import com.example.medicationreminder.utils.createChannel
import com.example.medicationreminder.utils.getUniqueId
import com.example.medicationreminder.utils.showToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateViewModel(
    private val localUC: LocalUC
) : ViewModel() {

    private val _createState = MutableStateFlow<UiState<String?>>(UiState.Empty)
    val createState: StateFlow<UiState<String?>> = _createState

    suspend fun loadCreate(context: Context, task: TaskEntity?) {
        _createState.value = UiState.Loading

        viewModelScope.launch {
            if (task != null) {
                localUC.addTask(task).collect { result ->
                    try {
                        createNotification(context, task)

                        showToast(context, "Berhasil")
                        _createState.value = UiState.Success("")
                    } catch (e: Exception) {
                        _createState.value = UiState.Error("Gagal create user: ${e.message}")
                    }
                }
            }
        }
    }

    private fun createNotification(context: Context, result: TaskEntity?) {
        val task = TaskEntity(
            title = result?.title,
            description = result?.description,
            dosis = result?.dosis,
            date = result?.date,
            time = result?.time,
            false,
            "Hight",
            getUniqueId()
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  // API 26
            val channelId = CHANNEL_ID
            val channelName = CHANNEL_NAME
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(
                    channelId.toString(),
                    channelName,
                    importance
                )
            val notificationManager =
                context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)

            createChannel(channelId, context, task)
        }
    }
}