package com.example.branchinternational.di

import com.example.branchinternational.data.source.remote.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private const val BASE_URL = ""

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}