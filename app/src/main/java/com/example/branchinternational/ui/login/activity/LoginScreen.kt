package com.example.branchinternational.ui.login.activity

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.branchinternational.data.model.LoginResponse
import com.example.branchinternational.ui.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    loginState: Result<LoginResponse>?,
    onLoginClick: (String, String) -> Unit
) {
    val backgroundColor = MaterialTheme.colorScheme.surface
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(vertical = 24.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Text(
            text = "Login",
            style = TextStyle(
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F4529)
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        CustomTextField(
            value = username,
            onValueChange = { username = it },
            label = "Email",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            password = password,
            onPasswordChange = { password = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            text = "Login",
            onClick = { onLoginClick(username, password) },
            modifier = Modifier.fillMaxWidth()
        )

        loginState?.let { result ->
            if (result.isFailure) {
                Spacer(modifier = Modifier.height(16.dp))
                ErrorMessage(
                    message = "Login failed: ${result.exceptionOrNull()?.message ?: "Unknown error"}",
                )
            } else if (result.isSuccess) {
                // Handle successful login here
                LaunchedEffect(result) {
                    Toast.makeText(context, "Login Sucessful!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


