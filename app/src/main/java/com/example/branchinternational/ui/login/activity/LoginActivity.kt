package com.example.branchinternational.ui.login.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.branchinternational.ui.login.viewmodel.LoginViewModel
import com.example.branchinternational.ui.theme.BranchInternationalTheme
import com.example.branchinternational.ui.theme.BranchinternationalTheme
import com.example.branchinternational.util.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BranchinternationalTheme  {
                LoginScreen()
            }
        }
    }
}

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel()) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginResult by viewModel.loginState.collectAsState()
    val isLoggedIn by viewModel.isLoggedIn.collectAsState() // Observe isLoggedIn state
    val context = LocalContext.current

    if (isLoggedIn) { // Navigate using Compose state
        val token = viewModel.getAuthToken()
        if (token != null) {
            DashboardScreen(token = token)
        }
        return // Important: Exit the LoginScreen composable
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.login(username, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        loginResult?.let { result ->
            when (result) {
                is Result.Success -> {
                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                }
                is Result.Error -> {
                    Toast.makeText(context, "Login Failed: ${result.error.message}", Toast.LENGTH_SHORT).show()
                }
            }
            LaunchedEffect(Unit) {
                viewModel.resetLoginResult() // Reset result after showing the toast
            }
        }
    }
}

// Define DashboardScreen composable (if not already defined)
@Composable
fun DashboardScreen(token: String) {
    // Dashboard screen content goes here, for now, let's display the token.
    Text(text = "Welcome! Token: $token")
}

// Preview function for login screen
@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    BranchInternationalTheme {
        LoginScreen()
    }
}
