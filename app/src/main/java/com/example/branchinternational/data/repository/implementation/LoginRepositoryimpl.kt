package com.example.branchinternational.data.repository.implementation

import android.content.SharedPreferences
import com.example.branchinternational.data.model.LoginRequest
import com.example.branchinternational.data.model.LoginResponse
import com.example.branchinternational.data.repository.interfaces.LoginRepository
import com.example.branchinternational.data.source.remote.ApiService
import com.example.branchinternational.util.SharedPreferencesManager

class LoginRepositoryImpl constructor(private val apiService: ApiService, private val sharedPreferencesManager: SharedPreferencesManager) : LoginRepository {
    override suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response = apiService.login(request)
            sharedPreferencesManager.saveAuthToken(response.authToken)
            Result.success(response)
        }catch (e : Exception){
            Result.failure(e)
        }
    }

}