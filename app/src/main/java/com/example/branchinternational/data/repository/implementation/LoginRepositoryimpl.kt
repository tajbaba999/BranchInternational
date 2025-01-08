package com.example.branchinternational.data.repository.implementation

import android.util.Log
import com.example.branchinternational.data.model.LoginRequest
import com.example.branchinternational.data.model.LoginResponse
import com.example.branchinternational.data.repository.interfaces.LoginRepository
import com.example.branchinternational.data.source.remote.ApiService
import com.example.branchinternational.util.SharedPreferencesManager
import javax.inject.Inject

//@Inject
class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val sharedPreferencesManager: SharedPreferencesManager
) : LoginRepository {
    override suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            Log.d("cred"," username : ${request.username}  paswrod : ${request.password}")
            val response = apiService.login(request)
            Log.d("LoginRepository", "Raw JSON Response: ${response.authToken}")
            if (response.authToken.isNullOrEmpty()) {
                Log.e("LoginRepository", "Error: authToken is null or empty")
              return  Result.failure(Exception("Auth token is missing"))
            }
                sharedPreferencesManager.saveAuthToken(response.authToken)
                Result.success(response)
        } catch (e: Exception) {
            Log.e("LoginRepository", "Login failed", e) // Log the full exception
            Result.failure(e)
        }
    }
}

