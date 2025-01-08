package com.example.branchinternational.data.repository.interfaces

import com.example.branchinternational.data.model.Message

interface MessageRepository {
    suspend fun getMessages(): Result<List<Message>>
}