package com.example.branchinternational.di

import com.example.branchinternational.data.source.remote.ApiService
import com.example.branchinternational.di.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @get:Provides
    val apiService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}