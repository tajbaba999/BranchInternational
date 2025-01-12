package com.example.branchinternational.domain.usecase

import com.example.branchinternational.data.model.LoginRequest
import com.example.branchinternational.data.model.LoginResponse
import com.example.branchinternational.data.repository.interfaces.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<LoginResponse> {
        if (username.isBlank() || password.isBlank()) {
            return Result.failure(IllegalArgumentException("Username and password cannot be empty"))
        }

        val request = LoginRequest(username, password)
        return loginRepository.login(request)
    }
}
