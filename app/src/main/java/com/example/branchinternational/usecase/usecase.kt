package com.example.branchinternational.usecase

import android.util.Log
import com.example.branchinternational.data.model.LoginRequest
import com.example.branchinternational.data.model.LoginResponse
import com.example.branchinternational.data.model.Message
import com.example.branchinternational.data.repository.interfaces.LoginRepository
import com.example.branchinternational.data.repository.interfaces.MessageRepository
import com.example.branchinternational.data.repository.interfaces.SendMessageRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(username: String, password: String): Result<LoginResponse> {
        Log.d("userdetails", "username: ${username} pass: ${password}")
        if (username.isBlank() || password.isBlank()) {
            Log.d("userdetails", "Hii")
            return Result.failure(IllegalArgumentException("Username and password cannot be empty"))
        }


        return loginRepository.login(LoginRequest(username, password))
    }
}


class GetMessageUseCase @Inject constructor(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(): Result<List<Message>> {
        return try {
            messageRepository.getMessages()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

class SendMessageUseCase @Inject constructor(private val sendMessageRepository: SendMessageRepository) {
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
