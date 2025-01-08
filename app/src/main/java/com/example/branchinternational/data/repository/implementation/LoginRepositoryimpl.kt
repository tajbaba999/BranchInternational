package com.example.branchinternational.data.repository.implementation

import android.content.SharedPreferences
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
            val response = apiService.login(request)
//            Log.d("resp", "${response.body()?.authToken}")

            if (response.isSuccessful) {
                response.body()?.let { loginResponse ->
                    if (!loginResponse.authToken.isNullOrEmpty()) {

                        sharedPreferencesManager.saveAuthToken(loginResponse.authToken)

                        return Result.success(loginResponse)
                    }
                }
                Result.failure(Exception("Auth token is missing"))
            } else {
                Result.failure(Exception("HTTP error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Log.e("LoginRepository", "Login failed", e)
            Result.failure(e)
        }
    }

}

