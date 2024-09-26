package com.example.myapplication.loginpage.navigation

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.MainActivity
import com.example.myapplication.booklistpage.BooklistActivity
import com.example.myapplication.loginpage.presentation.LoginScreen
import com.example.myapplication.loginpage.presentation.SignupScreen
import com.example.myapplication.loginpage.viewmodels.AuthViewModel

@Composable
fun LoginNavigation(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController , startDestination = "login", builder = {
        composable("login") {
            LoginScreen(navController, authViewModel)
        }
        composable("signup") {
            SignupScreen(navController, authViewModel)
        }
        composable("home") {
            val intent = Intent(context, BooklistActivity::class.java)
            context.startActivity(intent)
        }
    })
}
