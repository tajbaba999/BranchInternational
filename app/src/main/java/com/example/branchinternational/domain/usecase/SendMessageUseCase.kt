package com.example.branchinternational.domain.usecase

import com.example.branchinternational.data.model.Message
import com.example.branchinternational.data.repository.interfaces.SendMessageRepository
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val sendMessageRepository: SendMessageRepository
) {
    suspend operator fun invoke(message: Message): Result<Message> {
        if (message.body.isBlank()) {
            return Result.failure(IllegalArgumentException("Message body cannot be empty"))
        }

        return try {
            sendMessageRepository.sendMessage(message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
