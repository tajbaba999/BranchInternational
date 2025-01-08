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

    private val _loginResult = MutableStateFlow<Result<LoginResponse>?>(null)
    val loginResult: StateFlow<Result<LoginResponse>?> = _loginResult.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = loginUseCase(username, password)

                response.getOrNull()?.let {
                    if (it.authToken.isNotEmpty()) {
                        sharedPreferences.edit().putString("authToken", it.authToken).apply()
                        _loginResult.value = Result.success(it)
                    } else {
                        _toastMessage.value = "Login Failed: Token is null"
                    }
                } ?: run {
                    _toastMessage.value = "Login Failed: Unknown error"
                }
            } catch (e: Exception) {
                _loginResult.value = Result.failure(e)

                if (e.message?.contains("401") == true || e.message?.contains("invalid") == true) {
                    _toastMessage.value = "Login Failed: Username or password is invalid"
                } else {
                    _toastMessage.value = "Login Failed: ${e.message}"
                }
            }
        }
    }
}
