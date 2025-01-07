package com.example.branchinternational.data.source.remote

import com.example.branchinternational.data.model.LoginRequest
import com.example.branchinternational.data.model.LoginResponse
import com.example.branchinternational.data.model.Message
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest) : LoginResponse

    @GET("api/messages")
    suspend fun getMesssages(
        @Header("X-Branch-Auth-Token") authToken : String
    ) : List<Message>

    @POST("api/messages")
    suspend fun sendMessage(
        @Header("X-Branch-Auth-Token") authToken: String,
        @Body message: Message
    ) : Response<Message>
}