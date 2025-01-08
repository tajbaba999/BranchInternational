// ThreadDetailScreen.kt
package com.example.branchinternational.ui.messages.composable

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.branchinternational.data.model.Message
import com.example.branchinternational.ui.components.MessageItem
import com.example.branchinternational.ui.messages.viewmodel.MessageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThreadDetailsScreen(
    navController: NavController,
    threadId: Int,
    viewModel: MessageViewModel
) {
    val messages = viewModel.messagesState.collectAsState().value.filter { it.thread_id == threadId }
    val newMessage = remember { mutableStateOf("") }
    val context = LocalContext.current

    LaunchedEffect(threadId) {
        viewModel.fetchMessages(context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE).getString("authToken", "")!!)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Thread Details: $threadId") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF1F4529))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(1f),
                reverseLayout = true
            ) {
                items(messages) { message ->
                    MessageItem(message)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newMessage.value,
                    onValueChange = { newMessage.value = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            sendMessage(threadId, newMessage.value, context, viewModel)
                            newMessage.value = ""
                        }
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    label = { Text("Type a message") }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        sendMessage(threadId, newMessage.value, context, viewModel)
                        newMessage.value = ""
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier.padding(start = 8.dp).padding(end = 8.dp)
                ) {
                    Text("Send", color = Color.White)
                }
            }
        }
    }
}

fun sendMessage(threadId: Int, messageBody: String, context: Context, viewModel: MessageViewModel) {
    if (messageBody.isNotEmpty()) {
        viewModel.sendMessage(threadId, messageBody)
        Toast.makeText(context, "Message Sent", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, "Message cannot be empty", Toast.LENGTH_SHORT).show()
    }
}
