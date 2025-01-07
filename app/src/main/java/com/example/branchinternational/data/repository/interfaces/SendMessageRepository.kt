package com.example.branchinternational.data.repository.interfaces

import com.example.branchinternational.data.model.Message

interface SendMessageRepository {
    suspend fun sendMessage(message: Message): Result<Message>
}