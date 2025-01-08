package com.example.branchinternational.ui.login.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.branchinternational.data.model.LoginResponse
import com.example.branchinternational.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        viewModelScope.launch {
            try {
                val result = loginUseCase(username, password)

                result.getOrNull()?.let { loginResponse ->
                    if (loginResponse.authToken.isNotEmpty()) {
                        sharedPreferences.edit().putString("authToken", loginResponse.authToken).apply()
                        _loginState.value = Result.success(loginResponse)
                    } else {
                        _toastMessage.value = "Login Failed: Token is null"
                    }
                } ?: run {
                    _toastMessage.value = "Login Failed: Unknown error"
                }
            } catch (e: Exception) {
                _loginState.value = Result.failure(e)

                if (e.message?.contains("401") == true || e.message?.contains("invalid") == true) {
                    _toastMessage.value = "Login Failed: Username or password is invalid"
                } else {
                    _toastMessage.value = "Login Failed: ${e.message}"
                }
            }
        }
    }
}
