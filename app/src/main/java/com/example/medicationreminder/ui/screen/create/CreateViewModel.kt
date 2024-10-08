package com.example.medicationreminder.ui.screen.create

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminder.data.local.model.UserEntity
import com.example.medicationreminder.data.prefs.PreferencesLogin
import com.example.medicationreminder.domain.base.UiState
import com.example.medicationreminder.domain.usecase.LocalUC
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CreateViewModel(
    private val localUC: LocalUC
) : ViewModel() {

    private val _profileState = MutableStateFlow<UiState<UserEntity?>>(UiState.Empty)
    val profileState: StateFlow<UiState<UserEntity?>> = _profileState

    suspend fun loadProfile(context: Context) {
        _profileState.value = UiState.Loading

        viewModelScope.launch {
            localUC.getUser().collect { result ->
                try {
                    val data = result.find { it?.email == PreferencesLogin.getEmail(context)}
                    _profileState.value = UiState.Success(data)
                } catch (e: Exception) {
                    _profileState.value = UiState.Error("Gagal profile user: ${e.message}")
                }
            }
        }
    }
}