package com.example.branchinternational

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.branchinternational.ui.login.activity.LoginScreen
import com.example.branchinternational.ui.login.viewmodel.LoginViewModel

import com.example.branchinternational.ui.theme.BranchinternationalTheme
import com.example.branchinternational.util.SharedPreferencesManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BranchinternationalTheme {
                val navController = rememberNavController()
                val loginViewModel: LoginViewModel = viewModel()

                val loginState by loginViewModel.loginState.collectAsState()
                val toastMessage by loginViewModel.toastMessage.collectAsState()

                LaunchedEffect(toastMessage) {
                    toastMessage?.let {
                        Toast.makeText(applicationContext, it, Toast.LENGTH_SHORT).show()
                    }
                }

                val savedToken = sharedPreferencesManager.getAuthToken()

                NavHost(
                    navController = navController,
                    startDestination = if (savedToken != null) "threads" else "login"
                ) {
                    composable("login") {
                        LoginScreen(
                            loginState = loginState,
                            onLoginClick = { username, password ->
                                loginViewModel.login(username, password)
                            }
                        )

                        LaunchedEffect(loginState) {
                            loginState?.let { result ->
                                if (result.isSuccess) {
                                    navController.navigate("threads") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}
