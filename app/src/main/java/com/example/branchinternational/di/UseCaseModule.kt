package com.example.branchinternational.di

import com.example.branchinternational.data.repository.implementation.LoginRepositoryImpl
import com.example.branchinternational.data.repository.interfaces.LoginRepository
import  com.example.branchinternational.domain.usecase.LoginUseCase
import com.example.branchinternational.data.source.remote.ApiService
import com.example.branchinternational.util.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideLoginRepository(
        apiService: ApiService,
        sharedPreferencesManager: SharedPreferencesManager
    ): LoginRepository {
        return LoginRepositoryImpl(apiService, sharedPreferencesManager)
    }

    @Provides
    fun provideLoginUseCase(loginRepository: LoginRepository): LoginUseCase {
        return LoginUseCase(loginRepository)
    }
}