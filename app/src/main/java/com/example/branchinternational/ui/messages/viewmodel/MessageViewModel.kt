package com.example.branchinternational.ui.messages.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.branchinternational.data.model.Message
import com.example.branchinternational.data.source.remote.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val apiService: ApiService,
    private val sharedPreferences: SharedPreferences,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _threadsState = MutableStateFlow<List<Message>>(emptyList())
    val threadsState: StateFlow<List<Message>> = _threadsState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _messagesState = MutableStateFlow<List<Message>>(emptyList())
    val messagesState: StateFlow<List<Message>> = _messagesState.asStateFlow()

    private val _sendMessageResult = MutableStateFlow<Result<Message>?>(null)
    val sendMessageResult: StateFlow<Result<Message>?> = _sendMessageResult.asStateFlow()
    fun fetchThreads(authToken: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val messages = apiService.getMesssages(authToken)
                val groupedThreads = messages.groupBy { it.thread_id }
                    .map { (_, threadMessages) ->
                        threadMessages.maxByOrNull { it.timestamp }!!
                    }
                _threadsState.value = groupedThreads
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
    fun fetchMessages(authToken: String) {
        viewModelScope.launch {
            try {
                val messages = apiService.getMesssages(authToken)
                _messagesState.value = messages
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun sendMessage(threadId: Int, messageBody: String) {
        viewModelScope.launch {
            val authToken = sharedPreferences.getString("authToken", null) ?: return@launch

            val newMessage = Message(
                id = 0,
                thread_id = threadId,
                user_id = "user",
                body = messageBody,
                timestamp = System.currentTimeMillis().toString(),
                agent_id = null
            )

            try {
                apiService.sendMessage(authToken, newMessage)
                fetchThreads(authToken)
                fetchMessages(authToken)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    }
}