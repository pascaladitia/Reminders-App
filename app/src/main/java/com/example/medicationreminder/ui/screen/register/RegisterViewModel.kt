package com.example.medicationreminder.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminder.data.local.model.UserEntity
import com.example.medicationreminder.domain.base.UiState
import com.example.medicationreminder.domain.usecase.LocalUC
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val localUC: LocalUC
) : ViewModel() {

    private val _registerState = MutableStateFlow<UiState<String?>>(UiState.Empty)
    val registerState: StateFlow<UiState<String?>> = _registerState

    suspend fun loadRegister(data: UserEntity) {
        _registerState.value = UiState.Loading

        viewModelScope.launch {
            localUC.addUser(data).collect { result ->
                try {
                    _registerState.value = UiState.Success("Sukses register user")
                } catch (e: Exception) {
                    _registerState.value = UiState.Error("Gagal register user")
                }
            }
        }
    }
}