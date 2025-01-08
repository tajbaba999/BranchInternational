package com.example.branchinternational.data.repository.implementation

import com.example.branchinternational.data.model.Message
import com.example.branchinternational.data.repository.interfaces.SendMessageRepository
import com.example.branchinternational.data.source.remote.ApiService
import com.example.branchinternational.util.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class SharedMessageRepositoryImpl constructor(private val apiService: ApiService, private val sharedPreferencesManager: SharedPreferencesManager) : SendMessageRepository {
    override suspend fun sendMessage(message: Message): Result<Message> {
        return withContext(Dispatchers.IO){
            try {
                val token = getAuthToken() ?: return@withContext Result.failure(Exception("No auth token found"))
                val response = apiService.sendMessage(token, message)
                if (response.isSuccessful){
                    val sentMessage = response.body()
                    sentMessage?.let {
                        Result.success(it)
                    } ?: Result.failure(IOException("Response body is null"))
                }else{
                    Result.failure(IOException("Unsucessful response: ${response.code()}"))
                }
            }catch (e : IOException){
                Result.failure(IOException("Network error: ${e.message}"))
            } catch (e : Exception){
                Result.failure(IOException("Error sending message: ${e.message}"))
            }
        }
    }

}