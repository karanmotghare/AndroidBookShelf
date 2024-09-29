package com.example.myapplication.loginpage

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material.Scaffold
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.loginpage.data.repository.CountryListRepo
import com.example.myapplication.loginpage.data.repository.UserIpLocationRepo
import com.example.myapplication.loginpage.domain.usecases.CountryListUseCase
import com.example.myapplication.loginpage.domain.usecases.UserIpLocationUseCase
import com.example.myapplication.loginpage.navigation.LoginNavigation
import com.example.myapplication.loginpage.presentation.LoginScreen
import com.example.myapplication.loginpage.viewmodels.AuthViewModel
import com.example.myapplication.loginpage.viewmodels.CountryListViewModel
import com.example.myapplication.loginpage.viewmodels.CountryListViewModelFactory
import com.example.myapplication.loginpage.viewmodels.UserIpLocationViewModel
import com.example.myapplication.loginpage.viewmodels.UserIpLocationViewModelFactory

class LoginActivity : ComponentActivity() {
    private val countryViewModelFactory by lazy {
        CountryListViewModelFactory(
            CountryListUseCase(
                CountryListRepo()
            )
        )
    }
    private val countryViewModel: CountryListViewModel by lazy {
        ViewModelProvider(viewModelStore, countryViewModelFactory).get(CountryListViewModel::class.java)
    }

    private val userIpLocationViewModelFactory by lazy {
        UserIpLocationViewModelFactory(
            UserIpLocationUseCase(
                UserIpLocationRepo()
            )
        )
    }
    private val userIpViewModel: UserIpLocationViewModel by lazy {
        ViewModelProvider(viewModelStore, userIpLocationViewModelFactory).get(UserIpLocationViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel: AuthViewModel by viewModels()

        setContent {
            Scaffold {
                LoginNavigation(authViewModel, countryViewModel, userIpViewModel)
            }
        }
    }
}

