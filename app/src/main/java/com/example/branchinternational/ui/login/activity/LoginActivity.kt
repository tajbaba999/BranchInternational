package com.example.branchinternational.ui.login.activity

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.branchinternational.ui.login.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        setContent {
            val authToken = sharedPreferences.getString("authToken", null)

            if (authToken != null) {
                DashboardScreen(token = authToken)
            } else {
            }
        }
    }
}

@Composable
fun DashboardScreen(token: String) {
    Scaffold { paddingValues ->
        Text(
            text = "Welcome to the Dashboard!\nToken: $token",
            modifier = Modifier.padding(paddingValues)
        )
    }
}
