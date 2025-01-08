package com.example.branchinternational.ui.messages.composable
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.branchinternational.data.model.Message
import com.example.branchinternational.ui.messages.viewmodel.MessageViewModel


@Composable
fun MessageThreadsScreen(navController: NavController, viewModel: MessageViewModel) {
    val isLoading = viewModel.isLoading.collectAsState().value
    val threads = viewModel.threadsState.collectAsState().value
    val searchQuery = remember { mutableStateOf("") }

    val context = LocalContext.current


    LaunchedEffect(true) {
        val authToken = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
            .getString("authToken", "")
        authToken?.let {
            if (it.isNotEmpty()) {
                viewModel.fetchThreads(it)
            }
        }
    }



    val filteredThreads = threads.filter {
        it.thread_id.toString().contains(searchQuery.value, ignoreCase = true)
    }




    val backgroundColor = MaterialTheme.colorScheme.surface

    Column(modifier = Modifier.fillMaxSize().background(backgroundColor)) {

        OutlinedTextField(
            value = searchQuery.value,
            onValueChange = { searchQuery.value = it },
            label = { Text("Enter Thread Number") },
//            placeholder = { Text("Search by Thread ID") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .padding(horizontal = 10.dp),
            singleLine = true,
//            colors = TextFieldDefaults.colors(
//                focusedContainerColor = MaterialTheme.colorScheme.primary,
//                unfocusedContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
//                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
//                unfocusedTextColor = MaterialTheme.colorScheme.onSurface
//            )
        )


        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator( color = MaterialTheme.colorScheme.primary)
            }
        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredThreads) { thread ->
                    ThreadItem(
                        thread = thread,
                        onClick = {
                            navController.navigate("threadDetails/${thread.thread_id}")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ThreadItem(
    thread: Message,
    onClick: () -> Unit
) {
//    val cardBackgroundColor = MaterialTheme.colorScheme.onBackground
    val borderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 3.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        shape = RoundedCornerShape(16.dp),
//        colors = CardDefaults.cardColors(containerColor = ,
        border = BorderStroke(1.dp, borderColor)

    ) {



        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Thread ID: ${thread.thread_id}",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFFEED3B1)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Last Message: ${thread.body}",
                style = MaterialTheme.typography.bodyMedium,
                color =  MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Sent by: ${thread.user_id} at ${thread.timestamp}",
                style = MaterialTheme.typography.bodySmall,
                color =  MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Agent id : ${thread.agent_id}",
                style = MaterialTheme.typography.bodySmall,
                color =  MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}