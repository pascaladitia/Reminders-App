package com.example.medicationreminder.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminder.data.local.model.TaskEntity
import com.example.medicationreminder.domain.base.UiState
import com.example.medicationreminder.domain.usecase.LocalUC
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val localUC: LocalUC
) : ViewModel() {

    private val _taskState = MutableStateFlow<UiState<List<TaskEntity?>>>(UiState.Empty)
    val taskState: StateFlow<UiState<List<TaskEntity?>>> = _taskState

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
}