package com.example.myapplication.loginpage.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.loginpage.presentation.LoginScreen
import com.example.myapplication.loginpage.presentation.SignupScreen
import com.example.myapplication.loginpage.viewmodels.AuthViewModel

@Composable
fun LoginNavigation(authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController , startDestination = "signup", builder = {
        composable("login") {
            LoginScreen(navController, authViewModel)
        }
        composable("signup") {
            SignupScreen(navController, authViewModel)
        }
    })
}
