package com.example.branchinternational.ui.messages.composable
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import com.example.branchinternational.ui.components.ThreadItem
import com.example.branchinternational.ui.messages.viewmodel.MessageViewModel

@RequiresApi(Build.VERSION_CODES.O)
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .padding(horizontal = 10.dp),
            singleLine = true
        )

        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
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