package com.example.branchinternational.ui.components

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.branchinternational.data.model.Message
import java.sql.Timestamp
import java.time.DateTimeException
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun MessageItem(message: Message) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.agent_id != null) {
            Arrangement.End
        } else {
            Arrangement.Start
        }
    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Sender: ${message.user_id ?: "Agent"}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Message: ${message.body}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Sent at: ${message.timestamp}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ThreadItem(
    thread: Message,
    onClick: () -> Unit
) {

    val borderColor = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 3.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
        shape = RoundedCornerShape(16.dp),
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
                color = MaterialTheme.colorScheme.secondary
            )
//            Log.d("time", "${thread.timestamp}")
//            Log.d("times", "${formatIsoTimestamp(thread.timestamp)}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Sent by: ${thread.user_id} at ${thread.timestamp}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Agent id : ${thread.agent_id}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//fun formatIsoTimestamp(timestamp: String): String {
//    return try {
//        val instant = Instant.parse(timestamp) // Parse the ISO 8601 string
//        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a", Locale.getDefault()) // Define desired format
//        instant.atZone(ZoneId.systemDefault()).format(formatter) // Convert to system's timezone and format
//    } catch (e: Exception) {
//        "Invalid date" // Return a fallback string in case of parsing errors
//    }
//}
