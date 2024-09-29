package com.example.myapplication.loginpage.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.loginpage.domain.usecases.CountryListUseCase

class CountryListViewModelFactory(private val useCase: CountryListUseCase): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CountryListViewModel(useCase) as T
    }
}