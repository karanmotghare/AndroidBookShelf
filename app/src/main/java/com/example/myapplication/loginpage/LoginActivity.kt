package com.example.myapplication.loginpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import com.example.myapplication.loginpage.navigation.LoginNavigation
import com.example.myapplication.loginpage.presentation.LoginScreen
import com.example.myapplication.loginpage.viewmodels.AuthViewModel

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel: AuthViewModel by viewModels()
        setContent {
            Scaffold {
                LoginNavigation(authViewModel)
            }
        }
    }
}

