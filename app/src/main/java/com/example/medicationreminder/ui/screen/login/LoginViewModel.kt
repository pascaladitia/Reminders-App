package com.example.medicationreminder.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminder.data.local.model.UserEntity
import com.example.medicationreminder.domain.base.UiState
import com.example.medicationreminder.domain.usecase.LocalUC
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class LoginViewModel(
    private val localUC: LocalUC
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<String?>>(UiState.Empty)
    val loginState: StateFlow<UiState<String?>> = _loginState

    suspend fun loadLogin(body: RequestBody) {
        _loginState.value = UiState.Loading

        viewModelScope.launch {
            localUC.getUser().collect { result ->
                try {
                    _loginState.value = UiState.Success("Sukses login user")
                } catch (e: Exception) {
                    _loginState.value = UiState.Success("Gagal login user")
                }
            }
        }
    }

}