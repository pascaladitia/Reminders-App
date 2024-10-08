package com.example.medicationreminder.ui.screen.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicationreminder.data.local.model.UserEntity
import com.example.medicationreminder.domain.base.UiState
import com.example.medicationreminder.domain.usecase.LocalUC
import com.example.medicationreminder.utils.showToast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody

class LoginViewModel(
    private val localUC: LocalUC
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<String?>>(UiState.Empty)
    val loginState: StateFlow<UiState<String?>> = _loginState

    suspend fun loadLogin(context: Context, email: String, password: String) {
        _loginState.value = UiState.Loading

        viewModelScope.launch {
            localUC.getUser().collect { result ->
                try {
                    val user = result.find { it?.email == email && it.password == password }

                    if (user != null) {
                        showToast(context, "Login Sukses")
                        _loginState.value = UiState.Success("Login Sukses")
                    } else {
                        _loginState.value = UiState.Error("Login gagal, harap cek user & password")
                    }
                } catch (e: Exception) {
                    _loginState.value = UiState.Error("Gagal login user: ${e.message}")
                }
            }
        }
    }
}