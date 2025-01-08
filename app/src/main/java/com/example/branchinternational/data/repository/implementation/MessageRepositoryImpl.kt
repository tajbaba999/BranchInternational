package com.example.branchinternational.data.repository.implementation

import com.example.branchinternational.data.model.Message
import com.example.branchinternational.data.repository.interfaces.MessageRepository
import com.example.branchinternational.data.source.remote.ApiService
import com.example.branchinternational.util.SharedPreferencesManager
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(private val apiService: ApiService, private val sharedPreferencesManager: SharedPreferencesManager) : MessageRepository{
    private fun getAuthToken() : String?{
        return sharedPreferencesManager.getAuthToken()
    }

    override suspend fun getMessages(): Result<List<Message>> {
       return try {
           val token = getAuthToken() ?: return Result.failure(Exception("No auth token"))
           Result.success(apiService.getMesssages(token))
       }catch (e : Exception){
           Result.failure(e)
       }
    }

}