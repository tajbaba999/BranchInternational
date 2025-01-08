package com.example.branchinternational.usecase

import com.example.branchinternational.data.model.LoginRequest
import com.example.branchinternational.data.model.LoginResponse
import com.example.branchinternational.data.model.Message
import com.example.branchinternational.data.repository.interfaces.LoginRepository
import com.example.branchinternational.data.repository.interfaces.MessageRepository
import com.example.branchinternational.data.repository.interfaces.SendMessageRepository

    class LoginUseCase constructor(private val loginRepository: LoginRepository) {
        suspend operator fun invoke(username: String, password: String): Result<LoginResponse> {
            if (username.isBlank() || password.isBlank()) {
                return Result.failure(IllegalArgumentException("Username and password cannot be empty"))
            }
            return loginRepository.login(LoginRequest(username, password))
        }
    }

    class GetMessageUseCase constructor(private val messageRepository: MessageRepository) {
        suspend operator fun invoke(): Result<List<Message>> {
            return messageRepository.getMessages()
        }
    }

    class SendMessageUseCase constructor(private val sendMessageRepository: SendMessageRepository) {
        suspend operator fun invoke(message: Message): Result<Message> {
            if (message.body.isBlank()) {
                return Result.failure(IllegalArgumentException("Message body cannot be empty"))
            }
            return sendMessageRepository.sendMessage(message)
        }
    }

