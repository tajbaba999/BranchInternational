package com.example.branchinternational.data.repository.interfaces

import com.example.branchinternational.data.model.LoginRequest
import com.example.branchinternational.data.model.LoginResponse

interface LoginRepository {
        suspend fun login(request: LoginRequest) : Result<LoginResponse>
}