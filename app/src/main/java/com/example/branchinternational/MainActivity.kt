package com.example.branchinternational

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.branchinternational.ui.theme.BranchinternationalTheme
import com.example.branchinternational.ui.login.activity.LoginScreen
import com.example.branchinternational.ui.login.viewmodel.LoginViewModel
import com.example.branchinternational.ui.messages.composable.MessageThreadsScreen
import com.example.branchinternational.ui.messages.composable.ThreadDetailsScreen
import com.example.branchinternational.ui.messages.viewmodel.MessageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val messageViewModel: MessageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val savedToken = sharedPreferences.getString("authToken", null)

        setContent {
            BranchinternationalTheme {
                val navController = rememberNavController()
                val loginState = loginViewModel.loginState.collectAsState().value
                val toastMessage = loginViewModel.toastMessage.collectAsState()

                toastMessage.value?.let {
                    Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                }

                // Navigation Host
                NavHost(
                    navController = navController,
                    startDestination = if (savedToken != null) "threads" else "login"
                ) {
                    // Login Screen
                    composable("login") {
                        LoginScreen(
                            loginState = loginState,
                            onLoginClick = { username, password ->
                                loginViewModel.login(username, password)
                            }
                        )

                        // Navigate to threads on successful login
                        LaunchedEffect(loginState) {
                           loginState?.getOrNull()?.let {loginResponse ->
                               val token = loginResponse.authToken
                               sharedPreferences.edit().putString("authToken", token).apply()
                               navController.navigate("threads"){
                                   popUpTo("login") { inclusive = true }
                               }
                           } ?: run{
                               if (loginState?.isFailure == true){
                                   val errorMessage = loginState?.exceptionOrNull()?.message  ?: "Unknow error"
                                   Log.e("LoginError", errorMessage)
                               }
                        }
                        }
                    }

                    // Threads Screen
                    composable("threads") {
                        LaunchedEffect(savedToken) {
                            savedToken?.let { token ->
                                messageViewModel.fetchThreads(token)
                            }
                        }
                        MessageThreadsScreen(
                            navController = navController,
                            viewModel = messageViewModel
                        )
                    }

                    // Thread Details Screen
                    composable("threadDetails/{threadId}") { backStackEntry ->
                        val threadId = backStackEntry.arguments?.getString("threadId")?.toInt()
                            ?: return@composable
                        ThreadDetailsScreen(
                            navController = navController,
                            threadId = threadId,
                            viewModel = messageViewModel
                        )
                    }
                }
            }
        }
    }
}
