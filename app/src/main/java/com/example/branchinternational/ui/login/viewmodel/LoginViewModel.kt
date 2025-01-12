package com.example.branchinternational.ui.login.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.branchinternational.data.model.LoginResponse
import com.example.branchinternational.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _loginState = MutableStateFlow<Result<LoginResponse>?>(null)
    val loginState: StateFlow<Result<LoginResponse>?> = _loginState

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _toastMessage.value = "Username and password cannot be empty"
            return
        }

        viewModelScope.launch {
            try {
                val result = loginUseCase(username, password)
//                Log.d("result", "Auth token : ${result}")
//                Log.d("userdetails", "username: ${username} pass: ${password}")
                result.getOrNull()?.let { loginResponse ->
                    val authToken = loginResponse.authToken
//                    Log.d("auth", "Auth token : ${authToken}")
                    if (authToken.isNullOrEmpty()) {
                        _toastMessage.value = "Login Failed: Token is null or empty"
                    } else {
                        sharedPreferences.edit().putString("authToken", authToken).apply()
                        _loginState.value = Result.success(loginResponse)
                    }
                } ?: run {
                    _toastMessage.value = "Login Failed: Unknown error"
                }
            } catch (e: Exception) {
                _loginState.value = Result.failure(e)
//                Log.e("LoginViewModel", "Login failed: ${e.message}", e)
                _toastMessage.value = "Login Failed: ${e.message ?: "Unknown error"}"
            }
        }
    }
}

