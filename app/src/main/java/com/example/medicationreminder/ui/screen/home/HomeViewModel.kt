package com.example.medicationreminder.ui.screen.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminder.data.local.model.TaskEntity
import com.example.medicationreminder.domain.base.UiState
import com.example.medicationreminder.domain.usecase.LocalUC
import com.example.medicationreminder.utils.cancelAlarm
import com.example.medicationreminder.utils.showToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val localUC: LocalUC
) : ViewModel() {

    private val _taskState = MutableStateFlow<UiState<List<TaskEntity?>?>>(UiState.Empty)
    val taskState: StateFlow<UiState<List<TaskEntity?>?>> = _taskState

    suspend fun loadTask() {
        _taskState.value = UiState.Loading

        viewModelScope.launch {
            localUC.getTask().collect { result ->
                try {
                    if (result.isNotEmpty()) {
                        _taskState.value = UiState.Success(result)
                    } else {
                        _taskState.value = UiState.Empty
                    }
                } catch (e: Exception) {
                    _taskState.value = UiState.Error("Gagal task user: ${e.message}")
                }
            }
        }
    }

    suspend fun deleteTask(context: Context, task: TaskEntity?) {
        _taskState.value = UiState.Loading

        viewModelScope.launch {
            task?.let {
                localUC.deleteTask(it).collect { result ->
                    try {
                        loadTask()
                        cancelAlarm(context, task.alarmId)
                        showToast(context, "Berhasil menghapus data")
                    } catch (e: Exception) {
                        Log.e("Tag delete", e.message.toString())
                    }
                }
            }
        }
    }
}