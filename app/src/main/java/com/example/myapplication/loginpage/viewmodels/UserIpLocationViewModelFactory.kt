package com.example.myapplication.loginpage.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.loginpage.domain.usecases.UserIpLocationUseCase

class UserIpLocationViewModelFactory(private val useCase: UserIpLocationUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserIpLocationViewModel(useCase) as T
    }
}