package com.example.branchinternational.domain.usecase

import com.example.branchinternational.data.model.Message
import com.example.branchinternational.data.repository.interfaces.MessageRepository
import javax.inject.Inject

class GetMessagesUseCase @Inject constructor(
    private val messageRepository: MessageRepository
) {
    suspend operator fun invoke(): Result<List<Message>> {
        return try {
            messageRepository.getMessages()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}